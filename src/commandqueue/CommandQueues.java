/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import enums.CommandStatus;
import commands.CommandInterface;
import enums.EffectType;
import enums.CabinetType;
import java.util.concurrent.Semaphore;

/**
 *
 * @author kyle
 */
public class CommandQueues
{

    private static CommandQueues cw = null;

    private CommandQueue[] queues = new CommandQueue[3];
    private CommandQueue statusQueue = new CommandQueue();
    private Semaphore queueSemaphore = new Semaphore(0);
    private boolean killThread = false;
    private int roundRobin = 1;

    public static CommandQueues getInstance()
    {
        if (cw == null)
        {
            cw = new CommandQueues();
        }
        return cw;
    }

    private CommandQueues()
    {
        for (int i = 0; i < queues.length; i++)
        {
            queues[i] = new CommandQueue();
        }
    }

    /**
     * Clear the command queue of the given num
     * 
     * @param int with the queue index to clear
     */
    public void clear(int num)
    {
        System.out.println("Clearing queue " + num);
        synchronized (this) {
            // loop while anything is in this queue
            while (true) {
                // remove top element
                CommandInterface cmd = queues[num].pop();
                if (cmd == null)
                    break;
                System.out.println("Clearing unexecuted command: " + cmd.details());
                
                // and decrement semaphore counter accordingly
                queueSemaphore.tryAcquire();
            }
            
            // now do it again, this time for the status queue
            while (true) {
                // remove top element
                CommandInterface cmd = statusQueue.pop();
                if (cmd == null)
                    break;
                System.out.println("Clearing status command: " + cmd.details());
            }
        }
    }

    /**
     * Adds an element to the given queue. Standard command not requiring a sign
     * or desktop.
     *
     * @param queueIndex index of the queue to add to
     * @param cmd command to add to the queue
     * @param checkable if checkable with status
     */
    public void add(int queueIndex, CommandInterface cmd, boolean checkable)
    {
        synchronized (this) {
            cmd.setQueueIndex(queueIndex);
            queues[queueIndex].add(cmd);
            if (checkable) {
                statusQueue.add(cmd);
            }
            // signal that we have something to do
            queueSemaphore.release();
        }
    }

    /**
     * Gets the next command in the list (blocking until command available); leaves item on queue (see pop)
     *
     * @return Next CommandInterface from the queues, following priority rules.  Returns
          null if the thread should be killed
     */
    public CommandInterface pop()
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
        
        synchronized (this) {
            // first, see if we should kill
            if (killThread)
                return null;
            // check if anything in the high priority queue
            CommandInterface cmd;
            cmd = queues[0].pop();
            if (cmd != null)
                return cmd;

            // now try the next queue based on round-robin
            for (int scan = 0; scan < 2; ++scan) {
                // switch robin
                roundRobin++;
                // does this one have something for us?
                int queueIndex = roundRobin % 2 + 1;
                cmd = queues[queueIndex].pop();
                if (cmd != null)
                    return cmd;
            }
            // we got nothing.  
            System.out.println("WARNING: CommandQueueWrapper.pop found nothing; THIS SHOULD NEVER HAPPEN!");
            return null;
        }
    }

    /**
     * Pushes the specified command to the head of the queue; used to re-enqueue an item when it has not completed
     */
    public void push(CommandInterface cmd) {
        synchronized (this) {
            queues[cmd.getQueueIndex()].add(cmd);
            // signal that we have something to do
            queueSemaphore.release();
        }
    }
    
    /**
     * Gets status of the specific queued element; removes from list when complete
     * 
     * @param id Id of the queued item to check
     * @return Status of the command
     */
    public CommandStatus getStatus(String id)
    {
        synchronized (this) {
            long l = Long.parseLong(id);
            CommandInterface cmd = statusQueue.getById(l);
            if (cmd == null)
                return CommandStatus.UNKNOWN;
            CommandStatus status = cmd.getStatus();
            // if complete, remove it from memory
            if (status == CommandStatus.COMPLETE)
                statusQueue.remove(cmd);
            return cmd.getStatus();
        }
    }
    
    /**
     * Sends a kill message back to the controlling thread, via the pop call
     */
    public void kill() 
    {
        killThread = true;
        // signal that we have something to do
        queueSemaphore.release();        
    }
    
    /**
     * Determines if a shutdown request is pending
     * 
     * @return true if killed, false if not
     */
    public boolean killed() {
        return killThread;
    }
}
