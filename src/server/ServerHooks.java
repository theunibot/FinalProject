/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import utils.Utils;
import commandqueue.CommandQueueWrapper;
import enums.CommandStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import commands.*;

/**
 *
 * @author kyle
 */
public class ServerHooks
{

    //Objects
    private static ServerHooks s = null;

    //used in get/set vars calls
    private static Map<String, String> vars = Collections.synchronizedMap(new LinkedHashMap<String, String>());

    private CommandQueueWrapper cmdq = CommandQueueWrapper.getInstance();

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

    private volatile boolean errorState = false;

    public String enqueue(Map<String, String> params)
    {
        response.clear();
        String command = "";
        if ((command = params.get("command")) != null)
        {
            
            command = command.toLowerCase();    //convert the command to lowercase, case doesn't matter
            System.out.println("COMMAND: " + command);
            if (command.equals("mount-layer") && !errorState)
            {
                System.out.println("MountLayer called");
                String queue, checkableStr, layer, shelf, desktop, effect = "";
                if ((queue = params.get("queue")) == null)
                {
                    System.out.println("queue not found");
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    System.out.println("status not found");
                    return Utils.genericEnqueueFail();
                }
                if ((layer = params.get("layer")) == null)
                {
                    System.out.println("layer not found");
                    return Utils.genericEnqueueFail();
                }
                if ((shelf = params.get("shelf")) == null)
                {
                    System.out.println("shelf not found");
                    return Utils.genericEnqueueFail();
                }
                if ((desktop = params.get("desktop")) == null)
                {
                    System.out.println("desktop not found");
                    return Utils.genericEnqueueFail();
                }
                if ((effect = params.get("effect")) == null)
                {
                    System.out.println("effect not found");
                    return Utils.genericEnqueueFail();
                }
                //no fails
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }

                int queueint = Integer.parseInt(queue);
                CommandMountLayer cmd = new CommandMountLayer(Utils.stringToEnumShelfType(shelf), Integer.parseInt(shelf), Integer.parseInt(layer), Utils.effectStringToEffectType(effect));
                cmdq.add(queueint, cmd, checkable);
                
                //response
                response.add(new KVObj("id", String.valueOf(cmd.getClass())));
                return Utils.buildJSON(response);

            }
            else if (command.equals("replace-layer") && !errorState)
            {
                String queue, checkableStr, layer, shelf, desktop, effect = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((layer = params.get("layer")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((shelf = params.get("shelf")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((desktop = params.get("desktop")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((effect = params.get("effect")) == null)
                {
                    return Utils.genericEnqueueFail();
                }

                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);

                return null;
            }
            else if (command.equals("replace-layer") && !errorState)
            {
                String queue, checkableStr, shelf, desktop, effect = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((shelf = params.get("shelf")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((desktop = params.get("desktop")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((effect = params.get("effect")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                //no fails

                //no fails
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);
                return null;
            }
            else if (command.equals("empty-desktop") && !errorState)
            {
                String queue, checkableStr, desktop = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((desktop = params.get("desktop")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                //no fails
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }

                int queueint = Integer.parseInt(queue);
                return null;
            }
            else if (command.equals("show-sign") && !errorState)
            {
                String queue, checkableStr, layer, effect = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((layer = params.get("layer")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((effect = params.get("effect")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                //no fails
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);

                return null;
            }
            else if (command.equals("arm-home"))
            {
                String queue, checkableStr = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);
                return null;
            }
            else if (command.equals("arm-calibrate"))
            {
                String queue, checkableStr = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);
                return null;
            }
            else if (command.equals("arm-energize"))
            {
                String queue, checkableStr = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);
                return null;
            }
            else if (command.equals("arm-de-energize"))
            {
                String queue, checkableStr = "";
                if ((queue = params.get("queue")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                if ((checkableStr = params.get("status")) == null)
                {
                    return Utils.genericEnqueueFail();
                }
                boolean checkable = false;
                if (Integer.parseInt(checkableStr) == 1)
                {
                    checkable = true;
                }
                int queueint = Integer.parseInt(queue);
                return null;
            }
            else//unknown command type
            {
                return Utils.genericEnqueueFail();
            }
        }
        else
        {
            return Utils.genericEnqueueFail();
        }
    }

    private final String STATUS_ID_KEY = "id";
    private final String STATUS_RETURN_STATUS_KEY = "status";
    private final String STATUS_ERROR_KEY = "error";

    public String status(Map<String, String> params)
    {
        response.clear();
        String id;

        CommandStatus status = CommandStatus.UNKNOWN;

        
        if ((id = params.get(STATUS_ID_KEY)) != null)
        {
            status = cmdq.getStatus(id);
            response.add(new KVObj(STATUS_RETURN_STATUS_KEY, Utils.commandQueueStatusEnumToString(status)));
        }
        else
        {
            response.add(new KVObj(STATUS_ERROR_KEY, "error TBD"));
        }
        return Utils.buildJSON(response);
    }

    private final String CLEAR_QUEUE_ID_KEY = "queue";

    public String clearQueue(Map<String, String> params)
    {
        response.clear();
        String strVal = null;
        if ((strVal = params.get(CLEAR_QUEUE_ID_KEY)) == null)
        {
            int intVal = -1;
            try
            {
                intVal = Integer.parseInt(strVal);
            }
            catch (NumberFormatException ignored)
            {
            }
            //if value is legit
            if (intVal != -1)
            {
                cmdq.clear(intVal);
            }
        }
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
}
