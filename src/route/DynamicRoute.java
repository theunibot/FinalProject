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

/**
 * Helper class for ArmOperations to build a custom route
 */
public class DynamicRoute {
    private StringBuilder routePoints = new StringBuilder();
    private StringBuilder routePointsReverse = new StringBuilder();
    private int pointCount = 0;

    /**
     * Add a new position into the custom route list
     * 
     * @param newPosition Position to add to the route
     */
    public void addPosition(Position newPosition) {
    System.out.println("# " + 
            newPosition.getPitchStr() + " PITCH ! " +
            newPosition.getYawStr() + " YAW ! " +
            newPosition.getRollStr() + " ROLL ! " +
            newPosition.getXStr() + " " +
            newPosition.getYStr() + " " +
            newPosition.getZStr() + " MOVETO");
        if (pointCount != 0)
            routePoints.append(" \r");
        routePoints.append(newPosition.getXStr());
        routePoints.append(" \r");
        routePoints.append(newPosition.getYStr());
        routePoints.append(" \r");
        routePoints.append(newPosition.getZStr());
        routePoints.append(" \r");
        routePoints.append(newPosition.getPitchStr());
        routePoints.append(" \r");
        routePoints.append(newPosition.getYawStr());
        routePoints.append(" \r");
        routePoints.append(newPosition.getRollStr());
        
        routePointsReverse.insert(0, 
            newPosition.getRollStr() + " " +
            newPosition.getYawStr() + " " +
            newPosition.getPitchStr() + " " +
            newPosition.getZStr() + " " +
            newPosition.getYStr() + " " +
            newPosition.getXStr() + " ");
        ++pointCount;
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePoints = new StringBuilder();
        pointCount = 0;
    }
    
    /**
     * Return the command string to run a route
     * 
     * @return String containing the command sequence to send to the controller
     */
    public String routeCommand() {
       return Integer.toString(pointCount) + " UDR\r\000\000" + routePoints.toString();
//       return routePointsReverse.toString() + Integer.toString(pointCount) + " UDS\r";
    }
}
