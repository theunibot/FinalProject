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
import inventory.Inventory;
import robotoperations.ArmOperations;
import route.Position;
import route.PositionLookup;
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
    private Result result = new Result();
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
     * By default commands will have various messages logged about their progress.   Commands can override this and
     * return false to avoid having their command operations logged.
     * 
     * @return true if logging to be done, false if silent 
     */
    public boolean logActivity() {
        return true;
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
     * Gets the command current result value
     * 
     * @return Result with status and error information 
     */
    public Result getResult() {
        return this.result;
    }
    
    /**
     * Sets an result associated with this commands execution
     * 
     * @param result Result object with status/error information
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Gets the command current status value
     * 
     * @return Status of the command
     */
    public CommandStatus getStatus() {
        return this.status;
    }
    
    /**
     * Sets a status associated with this commands execution
     * 
     * @param Status to save with this command
     */
    public void setStatus(CommandStatus status) {
        this.status = status;
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
    protected Result moveLayer(CommandArguments args, CabinetType fromCabinet, int fromShelf, CabinetType toCabinet, int toShelf, RouteEffectType effect) {
        ArmOperations ao = ArmOperations.getInstance();
        RouteHolder rh = RouteHolder.getInstance();
        PositionLookup plt = PositionLookup.getInstance();
        Inventory inventory = Inventory.getInstance();
        Route route;
        Result result;
        
        System.out.println("  MoveLayer from " + fromCabinet.toString() + " shelf " + fromShelf + " to " +
                toCabinet.toString() + " shelf " + toShelf + " using effect " + effect.toString());
        
        //  determine the coordinate we must start at
        Position fromCoordinates = null;
        if (fromShelf != -1) {
            fromCoordinates = plt.shelfToPosition(fromCabinet, fromShelf);
            if (fromCoordinates == null)
                return new Result("Unable to locate point for " + fromCabinet.toString() + " shelf " + fromShelf);
        }

        // move from our current cabinet to the desired start (from) cabinet (if not already there)
        if (fromShelf != -1) {
            if (args.cabinet != fromCabinet) {
                // locate a route between these cabinets
                route = rh.getRoute(args.cabinet, fromCabinet, RouteEffectType.EFFICIENT);
                if (route == null)
                    return new Result("Unable to locate route from " + args.cabinet.toString() + " to " + fromCabinet.toString() + " (effect default)");


                // move the arm to the new cabinet
                result = ao.runRoute(route, args.coordinates, fromCoordinates);
                if (!result.success())
                    return result;

                // save our new position
                args.cabinet = fromCabinet;
                args.coordinates = fromCoordinates;

            } else if (!args.coordinates.equals(fromCoordinates)) {
                // we are in the same cabinet, but at the wrong location
                result = ao.moveTo(fromCoordinates);
                if (!result.success())
                    return result;
            }

            // now run the pick operation
            int depth = inventory.depth(fromCabinet, fromShelf);
            if (depth < 0)
                return new Result("Cabinet " + fromCabinet.toString() + " shelf " + fromShelf + " is empty; unable to retreive a disc");
            result = ao.pick(fromCabinet, fromShelf, depth, fromCoordinates);
            if (!result.success())
                return result;
        }
        
        // now find a route to the destination
        route = rh.getRoute(fromCabinet, toCabinet, effect);
        if (route == null)
            return new Result("Unable to locate route from " + fromCabinet.toString() + " to " + toCabinet.toString() + " (effect " + effect + ")");
        Position toCoordinates = null;
        if (toShelf != -1) {
            toCoordinates = plt.shelfToPosition(toCabinet, toShelf);
            if (toCoordinates == null)
                return new Result("Unable to locate point for " + toCabinet.toString() + " shelf " + toShelf);
        }
        result = ao.runRoute(route, fromCoordinates, toCoordinates);
        if (!result.success())
            return result;
        
        // update inventory to reflect this change - before the final drop so we can lookup what the
        // final effect on inventory would be to know our resting position (stack depth) on the cachepoint.
        // (this resolves an issue where moving a disk from/to the same slot would misunderstand the
        // depth)
        if ( (fromShelf != -1) && (toShelf != -1) ) {
            result = inventory.moveDisc(fromCabinet, fromShelf, toCabinet, toShelf);
            if (!result.success())
                return result;
        }
        // and finally run the drop operation
        if (toShelf != -1) { 
            int depth = inventory.depth(toCabinet, toShelf);
            
            result = ao.drop(toCabinet, toShelf, depth, plt.shelfToPosition(toCabinet, toShelf));
            if (!result.success())
                return result;

            // update args to reflect our new position
            args.cabinet = toCabinet;
            args.coordinates = plt.shelfToPosition(toCabinet, toShelf);
        }
        
        // success!
        return new Result();
   }
    
    /**
     * Internal function to move the arm to a position, while tracking it for future moves
     * 
     * @param args Command arguments to track the position of the arm
     * @param toCabinet Cabinet to move the disc to
     * @param toShelf to Shelf within the cabinet
     * @param effect effect to use for the actual placement route
     * 
     * @return Result with success/fail info
     */
    protected Result movePosition(CommandArguments args, CabinetType toCabinet, int toShelf, RouteEffectType effect) {
        ArmOperations ao = ArmOperations.getInstance();
        RouteHolder rh = RouteHolder.getInstance();
        PositionLookup plt = PositionLookup.getInstance();
        Inventory inventory = Inventory.getInstance();
        Route route;
        Result result;
        
        System.out.println("  MovePosition from " + args.cabinet.toString() + " position " + args.coordinates.toString() + " to " +
                toCabinet.toString() + " shelf " + toShelf + " using effect " + effect.toString());
        
        // locate the point we are moving to
        Position toCoordinates = null;
        toCoordinates = plt.shelfToPosition(toCabinet, toShelf);
        if (toCoordinates == null)
            return new Result("Unable to locate point for " + toCabinet.toString() + " shelf " + toShelf);

        // now find a route to the destination
        if (args.cabinet != toCabinet) {
            route = rh.getRoute(args.cabinet, toCabinet, effect);
            if (route == null)
                return new Result("Unable to locate route from " + args.cabinet.toString() + " to " + toCabinet.toString() + " (effect " + effect + ")");
            result = ao.runRoute(route, args.coordinates, toCoordinates);
            if (!result.success())
                return result;
        } else if (!args.coordinates.equals(toCoordinates)) {
            // we are in the same cabinet, but at the wrong location
            result = ao.moveTo(toCoordinates);
            if (!result.success())
                return result;
        }
        
        // update args to reflect our new position
        if (toShelf != -1) {
            args.cabinet = toCabinet;
            args.coordinates = plt.shelfToPosition(toCabinet, toShelf);
        }
        
        // success!
        return new Result();
   }
}
