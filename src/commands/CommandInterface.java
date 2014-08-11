/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import enums.EffectType;
import enums.ShelfType;
import enums.CommandType;

/**
 * Base interface class for all robot commands 
 */
public class CommandInterface
{
    private static long uniqueId = 1;
    private long id = uniqueId++;
    
    public long getId()
    {
        return id;
    }
    
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        
        result.append("Command Unknown (base CommandInterface implementation)");
        
        return result.toString();
    }
}
