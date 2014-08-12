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
     * @return success (true), or failure (false) 
     */
    public abstract CommandCompletion execute();
    
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
     * Set the queue index for this command; used to track which queue this command is executing in
     * 
     * @param queueIndex Index of the queue (0=priority, 1/2 = desktop queues)
     */
    public void setQueueIndex(Integer queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    /**
     * Returns the queue index used by this command
     * 
     * @return Integer with queue index (0=priority; 1/2 = desktop queues)
     */
    public int getQueueIndex() {
        return this.queueIndex;
    }
    
}
