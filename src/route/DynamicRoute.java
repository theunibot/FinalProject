//
// This file is part of theunibot.
//
// theunibot is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// theunibot is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with theunibot.  If not, see <http://www.gnu.org/licenses/>.
//
// Copyright (c) 2014 Unidesk Corporation
// 
package route;

import utils.Result;
import robotoperations.ArmOperations;
import static robotoperations.ArmOperations.armMaxAccel;
import static robotoperations.ArmOperations.armMaxSpeed;
import robotoperations.ResponsePattern;
import utils.Utils;
import java.util.*;
import java.io.*;
import utils.FileUtils;
/**
 * Helper class for ArmOperations to build a custom route
 */
public class DynamicRoute {
	private int accel = 0;								// acceleration that this route is compiled for
	private Position swapOldPosition = null;			// when using neighborXYZSwapForward, defines the old position to be looking for in the route
	private Position swapNewPosition = null;			// when using neighborXYZSwapForward, defines the new position to insert into the route
	private static boolean asyncSegmentRunning = false;	// used to track if the arm is currently running an async segment
	
	class RoutePosition {
		Position position = null;
		int speed = 0;
	}
	// define the collection of points that makes up this route
    private ArrayList<RoutePosition> routePositions = new ArrayList<RoutePosition>();
	
	// define a static table of compiled routes that have been vetted for speed
	static HashMap<String, DynamicRoute> compiledRoutes = null;
    
	/**
	 * Default constructor - nothing special
	 */
	public DynamicRoute() {	
		// load the compiled routes cache, if available
		if (compiledRoutes == null) {
			compiledRoutes = new HashMap<String, DynamicRoute>();
			load();
		}
	}
	
	/**
	 * Object duplication constructor
	 * 
	 * @param dr DynamicRoute to clone
	 */
	public DynamicRoute(DynamicRoute dr) {
		// duplicate the route positions
		for (RoutePosition rp : dr.routePositions) {
			RoutePosition newRP = new RoutePosition();
			newRP.speed = rp.speed;
			newRP.position = rp.position;
			this.routePositions.add(newRP);
		}
	}
	
    /**
     * Add a new position into the custom route list
     * 
     * @param newPosition Position to add to the route
     */
    public void addPosition(Position newPosition) {
		addPosition(newPosition, 0);
	}
	
    /**
     * Add a new position into the custom route list
     * 
     * @param newPosition Position to add to the route
	 * @param speed Speed to run the route at
     */
    public void addPosition(Position newPosition, int speed) {
		// before we add this position, check if neighborXYZSwapForward has been declared...
		if (swapOldPosition != null) {
			// see if this position is within range of swapOldPosition...
			if ((newPosition.distance(swapOldPosition)) < 1) {
				// it is the old position, so swap it to the new position
				newPosition.setXYZ(swapNewPosition);
			} else {
				// not within range, so clear out the tracking
				swapOldPosition = null;
				swapNewPosition = null;
			}
		}
        // is this position any different than the last one?  If not, just ignore it
        if (routePositions.size() > 0) {
			RoutePosition oldRP = routePositions.get(routePositions.size() - 1);
            if (oldRP.position.equals(newPosition))
                return;
        }
		RoutePosition newRP = new RoutePosition();
		newRP.position = newPosition;
		newRP.speed = speed;
        routePositions.add(newRP);
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePositions = new ArrayList<RoutePosition>();
    }
	
	/**
	 * Determines if the neighbor positions in the route (looking backward into the route
	 * tables) are at the same position as 'oldPosition', and if so, swap the XYZ with
	 * 'newPosition'.  This is to help reduce total route segments when needing to position
	 * at slightly different locations such as out-bottom being swapped with out-top.
	 * 
	 * @param oldPosition
	 * @param newPosition 
	 */
	public void neighborXYZSwapBackward(Position oldPosition, Position newPosition) {
		for (int index = routePositions.size() - 1; index > 0; --index) {
			Position dynPosition = routePositions.get(index).position;
			if (oldPosition.distance(dynPosition) < 1)
				dynPosition.setXYZ(newPosition);
			else
				break;
		}
	}
	
