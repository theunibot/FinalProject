/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import commands.CommandInterface;
import enums.EffectType;
import enums.ShelfType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kyle
 */
public class CommandQueue
{

    private List<CommandInterface> queue = Collections.synchronizedList(new ArrayList<CommandInterface>());

    /**
     * Clear the command queue
     */
    public void clear()
    {
        synchronized (queue)
        {
            queue.clear();
        }
    }
    
    /**
     * add a command to the queue
     * 
     * @param cmd - command interface to add 
     */
    public void add(CommandInterface cmd) {
        synchronized (queue) {
            queue.add(cmd);
        }
    }

    /**
     * Gets the first command in the list
     *
     * @return First command in list or null if none found
     */
    public CommandInterface getFirst()
    {
        synchronized (queue)
        {
            if (queue.size() > 0)
            {
                return queue.get(0);
            }
            return null;
        }
    }

    /**
     * Locate a specific command in the queue by Id
     * 
     * @param id
     * @return 
     */
    public CommandInterface getById(long id)
    {
        synchronized (queue)
        {
            for (CommandInterface q : queue)
            {
                if(q.getId() == id)
                {
                    return q;
                }
            }
        }
        return null;
    }

}
