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
public class CommandMountLayer extends CommandInterface {
    int layer;
    int  shelf;
    int desktop;
    EffectType effect;
    
    public CommandMountLayer(int layer, int shelf, int desktop, EffectType effect) {
        this.layer = layer;
        this.shelf = shelf;
        this.desktop = desktop;
        this.effect = effect;
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        RouteHolder rh = RouteHolder.getInstance();
        PositionLookupTable plt = PositionLookupTable.getInstance();
        Result result;
        
        /*********************
        /*********************   NEED TO ADD INVENTORY TO KNOW PICK POSITIONS AND CLEAR SLOT IF IN USE
        /*********************
        /*********************/
        
        // determine which cabinet we are moving towards
        CabinetType newCabinet = CabinetType.HOME; //utils.Utils.shelfToCabinet(this.layer);
        /********* FIX THE ABOVE ********/
        
        // move from our current cabinet to the desired cabinet (if not already there)
        if (args.cabinet != newCabinet) {
            // locate a route between these cabinets
            Route route = null; //rh.getRoutes(args.cabinet, newCabinet, "normal");
            if (route == null)
                return new Result("Unable to locate route from " + args.cabinet.toString() + " to " + newCabinet.toString() + " (effect normal)");
            // and determine the final coordinate we must reset at
            Cartesian endCoordinates = plt.shelfToCartesian(newCabinet, this.shelf);
            // move the arm to the new cabinet
            result = ao.runRoute(route, args.coordinates, endCoordinates);
            if (!result.success())
                return result;
            // save our new position
            args.cabinet = newCabinet;
            args.coordinates = endCoordinates;
        }
        // now run the pick operation
        
        // now find a route to the desktop
        
        // and finally run the drop operation
        
        // success!
        return new Result();
   }
    
    public String details() {
        return "MountLayer(" + layer + ", " + shelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
