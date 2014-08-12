/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import enums.*;
import route.Cartesian;
import utils.Result;

/**
 * Base interface class for all robot commands 
 */
public abstract class CommandInterface
{
    private static long uniqueId = 1;
    private long id = uniqueId++;
    private CommandStatus status = CommandStatus.PENDING;
    private int queueIndex = -1;

    /**
     * Returns the unique ID of the command
     * 
     * @return ID of the command
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * Executes the command to the robot arm
     * 
     * @param cabinetType of cabinet at start; should be updated to reflect type at end
     * @return success (true), or failure (false) 
     */
    public abstract Result execute(CommandArguments args);
    
    /**
     * Provide a debugging string of details about this command
     * 
     * @return String with the summary
     */
    public abstract String details();

    /**
     * Get the current status of this command
     * 
     * @return CommandStatus with the current status
     */
    public CommandStatus getStatus() {
        return this.status;
    }
    
    /**
     * Set the current status of this command
     * 
     * @param status Current status of this command; set by CommandProcessor
     */
    public void setStatus(CommandStatus status) {
        this.status = status;
    }
    
    
    /**
     * By default, commands will not execute if there is an error outstanding.  If a command
     * should execute even when there are errors, if should override this method and return
     * true.
     * 
     * @return false if errors should not be ignored, true if they should
     */
    public boolean ignoreErrors() {
        return false;
    }
    
    /**
     * By default, once an error occurs it will remain set even if some commands (those that override ignoreErrors)
     * are successful.  Only commands that support successClearsError (overrides it and returns true) will actually
     * clear the error.  Likely, this will only be used by Calibrate.
     * 
     * @return false if errors should not be cleared, true if they should
     */
    public boolean successClearsError() {
        return false;
    }
    
    /**
     * Set the queue index for this command; used to track which queue this command is executing in
     * 
     * @param queueIndex Index of the queue (0=priority, 1/2 = desktop queues)
     */
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    /**
     * Returns the queue index used by this command
     * 
     * @return int with queue index (0=priority; 1/2 = desktop queues)
     */
    public int getQueueIndex() {
        return this.queueIndex;
    }
    
}
