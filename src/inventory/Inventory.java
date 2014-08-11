/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import enums.DiskType;
import enums.ShelfUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kyle
 */
public class Inventory
{

    private static Inventory inventory = null;

    //ArrayLists
    List<Shelf> d1 = Collections.synchronizedList(new ArrayList<Shelf>());
    List<Shelf> d2 = Collections.synchronizedList(new ArrayList<Shelf>());
    List<Shelf> cachePoint = Collections.synchronizedList(new ArrayList<Shelf>());
//    List<Shelf> sign = Collections.synchronizedList(new ArrayList<Shelf>());

    /**
     * Returns an instance Inventory
     *
     * @return
     */
    public static Inventory getInstance()
    {
        if (inventory == null)
        {
            inventory = new Inventory();
        }
        return inventory;
    }

    /**
     * Private constructor
     */
    private Inventory()
    {
        for (int i = 0; i < 6; i++)
        {
            d1.add(new Shelf(DiskType.EMPTY));
            d2.add(new Shelf(DiskType.EMPTY));
        }

        for (int i = 0; i < 20; i++)
        {
            cachePoint.add(new Shelf(DiskType.EMPTY));
        }
    }

    public Shelf getShelf(ShelfUnit su, int index)
    {
        synchronized (shelfUnitToArrayList(su))
        {
            if (checkInBounds(index, shelfUnitToArrayList(su)))
            {
                return shelfUnitToArrayList(su).get(index);
            }
            return null;
        }

    }

    public DiskType getDisk(ShelfUnit su, int index)
    {
        synchronized (shelfUnitToArrayList(su))
        {
            if (checkInBounds(index, shelfUnitToArrayList(su)))
            {
                return shelfUnitToArrayList(su).get(index).getDisk();
            }
            return null;
        }
    }

    public boolean setDisk(ShelfUnit su, DiskType dt, int index)
    {
        synchronized (shelfUnitToArrayList(su))
        {
            if (checkInBounds(index, shelfUnitToArrayList(su)))
            {
                shelfUnitToArrayList(su).get(index).setDisk(dt);
                return true;
            }
            return false;
        }
    }

    private List<Shelf> shelfUnitToArrayList(ShelfUnit su)
    {
        if (su == ShelfUnit.D1)
        {
            return d1;
        }
        else if (su == ShelfUnit.D2)
        {
            return d2;
        }
        else if (su == ShelfUnit.CP)
        {
            return cachePoint;
        }        
        return null;
    }

    private boolean checkInBounds(int index, List<Shelf> l)
    {
        if (index >= 0 && index < l.size())
        {
            return true;
        }
        return false;

    }

}


