/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import enums.EffectType;
import enums.EnumShelfUnit;
import java.util.concurrent.Semaphore;

/**
 *
 * @author kyle
 */
public class CommandQueueWrapper
{

    private static CommandQueueWrapper cw = null;

    private CommandQueue[] queues = new CommandQueue[3];
    private StatusQueue statusQueue = new StatusQueue();
    private Semaphore queueSemaphore = new Semaphore(0);
    private Boolean killThread = false;
    private int roundRobin = 1;

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

    /**
     * Clear the command queue of the given num
     */
    public void clear(int num)
    {
        // note that we are not decrementing the counted semaphore (queueSemaphore), so this will
        // cause excess sempahore permits that will get drained by the CommandProcessor
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
        // signal that we have something to do
        queueSemaphore.release();
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
        // signal that we have something to do
        queueSemaphore.release();
    }

    public void add(int queueIndex, long id, EnumShelfUnit desktop, int desktopShelf, int layer, EffectType e, boolean checkable)
    {
        queues[queueIndex].add(id, desktop, desktopShelf, layer, e);//(id, desktop, desktopShelf, content);//add(id, desktop, e);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
        // signal that we have something to do
        queueSemaphore.release();
    }

    public void add(int queueIndex, long id, EnumShelfUnit desktop, String content, boolean checkable)
    {
        queues[queueIndex].add(id, desktop, content);//add(id, desktop, e);
        if (checkable)
        {
            statusQueue.add(new StatusObject(id, CommandQueueStatus.PENDING));
        }
        // signal that we have something to do
        queueSemaphore.release();
    }

    /**
     * Gets the next command in the list (blocking until command available)
     *
     * @return Next QueueableItem from the queues, following priority rules.  Returns
     *          null if the thread should be killed
     */
    public QueueableItem getItem()
    {
        // safety valve ... don't sleep if we know a kill is waiting
        if (killThread)
            return null;
        // now wait on the semaphore
        try {
            queueSemaphore.acquire();
        } catch (InterruptedException e) {
            return null;
        }
        // first, see if we should kill
        if (killThread)
            return null;
        // check if anything in the high priority queue
        QueueableItem cmd;
        cmd = queues[0].getFirst();
        if (cmd != null)
            return cmd;
        // now try the next queue based on round-robin
        for (int scan = 0; scan < 2; ++scan) {
            // switch robin
            roundRobin++;
            // does this one have something for us?
            cmd = queues[roundRobin % 2 + 1].getFirst();
            if (cmd != null)
                return cmd;
        }
        // we got nothing.  
        System.out.println("CommandQueueWrapper.getItem found nothing; maybe due to queue clear?");
        return null;
    }

    /**
     * Gets status of the specific queued element; removes from list when complete
     * 
     * @param id Id of the queued item to check
     * @return Status of the command
     */
    public CommandQueueStatus getStatus(String id)
    {
        long l = Long.parseLong(id);
        return statusQueue.getStatus(l);
    }
    
    /**
     * Sends a kill message back to the controlling thread, via the getItem call
     */
    public void kill() 
    {
        killThread = true;
        // signal that we have something to do
        queueSemaphore.release();        
    }
}