	/**
	 * Determines if the neighbor positions in the route (looking foward, into route positions
	 * that have not yet been added to the route table) are at the same position as 'oldPosition',
	 * and if so, swap the XYZ with 'newPosition'.  This is to help reduce total route segments when
	 * needing to position at slightly different locations such as out-bottom being swapped with out-top.
	 * 
	 * @param oldPosition
	 * @param newPosition 
	 */
	public void neighborXYZSwapForward(Position oldPosition, Position newPosition) {
		this.swapOldPosition = oldPosition;
		this.swapNewPosition = newPosition;
	}
	
    /**
     * Run a dynamic route with standard speed and accel
     * 
     * @return Result with success/fail info
     */
    public Result run() {
        return run(0, 0);
    }
	
    /**
     * Run a dynamic route with custom speed and accel
     *
     * @param routeSpeed    preferred speed; 0 for default
     * @param routeAccel    preferred acceleration; 0 for default
     *
     * @return Result with success/fail info
     */
    public Result run(int routeSpeed, int routeAccel) {
        // is there anything to run?
        if (routePositions.isEmpty())
            return new Result();
		
        // make sure speed and accel are acceptable
        if ( (routeSpeed > armMaxSpeed) || (routeSpeed == 0) )
            routeSpeed = armMaxSpeed;
        if ( (routeAccel > armMaxAccel) || (routeAccel == 0) )
            routeAccel = armMaxAccel;
		
		// do we already have this route compiled?
		String hash = hash(routeAccel);
		DynamicRoute compiled = compiledRoutes.get(hash);
		if (compiled == null) {
			System.out.println("Route cache hit failure; compiling route");
			// we need to compile this route by sending the route with various speeds to the controller
			// and vetting the best speed for each segment
			compiled = new DynamicRoute(this);
			compiled.accel = routeAccel;
			Result result  = compiled.compile(routeAccel);
			if (!result.success())
				return result;

			// add to the hash map
			compiledRoutes.put(hash, compiled);
			// and persist to disk
			persist();
		} else
			System.out.println("Route cache hit SUCCESS!");
		Result result = compiled.armRun(routeSpeed, routeAccel);
		// clear out our points so future operations start fresh
		clear();
		return result;
	}
	/**
	 * Define the return value fro the compileSegment method, which encapsulates a result and a speed value
	 */
	private class CompiledResult {
		Result result;
		int speed;
		
		public CompiledResult(Result newResult) {
			result = newResult;
		}
		public CompiledResult() {
			result = null;
		}
		public CompiledResult(Result newResult, int newSpeed) {
			result = newResult;
			speed = newSpeed;
		}
		public CompiledResult(int newSpeed) {
			result = new Result();
			speed = newSpeed;
		}
	}
	
	/**
	 * Compile a set of points (aka, segment) within a route - asking the controller for the fastest speed at which that
	 * segment can run at the given acceleration
	 * @param routeAccel
	 * @param startIndex
	 * @param endIndex
	 * 
	 * @return CompiledResult with the results
	 */
	private CompiledResult compileSegment(int routeAccel, int startIndex, int endIndex) {

		// do a DRY ADJUST RUN and capture the speed...
		ResponsePattern responsePattern = new ResponsePattern();
		responsePattern.define("speed", "SPEED = ([0-9]*)");
		Result result = armSegmentDryRun(startIndex, endIndex, routeAccel, responsePattern);
		if (!result.success())
			return new CompiledResult(result);
		
		if (responsePattern.lookup("speed") == null)
			return new CompiledResult(new Result("Missing SPEED result value"));

		return new CompiledResult(Integer.parseInt(responsePattern.lookup("speed")));
	}
		

