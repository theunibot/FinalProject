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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

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

    public static double inToMm(double in)
    {
        return (25.4d * in);
    }
    
    public static double mmToIn(double mm)
    {
        return (mm / 25.4d);
    }

    public static double zInToMm(double z)
    {
        return inToMm(z) - 303.0d - 9.5d;
    }
    
    public static double zMmToIn(double z)
    {
        return mmToIn(z + 303.0d + 9.5d);
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
        return enm.toString();
    }

    public static RouteEffectType effectStringToEffectType(String s)
    {
        try {
            return RouteEffectType.valueOf(s.trim().toUpperCase());
        } catch (Exception e) {
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

    public static String genericEnqueueFail()
    {
        return Utils.genericEnqueueFail("\"Generic Enqueue Fail\"");
    }

    public static String genericEnqueueFail(String error)
    {
        JSONObject json = new JSONObject();
        System.err.println("GenericEnqueueFail: " + error);
        json.put("error", error);
        return json.toString();
    }

    public static String genericStatusFail()
    {
        JSONObject json = new JSONObject();
        json.put("status", "unknown");
        return json.toString();
    }

    public static CabinetType shelfToCPCabinet(int shelf) {
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
            default:
                return CabinetType.UNKNOWN;
        }
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
    
    /**
     * Generates an MD5 hash for a string value, limited to a specific length
     * @param input String to generate hash for
     * @param length Number of characters to return (32 for a full MD5)
     * 
     * @return Hash string
     */
    public static String hash(String input, int length) {
        // get the message digest
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // a legit MD5 has 32 characters, so make it legal
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Unable to get an MD5 message digest");
            return null;
        }
    }

}
