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
import java.util.ArrayList;
import robotoperations.ArmOperations;
import static robotoperations.ArmOperations.armMaxAccel;
import static robotoperations.ArmOperations.armMaxSpeed;
import utils.Utils;
import java.util.HashMap;

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
	static HashMap<String, DynamicRoute> compiledRoutes = new HashMap<String, DynamicRoute>();
    
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
			Result result  = compile(routeAccel);
			if (!result.success())
				return result;
			compiled = this;
			// add to the hash map
			compiledRoutes.put(hash, this);
		} else
			System.out.println("Route cache hit SUCCESS!");
		return compiled.armRun(routeSpeed, routeAccel, false);
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
		// initialize all actuals to get things started
		for (RoutePosition position : routePositions) {
			position.actualAccel = routeAccel;
			position.actualSpeed = 1;
		}
		// process every line in the route
		for (RoutePosition position : routePositions) {
			// establish the brackets for the run
			int bracketLow = 1;
			int bracketHigh = 30001;
			int bestSpeed = -1;
			// loop until we find a good value for position
			while (true) {
				// and set the starting point
				int newSpeed = (bracketHigh - bracketLow) / 2 + bracketLow;
				// have we tried all possibilities?
				if (newSpeed == position.actualSpeed)
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
	private Result armRun(int routeSpeed, int routeAccel, boolean runTest) {        
        // initialize the dynamic route
        String runRoute = "DRINIT";
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
			int rpSpeed = (rp.actualSpeed == 0) ? routeSpeed : rp.actualSpeed;
			if (rpSpeed > routeSpeed)
				rpSpeed = routeSpeed;
			if (rpSpeed != currentSpeed) {
				currentSpeed = rpSpeed;
				runRoute += currentSpeed + " DRS ";
			}
			int rpAccel = (rp.actualAccel == 0) ? routeSpeed : rp.actualAccel;
			if (rpAccel > routeAccel)
				rpAccel = routeAccel;
			if (rpAccel != currentAccel) {
				currentAccel = rpAccel;
				runRoute += currentAccel + " DRS ";
			}
			
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
        result = ArmOperations.getInstance().runRobotCommand(Integer.toString(routeAccel) + " ACCEL ! " + Integer.toString(routeSpeed) + " SPEED ! " + (runTest ? "DRTEST" : "DRRUN"));
        // zero out the route - even if there is an error - so we don't keep the data hanging around
		if (!runTest)
	        clear();
        // return success/fail
        return result;
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
