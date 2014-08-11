/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import commandqueue.CommandQueueStatus;
import java.util.ArrayList;

/**
 *
 * @author kyle
 */
public class StatusQueue
{

    private ArrayList<StatusObject> statusQueue = null;

    public StatusQueue()
    {
        statusQueue = new ArrayList<StatusObject>();
    }

    public void add(StatusObject so)
    {
        statusQueue.add(so);
        if (main.Main.DEBUG)
        {
            System.out.println("Status Queue so far:");
            for (StatusObject so2 : statusQueue)
            {
                System.out.println("ID: " + so2.getId() + " Status: " + so2.getStatus());
            }
        }
    }

    public CommandQueueStatus getStatus(long id)
    {
//        for (StatusObject so : statusQueue)
        for (int i = 0; i < statusQueue.size(); i++)
        {
            StatusObject so = statusQueue.get(i);
            if (so.getId() == id)
            {
                CommandQueueStatus status = so.getStatus();
                if (status.equals(CommandQueueStatus.COMPLETE))
                {
                    statusQueue.remove(i);
                }
                return status;
            }
        }
        return CommandQueueStatus.UNKNOWN;
    }

    public void setStatus(long id, CommandQueueStatus status)
    {
        for (int i = 0; i < statusQueue.size(); i++)
        {
            StatusObject so = statusQueue.get(i);
            if (so.getId() == id)
            {
                so.setStatus(status);
            }
        }
    }

}