	/**
	 * Compile the current route by finding the best speeds for various sections within the 
	 * route.  This is done by trying the route with the ADJUST feature of the controller to 
	 * see what speeds can be performed.  The route is walked by trying the segment adding
	 * one position at a time, and checking to see if speed remains within tolerance (looking both
	 * for going too slow as well as opportunity to go faster).  The route speeds are then set for
	 * the various segments so that when the route is run, it is broken down into individual
	 * routes for highest performance.
	 * 
	 * @param routeAccel acceleration to use for the compilation
	 * 
	 * @return Result with success/fail info
	 */
	private Result compile(int routeAccel) {
		// if the route has less than two points, just get out
		if (routePositions.isEmpty())
			return new Result();
		if (routePositions.size() == 1) {
			routePositions.get(0).speed = 30000;
			return new Result();
		}
		
		// set up the initial parameters for scanning the route
		int startIndex = 0;
		int endIndex = 1;
		int bestSpeed = 0;
		int nextSpeed = 0;
		int saveSpeed = 0;
		
		// process the route  
		while (endIndex < routePositions.size()) {
			boolean breakRoute = false;
System.out.println("***** Testing route from " + startIndex + " to " + endIndex + " bestSpeed is " + bestSpeed + ", saveSpeed is " + saveSpeed);

			// get the speed for the current positions
			CompiledResult cr = compileSegment(routeAccel, startIndex, endIndex);
			if (!cr.result.success())
				return cr.result;
			// is this within tolerance?
			if (bestSpeed == 0) {
				bestSpeed = cr.speed;
				saveSpeed = cr.speed;
			}
			else if (compileThreshold(bestSpeed, cr.speed, endIndex - startIndex + 1, true)) {
				// we have dropped speed too much.  break this apart.
				nextSpeed = 0;
				breakRoute = true;
				// revert back one segment section - as this new segment slowed us down too much
				endIndex--;
System.out.println("***** Break route due to too much speed drop on primary route, at speed " + saveSpeed);
			}
			else {
				// speed remains acceptable, so check if we could be going faster.  Start
				// by making sure there is at least one more point for us to process
				if (endIndex < (routePositions.size() - 1)) {
					// see if the new position plus the prior could run even faster
					CompiledResult crNext = compileSegment(routeAccel, endIndex - 1, endIndex);
					if (!crNext.result.success())
						return cr.result;
					// is it running subtantially faster?
					if (compileThreshold(crNext.speed, cr.speed, endIndex - startIndex + 1, false)) {
						// yes - break the route apart
						nextSpeed = crNext.speed;
						breakRoute = true;
System.out.println("***** Break route due to next segment being faster (speed was " + crNext.speed + ", saveSpeed is " + saveSpeed + " and cr.speed is " + cr.speed + ")");
						endIndex--;
					}
				}
			}
			
			// are we done with this route?
			if (endIndex == (routePositions.size() - 1)) {
				breakRoute = true;
				saveSpeed = cr.speed;
System.out.println("***** Break route due to end of segments");
			}
			
			// do we need to break this route apart?
			if (breakRoute) {
System.out.println("***** Breaking route from " + startIndex + " to " + endIndex + " to speed " + saveSpeed);
				// set the speed on the prior route segments
				for (int index = startIndex; index <= endIndex; ++index)
					routePositions.get(index).speed = saveSpeed;
				// update the index values and speed
				startIndex = endIndex;
				endIndex = (nextSpeed == 0) ? (startIndex + 1) : (startIndex + 2);
				bestSpeed = nextSpeed;
				saveSpeed = nextSpeed;
			} else {
				// advance to the next segment
				endIndex++;
				saveSpeed = cr.speed;
			}
		}
		return new Result();
	}
	
