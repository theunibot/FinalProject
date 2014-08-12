/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import enums.*;

/**
 * Base interface class for all robot commands 
 */
public abstract class CommandInterface
{
    private static long uniqueId = 1;
    private long id = uniqueId++;
    
    public long getId()
    {
        return id;
    }
    
    /**
     * Executes the command to the robot arm
     * 
     * @return success (true), or failure (false) 
     */
    public abstract CommandCompletion execute();
    
    /**
     * Provide a debugging string of details about this command
     * 
     * @return String with the summary
     */
    public abstract String details();
}
