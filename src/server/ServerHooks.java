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
package server;

import commandqueue.CommandQueues;
import commands.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.Utils;
import utils.Result;
import inventory.*;
import enums.*;
import route.Position;
import route.PositionLookup;

/**
 *
 */
public class ServerHooks
{

    //Objects
    private static ServerHooks s = null;

    //used in get/set vars calls
    private static Map<String, String> vars = Collections.synchronizedMap(new LinkedHashMap<String, String>());

    private CommandQueues cmdq = CommandQueues.getInstance();

    //used in JSON response
    ArrayList<KVObj> response = new ArrayList<KVObj>();

    public static ServerHooks getInstance()
    {
        if (s == null)
        {
            s = new ServerHooks();
        }
        return s;
    }

    public String enqueue(Map<String, String> params)
    {
        CommandInterface cmd = null;

        response.clear();
        String command = "";
        
        // decode all possible parameters
        String queue = params.get("queue");
        int queueInt = Utils.strToInt(queue);
        String status = params.get("status");
        boolean statusBool = (Utils.strToInt(status) == 1);
        String layer = params.get("layer");
        int layerInt = Utils.strToInt(layer);
        String shelf = params.get("shelf");
        int shelfInt = Utils.strToInt(shelf);
        String desktop = params.get("desktop");
        int desktopInt = Utils.strToInt(desktop);
        String effect = params.get("effect");
        RouteEffectType effectEnum = Utils.effectStringToEffectType(effect);
                
        // process the command ... start by making sure it actually is a "command"...
        if ((command = params.get("command")) == null) {
            // failed to get a valid command
            System.err.println("Enqueue with missing 'command' tag");
            return Utils.genericEnqueueFail();
        }
        // also make sure the queue and status flags were provided    
        if ( (queue == null) || (status == null) ) {
            System.err.println("Request failure: missing required parameter");
            return Utils.genericEnqueueFail();
        }
        
        // now decode the command and build up the queue item
        command = command.toLowerCase();    //convert the command to lowercase, case doesn't matter
        System.out.println("Enqueing command: " + command);
        switch (command) {
            case "mount-layer":
            case "replace-layer":
                if ( (layer == null) || (shelf == null) || (desktop == null) || (effect == null) ) {
                    System.err.println("Request failure: missing required parameter");
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandMountLayer(layerInt, shelfInt, desktopInt, effectEnum);
                break;
            case "show-layer":
                if ( (shelf == null) || (desktop == null) || (effect == null) ) {
                    System.err.println("Request failure: missing required parameter");
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandShowLayer(shelfInt, desktopInt, effectEnum);
                break;
            case "empty-desktop":
                if ( (desktop == null) ) {
                    System.err.println("Request failure: missing required parameter");
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandEmptyDesktop(desktopInt);
                break;
            case "show-sign":
                if ( (layer == null) || (effect == null) ) {
                    System.err.println("Request failure: missing required parameter");
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandShowSign(layerInt, effectEnum);
                break;
            case "arm-home":
                cmd = new CommandArmHome();
                break;
            case "arm-calibrate":
                cmd = new CommandArmCalibrate();
                break;
            case "arm-energize":
                cmd = new CommandArmEnergize();
                break;
            case "arm-de-energize":
                cmd = new CommandArmDeEnergize();
                break;
            case "position":
                String cabinetStr = params.get("cabinet");
                if ( (cabinetStr == null) || (shelf == null) ) {
                    System.err.println("Position command missing required parameter");
                    return Utils.genericEnqueueFail();
                }

                CabinetType cabinet;
                try {
                    cabinet = CabinetType.valueOf(cabinetStr.trim().toUpperCase());
                } catch (Exception e) {
                    System.err.println("Position command mismatch on CabinetType");
                    return Utils.genericEnqueueFail();
                }
                
                PositionLookup pl = PositionLookup.getInstance();

                Position position  = pl.shelfToPosition(cabinet, shelfInt);
                if (position == null) {
                    System.err.println("Unable to locate position on cabinet " + cabinet.toString() + " shelf " + shelfInt);
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandPosition(position);
                break;
            case "route":
                String fromCabinetStr = params.get("fromcabinet");
                String toCabinetStr = params.get("tocabinet");
                String effectStr = params.get("effect");
                String fromShelfStr = params.get("fromshelf");
                int fromShelf = Integer.valueOf(fromShelfStr);
                String toShelfStr = params.get("toshelf");
                int toShelf = Integer.valueOf(toShelfStr);
                
                if ( (fromCabinetStr == null) || (toCabinetStr == null) || (effectStr == null) ||
                        (fromShelfStr == null) || (toShelfStr == null) ) {
                    System.err.println("Route command missing required parameter");
                    return Utils.genericEnqueueFail();
                }
                CabinetType fromCabinet, toCabinet;
                try {
                    fromCabinet = CabinetType.valueOf(fromCabinetStr.trim().toUpperCase());
                    toCabinet = CabinetType.valueOf(toCabinetStr.trim().toUpperCase());
                } catch (Exception e) {
                    System.err.println("Route command mismatch on CabinetType or RouteEffectType");
                    return Utils.genericEnqueueFail();
                }
                
                pl = PositionLookup.getInstance();

                Position fromPosition = pl.shelfToPosition(fromCabinet, fromShelf);
                if (fromPosition == null) {
                    System.err.println("From position not found");
                    return Utils.genericEnqueueFail();
                }
                Position toPosition = pl.shelfToPosition(toCabinet, toShelf);
                if (toPosition == null) {
                    System.err.println("To position not found");
                    return Utils.genericEnqueueFail();
                }
                cmd = new CommandRoute(fromCabinet, fromPosition, toCabinet, toPosition, effectEnum);
                break;
        }
        // did we build a command?
        if (cmd != null) {
            cmdq.add(queueInt, cmd, statusBool);
            response.add(new KVObj("id", String.valueOf(cmd.getId())));
            System.out.println("Enqueued request; ID is " + cmd.getId());
            return Utils.buildJSON(response);
        }

        // somehow we failed...
        System.out.println("Failed to enqueue command");
        return Utils.genericEnqueueFail();
    }

    private final String STATUS_ID_KEY = "id";
    private final String STATUS_RETURN_STATUS_KEY = "status";
    private final String STATUS_ERROR_KEY = "error";

    public String status(Map<String, String> params)
    {
        response.clear();
        String id;

        CommandStatus status = CommandStatus.UNKNOWN;

        
        if ((id = params.get(STATUS_ID_KEY)) != null) {
            // note: we must call getResult before getStatus, because getStatus may remove the
            // item from the list if it has encountered success/error
            Result result = cmdq.getResult(id);
            status = cmdq.getStatus(id);
            response.add(new KVObj(STATUS_RETURN_STATUS_KEY, status.toString()));
            if (status == CommandStatus.ERROR) {
                if (result != null)
                    response.add(new KVObj(STATUS_ERROR_KEY, "\"" + result.errorMessage + "\""));
            }
        }
        else
            response.add(new KVObj(STATUS_ERROR_KEY, "Missing id value for status request"));

        return Utils.buildJSON(response);
    }

    private final String CLEAR_QUEUE_ID_KEY = "queue";

    public String clearQueue(Map<String, String> params)
    {
        response.clear();
        String strVal = null;
        if ((strVal = params.get(CLEAR_QUEUE_ID_KEY)) != null)
        {
            int intVal = -1;
            try
            {
                intVal = Integer.parseInt(strVal);
            }
            catch (NumberFormatException ignored)
            {
                System.out.println("Clear queue failed - invalid number " + strVal);
            }
            //if value is legit
            if (intVal != -1)
            {
                cmdq.clear(intVal);
            }
        } else
            System.out.println("Clear queue failed - no parameter found");
        return "{}";//returns nothing
    }

    private final String GET_INPUT_VAL_KEY = "key";
    private final String GET_RETURN_VAL_KEY = "\"value\"";

    public String getVar(Map<String, String> params)
    {
        response.clear();
        String key = null;

        if ((key = params.get(GET_INPUT_VAL_KEY)) == null)//get the value of the key
        {
            System.out.println("Error, getVar key not found");
        }
        System.out.println("GET Printout: Key: " + key + " Value: " + vars.get(key));
        String val;
        synchronized (vars)
        {
            val = vars.get(key);
        }
        if (val == null)//get value of the value
        {
            val = "\"\"";
            System.out.println("Error, " + key + "\'s value not found");
        }
        else
        {
            val = "\"" + val + "\"";
        }

        response.add(new KVObj(GET_RETURN_VAL_KEY, val));
        return Utils.buildJSON(response);
    }

    private final String SET_VAR_READ_VAL_VALUE = "value";
    private final String SET_VAR_READ_VAL_KEY = "key";

    public String setVar(Map<String, String> params)
    {

        response.clear();
        String key = null;

        if ((key = params.get(SET_VAR_READ_VAL_KEY)) == null)//get the value of the key
        {
            key = null;
            System.out.println("Error, key not found");
        }
        String val;
        if ((val = params.get(SET_VAR_READ_VAL_VALUE)) == null)//get value of the value
        {
            val = null;
            System.out.println("Error, value not found");
        }

        if (key != null && val != null)
        {
            System.out.println("Wrt: key: " + key + " val: " + val);
            synchronized (vars)
            {
                vars.put(key, val);
            }
        }
        synchronized (vars)
        {
            System.out.println("Wrt test Get Val: " + vars.get(key));
        }

        return "{}";//returns nothing
    }
    
    public String inventory(Map<String, String> params) {
        Inventory inventory = Inventory.getInstance();
        return inventory.dump();
    }
}