	/**
	 * compare a dry run speed with a prior speed and determine if it exceeds a threshold and needs to break the route apart
	 * 
	 * @param newSpeed new speed of the tested route
	 * @param priorSpeed prior speed for shorter or prior route
	 * @param segmentSize number of elements in this segment (used to optimize threshold analysis)
	 * @param thresholdTooSlow true if checking if the new point is too slow, false if checking if the new route is faster
	 * 
	 * @return boolean true if break route apart, false if not
	 */
	private boolean compileThreshold(int newSpeed, int priorSpeed, int segmentSize, boolean thresholdTooSlow) {
System.out.println("***** Checking " + newSpeed + " against " + priorSpeed + " segment size " + segmentSize + " looking for " + (thresholdTooSlow ? "slow" : "fast"));
		// determine delta between speeds
		int delta = newSpeed - priorSpeed;
		if (!thresholdTooSlow)
			delta = -delta;
		// if negative, then we are done
		if (delta < 0) {
System.out.println("***** Speed was better than expected, no need to break");			
			return false;
		}
		
		// determine the acceptable max range
		int factor;
		switch (segmentSize) {
			case 1:
			case 2:
				factor = 3;
				break;
			case 3:
				factor = 4;
				break;
			default:
				factor = 5;
				break;
		}
		int range = priorSpeed / factor;
		
		// determine if we must break apart this segment
System.out.println("***** Speed is " + ((delta > range) ? "within acceptable loss" : "excessive and needs to be broken"));
		return (delta > range);
	}

	/**
	 * Run the route on the arm (actually sends the routing commands)
	 * 
	 * @param routeSpeed maximum speed (but route may say to go slower)
	 * @param routeAccel maximum accel
	 * 
	 * @return Result with success/fail info
	 */
	private Result armRun(int routeSpeed, int routeAccel) {   
		Result result;
		
		// set the starting speed
		int speed = 0;
		// and track the section we are processing
		int startingSegment = 0;
		
		// scan all positions, building unique routes per speed set
		for (int index = 0; index < routePositions.size(); ++index) {
            // build up the next position
            RoutePosition rp = routePositions.get(index);
			
			// if we have not yet set our speed, pick it up from the first route segment
			if (speed == 0)
				speed = rp.speed;
			
			// is this route over (either speed change or end)?			
			if ( (rp.speed != speed) || (index == (routePositions.size() - 1)) ) {
				// run this next segment (and complete the prior if appropriate)
				result = armSegmentExecute(startingSegment, index, (speed > routeSpeed) ? routeSpeed : speed, routeAccel);				
				if (!result.success())
					return result;

				// if we have more segments, reset our values to continue the segment run
				if (index != (routePositions.size() - 1)) {
					startingSegment = index;
					--index;
					speed = rp.speed;
				}
			}
			
        }

		// make sure if there is an active async segment that it is closed off
		result = armSegmentComplete();
		if (!result.success())
			return result;
 
		return new Result();
    }

	/**
	 * Execute a segment on the arm controller, using async operations.  If a prior segment has been
	 * started, this will send down the new points and then wait for the prior segment to complete prior
	 * to starting a new route execution.  This will return prior to the arm completing the movement, so
	 * it is critical that either another armSegmentExecute is done right away (no other robot commands) or
	 * an armSegmentComplete is execute (to wait for the segment to complete and wrap it up)
	 * 
	 * @param firstSegment first segment to run (base 0)
	 * @param lastSegment last segment to run
	 * @param routeSpeed speed at which to run (ignores speeds in route itself, this is actual speed)
	 * @param routeAccel accel to use with run
	 * 
	 * @return Result with success/fail info
	 */
	private Result armSegmentExecute(int firstSegment, int lastSegment, int routeSpeed, int routeAccel) {
		// send down the points
		Result result = armSegmentPoints(firstSegment, lastSegment);
		if (!result.success()) {
			clear();
			return result;
		}
		
		// execute the route, including potentially waiting on the prior segment to complete
		String command = (asyncSegmentRunning ? "DRCOMPLETE " : "") + Integer.toString(routeAccel) + " ACCEL ! " + 
				routeSpeed + " SPEED ! DRSTART";
		result = ArmOperations.getInstance().runRobotCommand(command);
		if (!result.success()) {
			clear();
			return result;
		}
		// indicate we have async route pending
		asyncSegmentRunning = true;
		
		return new Result();
	}
	
