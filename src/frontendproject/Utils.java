/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontendproject;

import java.util.ArrayList;

/**
 *
 * @author kyle
 */
public class Utils
{

    private static long idItr = 0;

    /**
     * Get 64 bit ID
     *
     * @return 64 bit ID
     */
    public static long getID()
    {
        if (idItr == Long.MAX_VALUE - 1)//roll over instead of overflow
        {
            idItr = 0;
        }
        idItr++;
        return idItr - 1;
    }

    /**
     * Converts the CommandQueueStatus enum to a string for output.
     *
     * @param enum Enum to convert to String
     * @return String version
     */
    public static String commandQueueStatusEnumToString(CommandQueueStatus enm)
    {
        if (enm == CommandQueueStatus.COMPLETE)
        {
            return "complete";
        }
        else if (enm == CommandQueueStatus.EXECUTING)
        {
            return "executing";
        }
        else if (enm == CommandQueueStatus.PENDING)
        {
            return "pending";
        }
        else if (enm == CommandQueueStatus.UNKNOWN)
        {
            return "unknown";
        }
        else
        {
            return null;
        }
    }
    
    public static EffectType effectStringToEffectType(String s)
    {
        if (s.equals("fancy"))
        {
            return EffectType.FANCY;
        }
        else if (s.equals("efficient"))
        {
            return EffectType.EFFICIENT;
        }
        else
        {
            return null;
        }
    }

    public static EnumShelfUnit stringToEnumShelfUnit(String s)
    {
        if(s.trim().equals("1"))
        {
            return EnumShelfUnit.D1;
        }
        else if(s.trim().equals("2"))
        {
            return EnumShelfUnit.D2;
        }
        return null;
    }
    
    
    private static ArrayList<KVObj> response;

    public static String genericEnqueueFail()
    {
        return Utils.genericEnqueueFail("Generic Enqueue Fail");
    }

    public static String genericEnqueueFail(String error)
    {
        response = new ArrayList<KVObj>();
        response.add(new KVObj("id", String.valueOf(Utils.getID())));
        response.add(new KVObj("error", error));
        return Utils.buildJSON(response);
    }

    public static String genericStatusFail()
    {
        response = new ArrayList<KVObj>();
        response.add(new KVObj("status", "unknown"));
        return Utils.buildJSON(response);
    }

    public static String buildJSON(ArrayList<KVObj> kvObjs)
    {
        StringBuilder b = new StringBuilder();
        KVObj kvObj;
        b.append("{");
        for (int i = 0; i < kvObjs.size() - 1; i++)//all but last set of vals
        {
            kvObj = kvObjs.get(i);
            if (kvObj != null)
            {
                b.append(kvObj.getKey() + ":" + kvObj.getValue() + ",");
            }
        }
        kvObj = kvObjs.get(kvObjs.size() - 1);
        b.append(kvObj.getKey() + ":" + kvObj.getValue() + "}");
        return b.toString();
    }

}
