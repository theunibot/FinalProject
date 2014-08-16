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
import enums.*;
import inventory.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import route.Position;
import route.PositionLookup;
import utils.Result;
import utils.Utils;

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
        if ((command = params.get("command")) == null)
            // failed to get a valid command
            return Utils.genericEnqueueFail("Enqueue missing 'command' tag");
        
        // also make sure the queue and status flags were provided    
        if (queue == null)
            return Utils.genericEnqueueFail("Request failure: missing 'queue' parameter");
        if (status == null)
            return Utils.genericEnqueueFail("Request failure: missing 'status' parameter");
        
        // now decode the command and build up the queue item
        command = command.toLowerCase();    //convert the command to lowercase, case doesn't matter
        System.out.println("Enqueing command: " + command);
        switch (command) {
            case "mount-layer":
            case "replace-layer":
                if (layer == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'layer' parameter");
                if (shelf == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'shelf' parameter");
                if (desktop == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'desktop' parameter");
                if (effect == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'effect' parameter");
                if (effectEnum == null)
                    return Utils.genericEnqueueFail("Request failure: unknown 'effect' parameter " + effect);
                cmd = new CommandMountLayer(layerInt, shelfInt, desktopInt, effectEnum);
                break;
            case "show-layer":
                if (shelf == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'shelf' parameter");
                if (desktop == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'desktop' parameter");
                if (effect == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'effect' parameter");
                if (effectEnum == null)
                    return Utils.genericEnqueueFail("Request failure: unknown 'effect' parameter " + effect);
                cmd = new CommandShowLayer(shelfInt, desktopInt, effectEnum);
                break;
            case "empty-desktop":
                if (desktop == null)
                    return Utils.genericEnqueueFail();
                cmd = new CommandEmptyDesktop(desktopInt);
                break;
            case "show-sign":
                if (layer == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'layer' parameter");
                if (effect == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'effect' parameter");
                if (effectEnum == null)
                    return Utils.genericEnqueueFail("Request failure: unknown 'effect' parameter " + effect);
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
            case "arm-grip":
                boolean grip = true;
                String gripStr = params.get("grip");
                if ( (gripStr != null) && (gripStr.toLowerCase().startsWith("o")) )
                    grip = false;
                cmd = new CommandArmGrip(grip);
                break;
            case "program-controller":
                String name = params.get("name");
                cmd = new CommandProgramController((name != null) ? name.toLowerCase() : null);
                break;                
            case "position":
                String cabinetStr = params.get("cabinet");
                if (cabinetStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'cabinet' parameter");
                if (shelf == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'shelf' parameter");

                CabinetType cabinet;
                try {
                    cabinet = CabinetType.valueOf(cabinetStr.trim().toUpperCase());
                } catch (Exception e) {
                    return Utils.genericEnqueueFail("Unknown cabinet type " + cabinetStr);
                }
                
                PositionLookup pl = PositionLookup.getInstance();

                Position position  = pl.shelfToPosition(cabinet, shelfInt);
                if (position == null)
                    return Utils.genericEnqueueFail("Unable to locate position on cabinet " + cabinet.toString() + " shelf " + shelfInt);
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
                
                if (fromCabinetStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'fromcabinet' parameter");
                if (toCabinetStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'tocabinet' parameter");
                if (effectStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'effect' parameter");
                if (fromShelfStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'fromshelf' parameter");
                if (toShelfStr == null)
                    return Utils.genericEnqueueFail("Request failure: missing 'toshelf' parameter");
                
                CabinetType fromCabinet, toCabinet;
                try {
                    fromCabinet = CabinetType.valueOf(fromCabinetStr.trim().toUpperCase());
                } catch (Exception e) {
                    return Utils.genericEnqueueFail("Unknown fromcabinet type " + fromCabinetStr);
                }
                
                try {
                    toCabinet = CabinetType.valueOf(toCabinetStr.trim().toUpperCase());
                } catch (Exception e) {
                    return Utils.genericEnqueueFail("Unknown tocabinet type " + toCabinetStr);
                }
                
                pl = PositionLookup.getInstance();

                Position fromPosition = pl.shelfToPosition(fromCabinet, fromShelf);
                if (fromPosition == null)
                    return Utils.genericEnqueueFail("From position not found");

                Position toPosition = pl.shelfToPosition(toCabinet, toShelf);
                if (toPosition == null)
                    return Utils.genericEnqueueFail("To position not found");

                cmd = new CommandRoute(fromCabinet, fromPosition, toCabinet, toPosition, effectEnum);
                break;
            default:
                return Utils.genericEnqueueFail("Unknown command " + command);
        }
        
        // enqueue the command
        cmdq.add(queueInt, cmd, statusBool);
        
        // and return the ID in JSON
        JSONObject json = new JSONObject();
        json.put("id", cmd.getId());
        System.out.println("Enqueued request; ID is " + cmd.getId());
        return json.toString();
    }

    private final String STATUS_ID_KEY = "id";
    private final String STATUS_RETURN_STATUS_KEY = "status";
    private final String STATUS_ERROR_KEY = "error";

    public String status(Map<String, String> params)
    {
        String id;
        JSONObject json = new JSONObject();
        
        CommandStatus status = CommandStatus.UNKNOWN;

        
        if ((id = params.get(STATUS_ID_KEY)) != null) {
            // note: we must call getResult before getStatus, because getStatus may remove the
            // item from the list if it has encountered success/error
            Result result = cmdq.getResult(id);
            status = cmdq.getStatus(id);
            json.put(STATUS_RETURN_STATUS_KEY, status.toString());
            if (status == CommandStatus.ERROR) {
                if (result != null)
                    json.put(STATUS_ERROR_KEY, result.errorMessage);
            }
        }
        else
            json.put(STATUS_ERROR_KEY, "Missing id value for status request");

        return json.toString();
    }

    private final String CLEAR_QUEUE_ID_KEY = "queue";

    public String clearQueue(Map<String, String> params)
    {
        JSONObject json = new JSONObject();
        
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
        return json.toString();
    }

    private final String GET_INPUT_VAL_KEY = "key";
    private final String GET_RETURN_VAL_KEY = "value";

    public String getVar(Map<String, String> params)
    {
        JSONObject json = new JSONObject();
        String key = null;
        String val;

        key = params.get(GET_INPUT_VAL_KEY);
//        System.out.println("GET Printout: Key: " + key + " Value: " + vars.get(key));
        synchronized (vars) {
            val = vars.get(key);
        }
        
        if (val == null) 
            val = "";
        
        json.put(GET_RETURN_VAL_KEY, val);
        return json.toString();
    }

    private final String SET_VAR_READ_VAL_VALUE = "value";
    private final String SET_VAR_READ_VAL_KEY = "key";

    public String setVar(Map<String, String> params)
    {
        String key = null;
        JSONObject json = new JSONObject();
        String val;

        key = params.get(SET_VAR_READ_VAL_KEY);
        val = params.get(SET_VAR_READ_VAL_VALUE);

        if (key != null && val != null) {
            synchronized (vars) {
                vars.put(key, val);
            }
        }

        return json.toString();
    }
    
    public String inventory(Map<String, String> params) {
        Inventory inventory = Inventory.getInstance();
        return inventory.dump();
    }
}
