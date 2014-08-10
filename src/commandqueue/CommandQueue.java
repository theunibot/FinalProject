/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandqueue;

import enums.EffectType;
import enums.EnumShelfUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kyle
 */
public class CommandQueue
{

    private List<QueueableItem> queue = Collections.synchronizedList(new ArrayList<QueueableItem>());

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
     * Add a Command String to the Queue
     *
     * @param s Command String
     */
    public void add(long id, String s)
    {
        synchronized (queue)
        {
            queue.add(new QueueableItem(id, s));
        }
    }

    public void add(long id, EnumShelfUnit desktop, String content)
    {
        synchronized (queue)
        {
            queue.add(new QueueableItem(id, desktop, content));
        }
    }
    
    public void add(long id, EnumShelfUnit desktop, int desktopShelf, String content)
    {
        synchronized (queue)
        {
            queue.add(new QueueableItem(id, desktop, desktopShelf, content));
        }
    }
    
    /**
     * Add a move command to the queue
     *
     * @param id
     * @param desktop
     * @param desktopShelf 
     * @param layer
     * @param e
     */
    public void add(long id, EnumShelfUnit desktop, int desktopShelf, int layer, EffectType e)
    {
        synchronized (queue)
        {
            queue.add(new QueueableItem(id, desktop, desktopShelf, layer, e));
        }
    }

    /**
     * Gets the first command in the list
     *
     * @return First command in list or null if none found
     */
    public QueueableItem getFirst()
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

    public QueueableItem getElement(int id)
    {
        synchronized (queue)
        {

        }
        return null;
    }

}
