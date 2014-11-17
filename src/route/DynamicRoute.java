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
	 * Compile the current route by sending it to the controller with binary searching on possible speeds at
	 * each route position until we find the optimal performance for the route.  This adds the actualSpeed 
	 * value into the route.
	 * 
	 * @param routeAccel acceleration to use for the compilation
	 * 
	 * @return Result with success/fail info
	 */
	private Result compile(int routeAccel) {
		// fake out the actual speed analysis...
		for (RoutePosition rp : this.routePositions)
			rp.speed = 30000;
		return new Result();
		
		/*
		Route of ABCDEF:
		
		AB - store speed
		ABC - within tolerance? YES...
		BC - much faster? NO...
		ABCD - within tolerance?  Yes...
		CD - much faster?  YES...
		[ABC]
		
		CD - store speed
		CDE - within tolerance?  YES...
		DE - much faster?  NO...
		CDEF - within tolerance?  NO...
		[CDE]
		
		F - only a single point, so done.
		[F]
		
		set startIndex = 0
		set endIndex = 1
		send down points startIndex to endIndex
		capture effective speed
		loop
			send down points startIndex to endIndex + 1
			is speed within range?
				No - mark points startIndex thru endIndex with this speed
				set startIndex to endIndex
				set endIndex to startIndex + 1
			
		
		Given start/end, verify next slot - is it end of this segment, or do we increment end to next position?
		Then walk each one to figure out what happens
		When a segment ends, go back and set speed
	
		So this function will need to return:
		- Continue or terminate
		- Speed of next segment (if known)
		Passed:
		- start/end
		- startingSpeed (speed of the first route pair)

		private class RouteValidate {
			bool terminate;
			int segmentSpeed = 0;
			Result result;
		}

		private RouteValidate validate(int firstSegment, int lastSegment, int startingSpeed)
		
		// if we have at least two segments, if not, we are done because 30000 is fine with
		// a single segment, though we need to deal with that somehow
		if (we have only one segment)
			set segment speed = 30000;
			done/return;
		
		firstSegment = 0;
		lastSegment = 1;
		bestSpeed = 0;
		segmentSpeed = 0;
		
		RouteValidate rv = validate(firstSegment, lastSegment, bestSpeed);
		if (!rv.result.success())
			return rv.result;
		// do we terminate or continue?
		if (rv.terminate) {
			// mark everything from firstSegment to lastSegment with segmentSpeed
		} else {
			++lastSegment;
			segmentSpeed = rv.segmentSpeed;
		}
		
		-- maybe this is all backwards...  another approach:
		
		firstSegment = 1;
		while (firstSegment < routePositions.size())
			result = validateSegment()
			if (!result.success())
				return result;

		Result validateSegment() {
			// starting at firstSegment, determine the number of segments that should be in this route based on speed
			lastSegment = firstSegment + 1;
			// is only one segment remains, run it at high speed
			if (lastSegment == routePositions.size()) {
				routePositions.get(firstSegment).speed = 30000;
				return new Result();
			}
			// validate the segments, trying sequences of positive and negative, looking for best speeds
			bestSpeed = 0;
			loop incrementing lastSegment until no more segments
				speed/result = dryRun(firstSegment, lastSegment)
				if (!result.success())
					return result;
				if (bestSpeed == 0)
					bestSpeed = speed;
				else if (speed is not within X% of bestSpeed)
					set speeds on firstSegment through lastSegment - 1
					set firstSegment to lastSegment - 1
					return success on firstSegment through lastSegment - 1
				else if (there is at least one more segment)
					try again with lastSegment thru lastSegment +1
					if (speed is more than Y% better than last speed)
						somehow save speed so next run can start with it rather than running it again (optional)
						return success on prior segment;
				else
					// speed is good enough, and no more segments
					set speed on firstSegment through lastSegment
					return success
				
		
		
		*/
		
		/*
			We want to run the route DRY multiple times, building up routes until we
			see the DRY RUN speed degrade too much.  Then we assign the "best speed" and
			go again with the new route segment(s).  The problem with this is that we
			won't find opportunities to speed up ... only to slow down.
		
			So instead, we try two segments.  That becomes baseline speed.  We add a third
			segment and capture it's speed.  If much slower, it is a breakpoint.  If not, we 
			take the 2nd and 3rd and try them.  If they are much faster, we have a break point.  
		
			This should be designed so that the number we span is configurable.  We may find that
			we are better working in sets of three than two, for continuous to look good.
		*/
//		// do a DRY ADJUST RUN and capture the speed...
//		ResponsePattern responsePattern = new ResponsePattern();
//		responsePattern.define("speed", "SPEED = ([0-9]*)");
//		Result result = armRun(30000, routeAccel, true, responsePattern);
//		if (!result.success())
//			return result;
//		
//		if (responsePattern.lookup("speed") == null)
//			return new Result("Missing SPEED result value");
//// TODO: Figure out the new compile and save speed accordingly
////		this.adjustedSpeed = Integer.parseInt(responsePattern.lookup("speed"));
//		return result;
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
			if (line.startsWith("#")) {
				// define a new route - decode the hash value
				hash = line.substring(1);
				// create new route
				dr = new DynamicRoute();
				// and place in cache
				compiledRoutes.put(hash, dr);
			} else {
				// decode the values for it, which are X Y Z P Y R SPEED
				String[] chunks = line.split(" ");
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
			output.append("#" + hash + "\n");
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
