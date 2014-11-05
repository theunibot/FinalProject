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

/**
 * Helper class for ArmOperations to build a custom route
 */
public class DynamicRoute {
    ArrayList<String> routePoints = new ArrayList<String>();
    
    /**
     * Add a new position into the custom route list
     * 
     * @param newPosition Position to add to the route
     */
    public void addPosition(Position newPosition) {
        routePoints.add( 
            newPosition.getRollStr() + " " +
            newPosition.getYawStr() + " " +
            newPosition.getPitchStr() + " " +
            newPosition.getZStr() + " " +
            newPosition.getYStr() + " " +
            newPosition.getXStr() + " DR2 " +
			routePoints.size() * 1000 + " DRS");
    }
    
    /**
     * Empty the route for a new route generation
     */
    public void clear() {
        routePoints = new ArrayList<String>();
    }
    
    /**
     * Return the command string to run a route
     * 
     * @param index of the item to return, starting at 0.  Returns null if no value at specified index
     * @return String containing the command sequence to send to the controller
     */
    public String routeCommand(int index) {
        if (index >= routePoints.size())
            return null;
        return routePoints.get(index);
    }
}
