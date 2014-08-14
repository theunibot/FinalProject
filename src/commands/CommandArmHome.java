/*
    This file is part of theunibot.

    theunibot is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    theunibot is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */

package commands;

import enums.*;
import robotoperations.ArmOperations;
import route.Position;
import route.PositionLookup;
import route.Route;
import route.RouteHolder;
import utils.Result;

/**
 *
 */
public class CommandArmHome extends CommandInterface {
    public CommandArmHome() {
        
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        Result result;
        
        // do we need to find a safe route to get near home?
        if (args.cabinet != CabinetType.HOME) {
            // go get a route that is safe...
            Route homeRoute = RouteHolder.getInstance().getRoute(args.cabinet, CabinetType.HOME, RouteEffectType.EFFICIENT);
            if (homeRoute == null)
                return new Result("Unable to locate route from " + args.cabinet.toString() + " to HOME");
            // go run the route
            result = ao.runRoute(homeRoute, args.coordinates, PositionLookup.homePosition());
            if (!result.success())
                return result;
        }
        result = ao.home();
        if (!result.success())
            return result;
        // sucess - record our new known position
        args.cabinet = CabinetType.HOME;
        args.coordinates = PositionLookup.homePosition();
        return new Result();
    }
    
    public String details() {
        return "Home()";
    }

    /**
     * This command should be allowed to run, even if we have outstanding robot errors
     * 
     * @return true to indicate we should run even during errors
     */
    public boolean ignoreErrors() {
        return true;
    }    

}
