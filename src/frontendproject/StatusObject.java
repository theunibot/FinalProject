/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package frontendproject;

/**
 *
 * @author kyle
 */
public class StatusObject
{
    private long id;
    private CommandQueueStatus status;

    public StatusObject(long id, CommandQueueStatus status)
    {
        this.id = id;
        this.status = status;
    }

    public long getId()
    {
        return id;
    }

    public CommandQueueStatus getStatus()
    {
        return status;
    }
    
    
}
