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
package utils;

import enums.CabinetType;
import enums.CommandStatus;
import enums.RouteEffectType;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.KVObj;

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

    public static RouteEffectType effectStringToEffectType(String s)
    {
        if (s == null)
        {
            return null;
        }
        if (s.equals("fancy"))
        {
            return RouteEffectType.FANCY;
        }
        else if (s.equals("efficient"))
        {
            return RouteEffectType.EFFICIENT;
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
    
    /**
     * Determines the index and cabinet given a cabinet and shelf.  When passed a desktop, this method doesn't do
     * much, as a desktop shelf and a desktop shelfIndex are the same.  When passed a CachePoint shelf, it converts
     * the 00-34 numbering scheme (for a full CP) into a CPL/CPM/CPR index (0-7 for CPL/CPR, 0-3 for CPM).
     * 
     * @param cabinet Cabinet type being looked at (if UNKNOWN it will determine the CP type)
     * @param shelf shelf to be converted
     * @return ShelfLocation with cabinet, shelf and index
     */
    public static ShelfLocation shelfToShelfLocation(CabinetType cabinet, int shelf) {
        Object x = new Object();
        Integer y = new Integer(3);
        ShelfLocation sl = new ShelfLocation();
        sl.shelf = shelf;
        
        // if we don't know the cabinet type, assume it is a CP and go figure it out from the shelf number
        if (cabinet == CabinetType.UNKNOWN) {
            // 00 = lower left, 30 = upper left
            // so second digit is cabinet...
            switch (shelf % 10) {
                case 0:
                case 1:
                    sl.cabinet = CabinetType.CPL;
                    break;
                case 2:
                    sl.cabinet = CabinetType.CPM;
                    break;
                case 3:
                case 4:
                    sl.cabinet = CabinetType.CPR;
                    break;
                default:
                    sl.cabinet = CabinetType.UNKNOWN;
                    break;
            }
        } else 
            sl.cabinet = cabinet;
        
        switch (sl.cabinet) {
            case CPL:
                // CPL is 00-30 and 01-31, which will be index 0-3 and 4-7
                sl.shelfIndex = (shelf / 10) + ((shelf % 10) * 4);
                break;
            case CPM:
                // CPM is 02-32
                sl.shelfIndex = (shelf / 10);
                break;
            case CPR:
                // CPR is 03-33 and 04-34
                sl.shelfIndex = (shelf / 10) + (((shelf % 10) - 3) * 4);
                break;
            default:
                sl.shelfIndex = shelf;
                break;
        }

        return sl;
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
