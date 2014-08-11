/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author kyle
 */
public class GeneralUtils
{
    static int ret;
    public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter number. ");
    int slot = sc.nextInt();
    SlotToIndex(slot);
    
}
    static int SlotToIndex(int slot){
        if (slot < 10)
            ret = slot;
        else if (slot == 10)
            ret = 5;
        else if (slot == 11)
            ret = 6;
        else if (slot == 12)
            ret = 7;
        else if (slot == 13)
            ret = 8;
        else if (slot == 14)
            ret = 9;
        else if (slot == 20)
            ret = 10;
        else if (slot == 21)
            ret = 11;
        else if (slot == 22)
            ret = 12;
        else if (slot == 23)
            ret = 13;
        else if (slot == 24)
            ret = 14;
        else if (slot == 30)
            ret = 15;
        else if (slot == 31)
            ret = 16;
        else if (slot == 32)
            ret = 17;
        else if (slot == 33)
            ret = 18;
        else if (slot == 34)
            ret = 19;
    return (ret);
    }
}