/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import enums.EffectType;
import enums.EnumShelfUnit;
import status.StatusObject;
import status.StatusQueue;

/**
 *
 * @author kyle
 */
public class CommandQueueWrapper
{

    private static CommandQueueWrapper cw = null;

    public static CommandQueueWrapper getInstance()
    {
        if (cw == null)
        {
            cw = new CommandQueueWrapper();
        }
        return cw;
    }

    private CommandQueueWrapper()
    {
        for (int i = 0; i < queues.length; i++)
        {
            queues[i] = new CommandQueue();
        }
    }

    private CommandQueue[] queues = new CommandQueue[3];
    private StatusQueue statusQueue = new StatusQueue();

    /**
     * Clear the command queue of the given num
     */
    public void clear(int num)
    {
        queues[num].clear();
    }

    /**
     * Adds an element to the given queue. Standard command not requiring a sign
     * or desktop.
     *
     * @param queueIndex index of the queue to add to
     * @param id element id
     * @param content element content
     * @param checkable if checkable with status
     */
    public void add(int queueIndex, long id, String content, boolean checkable)
    {
        queues[queueIndex].add(id, content);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
    }

    /**
     * Adds an element to the given queue. Standard command not requiring a sign
     * or desktop.
     *
     * @param queueIndex index of the queue to add to
     * @param id element id
     * @param content element content
     * @param checkable if checkable with status
     */
    public void add(int queueIndex, long id, EnumShelfUnit desktop, int desktopShelf, String content, boolean checkable)
    {
        queues[queueIndex].add(id, desktop, desktopShelf, content);//add(id, desktop, e);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
    }
    
    public void add(int queueIndex, long id, EnumShelfUnit desktop, int desktopShelf, int layer, EffectType e, boolean checkable)
    {
        queues[queueIndex].add(id, desktop, desktopShelf, layer, e);//(id, desktop, desktopShelf, content);//add(id, desktop, e);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
    }
    
    public void add(int queueIndex, long id, EnumShelfUnit desktop, String content, boolean checkable)
    {
        queues[queueIndex].add(id, desktop, content);//add(id, desktop, e);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
    }

    /**
     * Gets the first command in the list
     *
     * @return First command in list or null if none found
     */
    public QueueableItem getFirst(int index)
    {
        return queues[index].getFirst();
    }
    
    public CommandQueueStatus getStatus(String id)
    {
        long l = Long.parseLong(id);
        return statusQueue.getStatus(l);
    }
}
