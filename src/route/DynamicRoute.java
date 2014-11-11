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
import utils.Utils;
import java.util.*;
import java.io.*;
import utils.FileUtils;
/**
 * Helper class for ArmOperations to build a custom route
 */
public class DynamicRoute {
	// define the data for an individual point in the route
	private class RoutePosition {
		public Position position = null;	// the next position in the route (XYZPYR)
		public int maxSpeed = 0;			// max speed this segment is allowed to run at
		public int maxAccel = 0;			// max acceleration that this segment is allowed to run at
		public int actualSpeed = 0;			// best speed we can use, after testing the route on the controller
		public int actualAccel = 0;			// best acceleration we can use, after testing the route on the controller
	}
	// define the collection of points that makes up this route
    ArrayList<RoutePosition> routePositions = new ArrayList<RoutePosition>();
	// define a static table of compiled routes that have been vetted for max speed on the controller.  This is a very
	// expensive process, so it's important that we cache and persist it to improve performance
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
			// duplicate the properties
			RoutePosition newRp = new RoutePosition();
			newRp.maxSpeed = rp.maxSpeed;
			newRp.maxAccel = rp.maxAccel;
			newRp.actualSpeed = rp.actualSpeed;
			newRp.actualAccel = rp.actualAccel;
			// note: this isn't a true clone, but we can get away with same referenced object here...
			newRp.position = rp.position;
			this.routePositions.add(newRp);
		}
	}
	
    /**
     * Add a new position into the custom route list 
     * 
     * @param newPosition Position to add to the route
     */
    public void addPosition(Position newPosition) {
        addPosition(newPosition, armMaxSpeed, armMaxAccel);
    }
    
    /**
     * Add a new position into the custom route list with custom speed/accel
     * 
     * @param newPosition Position to add to the route
     * @param speed Speed to run this at (0=default)
     * @param accel Accel to run this at (0=default)
     */
    public void addPosition(Position newPosition, int speed, int accel) {
        // is this position any different than the last one?  If not, just ignore it
        if (routePositions.size() > 0) {
            Position oldPosition = routePositions.get(routePositions.size() - 1).position;
            if (oldPosition.equals(newPosition))
                return;
        }
		RoutePosition rp = new RoutePosition();
		rp.position = newPosition;
		rp.maxSpeed = speed;
		rp.maxAccel = accel;
        routePositions.add(rp);
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePositions = new ArrayList<RoutePosition>();
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
		Result result = compiled.armRun(routeSpeed, routeAccel, false);
		// clear out our points so future operations start fresh
		clear();
		return result;
	}
	
	/**
	 * Compile the current route by sending it to the controller with binary searching on possible speeds at
	 * each route position until we find the optimal performance for the route.  This adds the actualSpeed and
	 * actualAccel values into the route.
	 * 
	 * @param routeAccel acceleration to use for the compilation
	 * 
	 * @return Result with success/fail info
	 */
	private Result compile(int routeAccel) {
		return new Result();
		/*
		// does this route have more than one point?
		if (routePositions.size() <= 1)
			return new Result();
		
		// initialize all actuals to get things started (skipping first one)
		for (int posIndex = 1; posIndex < routePositions.size(); ++posIndex) {
			RoutePosition position = routePositions.get(posIndex);
			position.actualAccel = routeAccel;
			position.actualSpeed = 500;
		}
		// process every line in the route (though we skip the first one, since it runs at default speed)
		for (int posIndex = 1; posIndex < routePositions.size(); ++posIndex) {
			RoutePosition position = routePositions.get(posIndex);
			// establish the brackets for the run
			int bracketLow = 1;
			int bracketHigh = 30001;
			int bestSpeed = -1;
			// loop until we find a good value for position
			while (true) {
				// and set the starting point
				int newSpeed = (bracketHigh - bracketLow) / 2 + bracketLow;
				// have we tried all possibilities?
				if ((newSpeed / 100) == (position.actualSpeed / 100))
					break;
				// set the speed into the route
				position.actualSpeed = newSpeed;
				// now test the value...
				Result result = armRun(30000, routeAccel, true);
				// failure indicates we went too fast, success means we might be able to go faster!
				if (result.success()) {
					System.out.println("SUCCESS - setting LOW bracket to " + position.actualSpeed);
					// success, so maybe we can go faster ... set this speed as the new bracket low
					bracketLow = position.actualSpeed;
					bestSpeed = position.actualSpeed;
				}
				else {
					System.out.println("FAILURE - setting HIGH bracket to " + position.actualSpeed);
					// failure, so don't try anything faster tha this
					bracketHigh = position.actualSpeed;
				}
			}
			// set the speed
			if (bestSpeed == -1)
				return new Result("Unable to find acceptable speed for route");
			position.actualSpeed = bestSpeed;
		}
		return new Result();
		*/
	}
	
	/**
	 * Run the route on the arm (actually sends the routing commands)
	 * 
	 * @param routeSpeed maximum speed
	 * @param routeAccel maximum accel
	 * @param runTest true if do a dry run test, false if a real run
	 * 
	 * @return Result with success/fail info
	 */
