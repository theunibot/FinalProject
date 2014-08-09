/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backendproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyle
 */
public class GeneralUtils
{
    public static void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(GeneralUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static double strToDbl(String s)
    {
        return Double.parseDouble(s);
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
}
