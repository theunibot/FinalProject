/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;
import robotoperations.ArmOperations;
import route.Cartesian;
import route.PositionLookupTable;
import route.Route;
import route.RouteHolder;
import utils.Result;

/**
 *
 * @author cmidgley
 */
public class CommandArmHome extends CommandInterface {
    public CommandArmHome() {
        
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        // go get a route that is safe...
        Route homeRoute = null; //RouteHolder.getInstance().getRoutes(args.cabinet, CabinetType.HOME, "SAFEHOME");
        if (homeRoute == null)
            return new Result("Unable to locate route from " + args.cabinet.toString() + " to HOME (for SAFEHOME)");
        // go run the route
        Result result = ao.runRoute(homeRoute, args.coordinates, PositionLookupTable.homeCartesian());
        if (!result.success())
            return result;
        result = ao.home();
        if (!result.success())
            return result;
        // sucess - record our new known position
        args.cabinet = CabinetType.HOME;
        args.coordinates = PositionLookupTable.homeCartesian();
        return new Result();
    }
    
    public String details() {
        return "Home()";
    }
}
