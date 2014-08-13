/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import enums.*;
import inventory.Inventory;
import robotoperations.ArmOperations;
import route.Cartesian;
import route.PositionLookupTable;
import route.Route;
import route.RouteHolder;
import utils.Result;

/**
 * Base interface class for all robot commands 
 */
public abstract class CommandInterface
{
    private static long uniqueId = 1;
    private long id = uniqueId++;
    private CommandStatus status = CommandStatus.PENDING;
    private int queueIndex = -1;

    /**
     * Returns the unique ID of the command
     * 
     * @return ID of the command
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * Executes the command to the robot arm
     * 
     * @param cabinetType of cabinet at start; should be updated to reflect type at end
     * @return success (true), or failure (false) 
     */
    public abstract Result execute(CommandArguments args);
    
    /**
     * Provide a debugging string of details about this command
     * 
     * @return String with the summary
     */
    public abstract String details();

    /**
     * Get the current status of this command
     * 
     * @return CommandStatus with the current status
     */
    public CommandStatus getStatus() {
        return this.status;
    }
    
    /**
     * Set the current status of this command
     * 
     * @param status Current status of this command; set by CommandProcessor
     */
    public void setStatus(CommandStatus status) {
        this.status = status;
    }
    
    
    /**
     * By default, commands will not execute if there is an error outstanding.  If a command
     * should execute even when there are errors, if should override this method and return
     * true.
     * 
     * @return false if errors should not be ignored, true if they should
     */
    public boolean ignoreErrors() {
        return false;
    }
    
    /**
     * By default, once an error occurs it will remain set even if some commands (those that override ignoreErrors)
     * are successful.  Only commands that support successClearsError (overrides it and returns true) will actually
     * clear the error.  Likely, this will only be used by Calibrate.
     * 
     * @return false if errors should not be cleared, true if they should
     */
    public boolean successClearsError() {
        return false;
    }
    
    /**
     * Set the queue index for this command; used to track which queue this command is executing in
     * 
     * @param queueIndex Index of the queue (0=priority, 1/2 = desktop queues)
     */
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    /**
     * Returns the queue index used by this command
     * 
     * @return int with queue index (0=priority; 1/2 = desktop queues)
     */
    public int getQueueIndex() {
        return this.queueIndex;
    }
    

    /**
     * Internal function to execute the entire move operation of a layer
     * 
     * @param args Command arguments to track the position of the arm
     * @param fromCabinet Cabinet to move the disc from
     * @param fromShelf from Shelf within the cabinet
     * @param toCabinet Cabinet to move the disc to
     * @param toShelf to Shelf within the cabinet
     * @param effect effect to use for the actual placement route
     * 
     * @return Result with success/fail info
     */
    protected Result moveLayer(CommandArguments args, CabinetType fromCabinet, int fromShelf, CabinetType toCabinet, int toShelf, String effect) {
        ArmOperations ao = ArmOperations.getInstance();
        RouteHolder rh = RouteHolder.getInstance();
        PositionLookupTable plt = PositionLookupTable.getInstance();
        Inventory inventory = Inventory.getInstance();
        Route route;
        Result result;
        
       // move from our current cabinet to the desired start (from) cabinet (if not already there)
        if (args.cabinet != fromCabinet) {
            // locate a route between these cabinets
            route = rh.getRoute(args.cabinet, fromCabinet, "default");
            if (route == null)
                return new Result("Unable to locate route from " + args.cabinet.toString() + " to " + fromCabinet.toString() + " (effect default)");
            // and determine the final coordinate we must reset at
            Cartesian endCoordinates = plt.shelfToCartesian(fromCabinet, fromShelf);
            // move the arm to the new cabinet
            result = ao.runRoute(route, args.coordinates, endCoordinates);
            if (!result.success())
                return result;
            
            // save our new position
            args.cabinet = fromCabinet;
            args.coordinates = endCoordinates;
            
        }

        // now run the pick operation
        int depth = inventory.depth(fromCabinet, fromShelf);
        if (depth < 0)
            return new Result("Cabinet " + fromCabinet.toString() + " shelf " + fromShelf + " is empty; unable to retreive a disc");
        result = ao.pick(fromCabinet, depth, plt.shelfToCartesian(fromCabinet, fromShelf));
        if (!result.success())
            return result;
        
        // now find a route to the desktop
        route = rh.getRoute(fromCabinet, toCabinet, effect);
        if (route == null)
            return new Result("Unable to locate route from " + fromCabinet.toString() + " to " + toCabinet.toString() + " (effect " + effect + ")");
        result = ao.runRoute(route, plt.shelfToCartesian(fromCabinet, fromShelf), plt.shelfToCartesian(toCabinet, toShelf));
        if (!result.success())
            return result;
        
        // and finally run the drop operation
        depth = inventory.depth(toCabinet, toShelf);
        result = ao.drop(toCabinet, depth + 1, plt.shelfToCartesian(toCabinet, toShelf));
        if (!result.success())
            return result;

        // update args to reflect our new position
        args.cabinet = toCabinet;
        args.coordinates = plt.shelfToCartesian(toCabinet, toShelf);
        
        // update inventory to reflect this change
        result = inventory.moveDisc(fromCabinet, fromShelf, toCabinet, toShelf);
        if (!result.success())
            return result;
        
        // success!
        return new Result();
   }
        
}
