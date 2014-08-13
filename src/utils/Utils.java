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
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package utils;

import enums.CommandStatus;
import enums.EffectType;
import enums.CabinetType;
import server.KVObj;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Utils
{

    private static long idItr = 0;

    /**
     * Get 64 bit ID
     *
     * @return 64 bit ID
     */
    /*
     public static long getID()
     {
     if (idItr == Long.MAX_VALUE - 1)//roll over instead of overflow
     {
     idItr = 0;
     }
     idItr++;
     return idItr - 1;
     }
     */        

    public static void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double strToDbl(String s)
    {
        return Double.parseDouble(s);
    }

    /**
     * Convert string to integer without throwing errors
     *
     * @param s string to convert
     * @return converted integer, or -1 if unable to convert
     */
    public static int strToInt(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static String zInToMmStr(String z)
    {

        return formatDouble((double) (inToMm(strToDbl(z)) - 303.0d));
    }

    public static String xyInToMmStr(String in)
    {
        return formatDouble(25.4d * strToDbl(in));
    }

    public static double inToMm(double in)
    {
        return (25.4d * in);
    }

    public static String formatDouble(double d)
    {

        return String.format("%.1f", d);
    }

    /**
     * Converts the CommandStatus enum to a string for output.
     *
     * @param enum Enum to convert to String
     * @return String version
     */
    public static String commandQueueStatusEnumToString(CommandStatus enm)
    {
        if (enm == CommandStatus.COMPLETE)
        {
            return "complete";
        }
        else if (enm == CommandStatus.EXECUTING)
        {
            return "executing";
        }
        else if (enm == CommandStatus.PENDING)
        {
            return "pending";
        }
        else if (enm == CommandStatus.UNKNOWN)
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
        if (s == null)
        {
            return null;
        }
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

    public static CabinetType stringToEnumShelfType(String s)
    {
        if (s.trim().equals("1"))
        {
            return CabinetType.D1;
        }
        else if (s.trim().equals("2"))
        {
            return CabinetType.D2;
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
    
    public static CabinetType shelfToCabinet(int shelf) {
        // 00 = lower left, 30 = upper left
        // so second digit is cabinet...
        switch (shelf % 10) {
            case 0:
            case 1:
                return CabinetType.CPL;
            case 2:
                return CabinetType.CPM;
            case 3:
            case 4:
                return CabinetType.CPR;
        }
        return CabinetType.UNKNOWN;
    }

    public static int shelfToIndex(int shelf)
    {
        int ret = shelf;
        if (shelf < 10)
        {
            ret = shelf;
        }
        else if (shelf < 15)
        {
            ret = (shelf - 5);
        }
        else if (shelf < 25)
        {
            ret = (shelf - 10);
        }
        else if (shelf < 35)
        {
            ret = (shelf - 15);
        }
        return (ret);
    }
    
    static Random r = new Random(System.currentTimeMillis());
    
    /**
     * Returns a rand int from zero (inclusive) to max (exclusive)
     * @param max
     * @return 
     */
    public static int getRandInt(int max)
    {
        return r.nextInt(max);
    }

}
