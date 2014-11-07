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

/**
 * Helper class for ArmOperations to build a custom route
 */
public class DynamicRoute {
    ArrayList<Position> routePoints = new ArrayList<Position>();
    
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
        if (routePoints.size() > 0) {
            Position oldPosition = routePoints.get(routePoints.size() - 1);
            if (oldPosition.equals(newPosition))
                return;
        }
        routePoints.add(newPosition);
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePoints = new ArrayList<Position>();
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
        if (routePoints.size() == 0)
            return new Result();
        
        // make sure speed and accel are acceptable
        if ( (routeSpeed > armMaxSpeed) || (routeSpeed == 0) )
            routeSpeed = armMaxSpeed;
        if ( (routeAccel > armMaxAccel) || (routeAccel == 0) )
            routeAccel = armMaxAccel;

        // initialize the dynamic route
        String runRoute = "DRINIT";
        Result result = ArmOperations.getInstance().runRobotCommand(runRoute);
        if (!result.success()) {
            clear();
            return result;
        }
        // send down each coordinate
        for (int index = 0; index < routePoints.size(); ++index) {
            // build up the next position
            Position pos = routePoints.get(index);
            runRoute = 
                pos.getRollStr() + " " +
                pos.getYawStr() + " " +
                pos.getPitchStr() + " " +
                pos.getZStr() + " " +
                pos.getYStr() + " " +
                pos.getXStr() + " DRPOINT";
            result = ArmOperations.getInstance().runRobotCommand(runRoute);
            if (!result.success())
                return result;
        }
        // run the actual route
        result = ArmOperations.getInstance().runRobotCommand(Integer.toString(routeAccel) + " ACCEL ! " + Integer.toString(routeSpeed) + " SPEED ! DRRUN");
        // zero out the route - even if there is an error - so we don't keep the data hanging around
        clear();
        // return success/fail
        return result;
    }

}