	/**
	 * Complete the execution of a segment.  See armSegmentExecute for details, but this must be called after
	 * sending down one or more route segments prior to any other robot command being executed.
	 */
	private Result armSegmentComplete() {
		// execute the route
		if (asyncSegmentRunning) {
			// clear async flag
			asyncSegmentRunning = false;
			
			// and complete
			Result result = ArmOperations.getInstance().runRobotCommand("DRCOMPLETE");
			if (!result.success()) {
				clear();
				return result;
			}
		}
		return new Result();
	}

	/**
	 * Execute a dry run of a segment on the controller to learn a speed
	 * 
	 * @param firstSegment first segment to run (base 0)
	 * @param lastSegment last segment to run
	 * @param routeAccel accel to use with run
	 * @param responsePattern pattern to use when matching result messages from the controller
	 * 
	 * @return Result with success/fail info
	 */
	private Result armSegmentDryRun(int firstSegment, int lastSegment, int routeAccel, ResponsePattern pattern) {
		// send down the points
		Result result = armSegmentPoints(firstSegment, lastSegment);
		if (!result.success()) {
			clear();
			return result;
		}
		
		// execute the route, including potentially waiting on the prior segment to complete
		String command = Integer.toString(routeAccel) + " ACCEL ! " + 
				"30000 SPEED ! DRDRY";
		result = ArmOperations.getInstance().runRobotCommand(command, pattern);
		return result;
	}
		
	/**
	 * Program the points for a segment of a dynamic route into the controller
	 * 
	 * @param firstSegment first segment to run (base 0)
	 * @param lastSegment last segment to run
	 * 
	 * @return result with success/fail info
	 */
	private Result armSegmentPoints(int firstSegment, int lastSegment) {
		String runRoute;
		Result result;
		
		// initialize the dynamic route
		runRoute = "DRINIT";
		result = ArmOperations.getInstance().runRobotCommand(runRoute);
		if (!result.success()) {
			clear();
			return result;
		}

		// scan all positions, building unique routes per speed set
		for (int index = firstSegment; index <= lastSegment; ++index) {
            // build up the next position
            RoutePosition rp = routePositions.get(index);
			
			// program up the route position itself
            runRoute = 
                rp.position.getRollStr() + " " +
                rp.position.getYawStr() + " " +
                rp.position.getPitchStr() + " " +
                rp.position.getZStr() + " " +
                rp.position.getYStr() + " " +
                rp.position.getXStr() + " DRPOINT";
            result = ArmOperations.getInstance().runRobotCommand(runRoute);
            if (!result.success()) {
				clear();
                return result;
			}			
        }
		
		return new Result();
	}
	
	/**
	 * Load compiled routes cache from disk (RouteCache.txt)
	 */
	void load() {
		ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + "RouteCache.txt", "");
		if (lines == null) {
			System.out.println("Unable to load RouteCache.txt file; using blank cache for now");
			return;
		}
		System.out.println("Loading RouteCache.txt file; found " + lines.size() + " lines");
		
