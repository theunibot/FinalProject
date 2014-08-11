/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commandqueue;

import enums.CommandStatus;

/**
 *
 * @author kyle
 */
public class StatusObject
{
    private long id;
    private CommandStatus status;

    public StatusObject(long id, CommandStatus status)
    {
        this.id = id;
        this.status = status;
    }

    public long getId()
    {
        return id;
    }

    public CommandStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(CommandStatus status)
    {
        this.status = status;
    }
    
    
}
