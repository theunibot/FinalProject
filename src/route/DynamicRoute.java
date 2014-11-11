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
	private int adjustedSpeed = 0;			// speed that has been found for this route at the specified accel once compiled
	private int accel = 0;					// acceleration that this route is compiled for
	
	// define the collection of points that makes up this route
    private ArrayList<Position> routePositions = new ArrayList<Position>();
	
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
		for (Position rp : dr.routePositions) {
			// note: this isn't a true clone, but we can get away with same referenced object here...
			this.routePositions.add(rp);
		}
	}
	
	/**
	 * Sets the compiled speed for this route
	 * 
	 * @param adjustedSpeed 
	 */
	private void setSpeed(int adjustedSpeed) {
		this.adjustedSpeed = adjustedSpeed;
	}
	
	/**
	 * Gets the compiled speed for this route (0 if not yet compiled)
	 * 
	 * @return Speed
	 */
	public int getSpeed() {
		return this.adjustedSpeed;
	}
	
    /**
     * Add a new position into the custom route list with custom speed/accel
     * 
     * @param newPosition Position to add to the route
     */
    public void addPosition(Position newPosition) {
        // is this position any different than the last one?  If not, just ignore it
        if (routePositions.size() > 0) {
            Position oldPosition = routePositions.get(routePositions.size() - 1);
            if (oldPosition.equals(newPosition))
                return;
        }
        routePositions.add(newPosition);
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePositions = new ArrayList<Position>();
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
		Result result = compiled.armRun(routeSpeed, routeAccel, false, null);
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
		// do a DRY ADJUST RUN and capture the speed...
		ResponsePattern responsePattern = new ResponsePattern();
		responsePattern.define("speed", "/SPEED = ([0-9]*)/");
		Result result = armRun(30000, routeAccel, true, responsePattern);
		if (!result.success())
			return result;
		
		if (responsePattern.lookup("speed") == null)
			return new Result("Missing SPEED result value");
		
		this.adjustedSpeed = Integer.parseInt(responsePattern.lookup("speed"));
		System.out.println("*******************   Resulting speed was: " + this.adjustedSpeed);
		return result;
	}
	
	/**
	 * Run the route on the arm (actually sends the routing commands)
	 * 
	 * @param routeSpeed maximum speed (but route may say to go slower)
	 * @param routeAccel maximum accel
	 * @param runTest true if do a dry run test, false if a real run
	 * @param pattern ResponsePattern to use (if any) on the resulting message from the robot
	 * 
	 * @return Result with success/fail info
	 */
	private Result armRun(int routeSpeed, int routeAccel, boolean runTest, ResponsePattern pattern) {        
        // initialize the dynamic route
//***** NEED TO FLIP TO USING CRUN and ?SPEED and DSPASSUME, assuming we can learn about failures...
		String runRoute = "DRINIT";
        Result result = ArmOperations.getInstance().runRobotCommand(runRoute);
        if (!result.success()) {
            clear();
            return result;
        }
        // send down each coordinate
		int currentSpeed = routeSpeed;
		if ( (this.adjustedSpeed < currentSpeed) && (this.adjustedSpeed != 0) )
			currentSpeed = this.adjustedSpeed;

		for (int index = 0; index < routePositions.size(); ++index) {
            // build up the next position
            Position rp = routePositions.get(index);
			runRoute = "";
			
			// program up the route position itself
            runRoute += 
                rp.getRollStr() + " " +
                rp.getYawStr() + " " +
                rp.getPitchStr() + " " +
                rp.getZStr() + " " +
                rp.getYStr() + " " +
                rp.getXStr() + " DRPOINT";
            result = ArmOperations.getInstance().runRobotCommand(runRoute);
            if (!result.success())
                return result;
        }
 
		// go run it
		result = ArmOperations.getInstance().runRobotCommand(Integer.toString(routeAccel) + " ACCEL ! " + 
				Integer.toString(currentSpeed) + " SPEED ! " +
				((runTest) ? "DRTEST" : "DRRUN"), pattern);
		return result;
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
				// decode values, which are hash speed
				String[] chunks = line.split(" ");
				// define a new route - decode the hash value
				hash = chunks[0].substring(1);
				// and get the speed
				int speed = Integer.parseInt(chunks[1]);
				// create new route
				dr = new DynamicRoute();
				dr.setSpeed(speed);
				// and place in cache
				compiledRoutes.put(hash, dr);
			} else {
				// decode the values for it, which are X Y Z P Y R
				String[] chunks = line.split(" ");
				Position rp = new Position(hash, 
					Double.parseDouble(chunks[0]),
					Double.parseDouble(chunks[1]),
					Double.parseDouble(chunks[2]),
					Double.parseDouble(chunks[3]),
					Double.parseDouble(chunks[4]),
					Double.parseDouble(chunks[5]));

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
			output.append("#" + hash + " " + route.adjustedSpeed + "\n");
			for (Position rp : route.routePositions) {
				output.append(Utils.formatDouble(rp.getX()) + " ");
				output.append(Utils.formatDouble(rp.getY()) + " ");
				output.append(Utils.formatDouble(rp.getZ()) + " ");
				output.append(Utils.formatDouble(rp.getPitch()) + " ");
				output.append(Utils.formatDouble(rp.getYaw()) + " ");
				output.append(Utils.formatDouble(rp.getRoll()) + "\n");
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
		for (Position rp : routePositions) {
			serialize.append(rp.getX());
			serialize.append(rp.getY());
			serialize.append(rp.getZ());
			serialize.append(rp.getRoll());
			serialize.append(rp.getYaw());
			serialize.append(rp.getPitch());
		}
		return Utils.hash(serialize.toString(), 32);
	}
}