		// loop through the lines, breaking into sets of routes which are deliniated by a "#" character
		DynamicRoute dr = null;
		String hash = "";
		for (String line : lines) {
			String[] chunks;
			
			if (line.startsWith("#")) {
				// define a new route - decode the hash value and accel
				chunks = line.split(" ");
				hash = chunks[0].substring(1);
				int accel = Integer.parseInt(chunks[1]);
				// create new route
				dr = new DynamicRoute();
				dr.accel = accel;
				// and place in cache
				compiledRoutes.put(hash, dr);
			} else {
				// decode the values for it, which are X Y Z P Y R SPEED
				chunks = line.split(" ");
				RoutePosition rp = new RoutePosition();
				rp.position = new Position(hash, 
					Double.parseDouble(chunks[0]),
					Double.parseDouble(chunks[1]),
					Double.parseDouble(chunks[2]),
					Double.parseDouble(chunks[3]),
					Double.parseDouble(chunks[4]),
					Double.parseDouble(chunks[5]));
				rp.speed = Integer.parseInt(chunks[6]);

				dr.routePositions.add(rp);
			}
		}
		System.out.println("Total of " + compiledRoutes.size() + " routes loaded");
	}
	
	/**
	 * Invalidate the dynamic route cache, by setting all speed values to 0.  Future
	 * calls to recompile will build back the cache until all routes are completed.
	 */
	public void invalidate() {
		for (Map.Entry<String, DynamicRoute> entry : compiledRoutes.entrySet()) {
			// get the hash and the route entry
			String hash = entry.getKey();
			DynamicRoute route = compiledRoutes.get(hash);
			// reset to 0
			for (RoutePosition rp : route.routePositions)
				rp.speed = 0;
		}
		// and persist the changes
		persist();
	}
	
	/**
	 * Runs the route compiler on the next route in the compiledRoutes list that is lacking
	 * speed information.
	 * 
	 * @return Result with success/fail info, or null if no more routes to execute 
	 */
	public Result recompile() {
		for (Map.Entry<String, DynamicRoute> entry : compiledRoutes.entrySet()) {
			// get the hash and the route entry
			String hash = entry.getKey();
			DynamicRoute route = compiledRoutes.get(hash);
			// does this one have speed values?
			if (route.routePositions.get(0).speed == 0) {
				// compile this route
				Result result = route.compile(route.accel);
				if (!result.success())
					return result;
				// and persist the changes
				persist();
				return new Result();
			}
		}
		// done - nothing left to compile
		return null;
	}
    
    
	/**
	 * Persist compiled routes cache to disk (RouteCache.txt)
	 */
	void persist() {
		StringBuilder output = new StringBuilder();
		
		// loop over the entire hash table
		for (Map.Entry<String, DynamicRoute> entry : compiledRoutes.entrySet()) {
			// get the hash and the route entry
			String hash = entry.getKey();
			DynamicRoute route = compiledRoutes.get(hash); //entry.getValue();
			// now serialize them
			output.append("#" + hash + " " + route.accel + "\n");
			for (RoutePosition rp : route.routePositions) {
				output.append(Utils.formatDouble(rp.position.getX()) + " ");
				output.append(Utils.formatDouble(rp.position.getY()) + " ");
				output.append(Utils.formatDouble(rp.position.getZ()) + " ");
				output.append(Utils.formatDouble(rp.position.getPitch()) + " ");
				output.append(Utils.formatDouble(rp.position.getYaw()) + " ");
				output.append(Utils.formatDouble(rp.position.getRoll()) + " ");
				output.append(rp.speed + "\n");
			}
		}	
		// now record to the file
		if (!FileUtils.createFile(FileUtils.getFilesFolderString() + "RouteCache.txt", output.toString()))
			System.err.println("Unable to create DynamicRoutes file " + FileUtils.getFilesFolderString() + "RouteCache.txt");
	}

	/**
	 * Calculate a unique hashcode for this route to help locating a tested/compiled version for fast execution
	 * 
	 * @param accel Acceleration at which the route would be run
	 * @return String with an MD5 hash 
	 */
	String hash(int accel) {
		StringBuilder serialize = new StringBuilder();
		serialize.append(accel);
		for (RoutePosition rp : routePositions) {
			serialize.append(rp.position.getX());
			serialize.append(rp.position.getY());
			serialize.append(rp.position.getZ());
			serialize.append(rp.position.getRoll());
			serialize.append(rp.position.getYaw());
			serialize.append(rp.position.getPitch());
		}
		return Utils.hash(serialize.toString(), 32);
	}
	
	/**
	 * Overrides toString to contain all information about this route
	 * 
	 * @return String with dynamic route information
	 */
	public String toString() {
		StringBuilder dump = new StringBuilder();
		int pos = 1;
		for (RoutePosition rp : routePositions) {
			dump.append("Pos " + pos++ + ": ");
			dump.append(rp.position.getX() + ", ");
			dump.append(rp.position.getY() + ", ");
			dump.append(rp.position.getZ() + " [");
			dump.append(rp.position.getRoll() + ", ");
			dump.append(rp.position.getYaw() + ", ");
			dump.append(rp.position.getPitch() + "] speed " + rp.speed + "\n");
		}
		return dump.toString();
	}
}
