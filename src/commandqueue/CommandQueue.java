/*
    This file is part of theunibot.

    theunibot is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    theunibot is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package commandqueue;

import commands.CommandInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
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
     * Returns number of items waiting in this queue
     * 
     * @return count of items waiting
     */
    public int queueDepth() {
        return queue.size();
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