private static int routeID = 1;
private Result armRun(int routeSpeed, int routeAccel, boolean runTest) {        
        // initialize the dynamic route

		String runRoute = "ROUTE R" + routeID++ + " 40 RESERVE DRINIT";
        Result result = ArmOperations.getInstance().runRobotCommand(runRoute);
        if (!result.success()) {
            clear();
            return result;
        }
        // send down each coordinate
		int currentSpeed = routeSpeed;
		int currentAccel = routeAccel;
        for (int index = 0; index < routePositions.size(); ++index) {
            // build up the next position
            RoutePosition rp = routePositions.get(index);
			runRoute = "";
			
			// determine what speed and accel we run at
			/*
			int rpSpeed = (rp.actualSpeed == 0) ? routeSpeed : rp.actualSpeed;
			if (rpSpeed > routeSpeed)
				rpSpeed = routeSpeed;
			if (rpSpeed != currentSpeed) {
				currentSpeed = rpSpeed;
				runRoute += currentSpeed + " DRSPEED ";
			}
			int rpAccel = (rp.actualAccel == 0) ? routeSpeed : rp.actualAccel;
			if (rpAccel > routeAccel)
				rpAccel = routeAccel;
			if (rpAccel != currentAccel) {
				currentAccel = rpAccel;
				runRoute += currentAccel + " DRACCEL";
			}
*/
			// program up the route position itself
            runRoute += 
                rp.position.getRollStr() + " " +
                rp.position.getYawStr() + " " +
                rp.position.getPitchStr() + " " +
                rp.position.getZStr() + " " +
                rp.position.getYStr() + " " +
                rp.position.getXStr() + " DRPOINT";
            result = ArmOperations.getInstance().runRobotCommand(runRoute);
            if (!result.success())
                return result;
        }
        // run the actual route
        return ArmOperations.getInstance().runRobotCommand(Integer.toString(routeAccel) + " ACCEL ! " + Integer.toString(routeSpeed) + " SPEED ! " + (runTest ? "DRTEST" : "DRRUN"));
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
				// its a point in the route
				RoutePosition rp = new RoutePosition();
				// decode the values for it, which are Speed Size X Y Z P Y R
				String[] chunks = line.split(" ");
				rp.actualSpeed = Integer.parseInt(chunks[0]);
				rp.actualAccel = Integer.parseInt(chunks[1]);
				rp.position = new Position(hash, 
					Double.parseDouble(chunks[2]),
					Double.parseDouble(chunks[3]),
					Double.parseDouble(chunks[4]),
					Double.parseDouble(chunks[5]),
					Double.parseDouble(chunks[6]),
					Double.parseDouble(chunks[7]));

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
				output.append(rp.actualSpeed + " ");
				output.append(rp.actualAccel + " ");
				output.append(Utils.formatDouble(rp.position.getX()) + " ");
				output.append(Utils.formatDouble(rp.position.getY()) + " ");
				output.append(Utils.formatDouble(rp.position.getZ()) + " ");
				output.append(Utils.formatDouble(rp.position.getPitch()) + " ");
				output.append(Utils.formatDouble(rp.position.getYaw()) + " ");
				output.append(Utils.formatDouble(rp.position.getRoll()) + "\n");
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
			serialize.append(rp.maxSpeed);
			serialize.append(rp.maxAccel);
		}
		return Utils.hash(serialize.toString(), 32);
	}
}
