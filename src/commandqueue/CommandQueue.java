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
        queue.clear();
    }
    
    /**
     * add a command to the queue
     * 
     * @param cmd - command interface to add 
     */
    public void add(CommandInterface cmd) {
        queue.add(cmd);
    }

    /**
     * Gets the first command in the list
     *
     * @return First command in list or null if none found
     */
    public CommandInterface pop()
    {
        if (queue.size() > 0)
        {
            CommandInterface cmd = queue.get(0);                
            queue.remove(cmd);
            return cmd;
        }
        return null;
    }

    /**
     * Pushes an item onto the head of the queue, making it the next one to execute
     * 
     * @param cmd Command to add to the queue head
     */
    public void push(CommandInterface cmd) {
        queue.add(0, cmd);
    }

    /**
     * Locate a specific command in the queue by Id
     * 
     * @param id
     * @return 
     */
    public CommandInterface getById(long id)
    {
        for (CommandInterface q : queue)
        {
            if(q.getId() == id)
            {
                return q;
            }
        }
        return null;
    }

    public void remove(CommandInterface cmd) {
        queue.remove(cmd);
    }
}
