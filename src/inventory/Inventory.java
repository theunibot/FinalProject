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
package inventory;

import enums.CabinetType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import server.KVObj;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class Inventory
{
    private static Inventory singleton = null;
    // define inventory.  The second Hashtable is Shelf as key, and Count of discs as value.
    private HashMap<CabinetType, HashMap<Integer, InventoryShelf>> inventory;
      
    /**
     * Initialize inventory with desktops empty and CachePoint having two layers in every slot
     */
    private Inventory() {
        resetInventory();
    }
    
    /**
     * Resets inventory to the starting values
     */
    public void resetInventory() {    
        // build the base inventory object
        inventory = new HashMap<>();
        // now add the desktops
        inventory.put(CabinetType.D1, new HashMap<Integer, InventoryShelf>());
        inventory.put(CabinetType.D2, new HashMap<Integer, InventoryShelf>());
        for (int i = 0; i < 6; i++) {
            inventory.get(CabinetType.D1).put(i, new InventoryShelf(0));
            inventory.get(CabinetType.D2).put(i, new InventoryShelf(0));
        }
        // and add the CachePoint
        inventory.put(CabinetType.CPL, new HashMap<Integer, InventoryShelf>());
        inventory.put(CabinetType.CPM, new HashMap<Integer, InventoryShelf>());
        inventory.put(CabinetType.CPR, new HashMap<Integer, InventoryShelf>());
        for (int i = 0; i < 5; i++) {
            inventory.get(CabinetType.CPL).put(i, new InventoryShelf(2, i));
            inventory.get(CabinetType.CPL).put(i + 10, new InventoryShelf(2, i + 10));
            inventory.get(CabinetType.CPM).put(i + 20, new InventoryShelf(2, i + 20));
            inventory.get(CabinetType.CPR).put(i + 30, new InventoryShelf(2, i + 30));
            inventory.get(CabinetType.CPR).put(i + 40, new InventoryShelf(2, i + 40));
        }
    }
    
        
    /**
     * Returns an instance Inventory
     *
     * @return
     */
    public static Inventory getInstance()
    {
        if (singleton == null)
        {
            singleton = new Inventory();
        }
        return singleton;
    }

    
    /**
     * Record the moving of a disc from one cabinet to another
     * 
     * @param fromType Cabinet moving from
     * @param fromShelf Shelf in cabinet
     * @param toType Cabinet moving to
     * @param toShelf Shelf in cabinet
     * 
     * @return Result indicating success/failure
     */
    public Result moveDisc(CabinetType fromType, int fromShelf, CabinetType toType, int toShelf) {
        // make sure the fromType and toType are known
        if (!inventory.containsKey(fromType))
            return new Result("Unable to locate cabinet " + fromType.toString() + " in inventory");
        if (!inventory.containsKey(toType))
            return new Result("Unable to locate cabinet " + toType.toString() + " in inventory");
        // validate that this request is legit
        InventoryShelf fromInventory = inventory.get(fromType).get(fromShelf);
        if (fromInventory == null)
            return new Result("Unable to locate cabinet " + fromType.toString() + " shelf " + fromShelf + " in inventory");
        if (fromInventory.count == 0)
            return new Result("Inventory found no discs in cabinet " + fromType.toString() + " shelf " + fromShelf);

        InventoryShelf toInventory = inventory.get(toType).get(toShelf);
        if (toInventory == null)
            return new Result("Unable to locate shelf " + toType.toString() + " shelf " + toShelf + " in inventory");
        if ( (toInventory.count > 1) || ((toInventory.count > 0) && ((toType == CabinetType.D1) || (toType == CabinetType.D2))) )
            return new Result("Inventory found shelf full in " + toType.toString() + " shelf " + toShelf);
        
        // go ahead and do the move
        fromInventory.count--;
        toInventory.count++;
        // if moving to a desktop, track the original shelf
        if ( (toType == CabinetType.D1) || (toType == CabinetType.D2) )
            toInventory.originalShelf = fromInventory.originalShelf;
 
        // done!
        return new Result();
    }
    
    /**
     * Determine the count of a discs on a shelf
     * 
     * @param cabinet Cabinet to examine
     * @param shelf shelf to examine
     * @return Count of the discs (-1 if unable to locate cabinet/shelf)
     */
    public int depth(CabinetType cabinet, int shelf) {
        // make sure the cabinet is known
        if (!inventory.containsKey(cabinet))
            return -1;
        InventoryShelf inventoryShelf = inventory.get(cabinet).get(shelf);
        if (inventoryShelf == null)
            return -1;
        return inventoryShelf.count;
    }
    
    /**
     * Determines what disc is contained, if any, in a particular shelf
     * 
     * @param cabinet Cabinet to examine
     * @param shelf Shelf to examine
     * 
     * @return Shelf ID of disc in shelf, or -1 if no disc in shelf or shelf not found
     */
    public int getDisc(CabinetType cabinet, int shelf) {
        // make sure the cabinet is known
        if (!inventory.containsKey(cabinet))
            return -1;
        InventoryShelf inventoryShelf = inventory.get(cabinet).get(shelf);
        if (inventoryShelf == null)
            return -1;
        return inventoryShelf.originalShelf;
    }    
    
    /**
     * Dump all inventory information to the console and returns JSON with the dump as well
     */
    public String dump() {
        ArrayList<KVObj> response = new ArrayList<KVObj>();
        
        // loop through all possible entires in inventory
        for(Entry<CabinetType, HashMap<Integer, InventoryShelf>> entry : inventory.entrySet()) {
            CabinetType cabinet = entry.getKey();
            HashMap<Integer, InventoryShelf> shelves = entry.getValue();

            System.out.println("Inventory on cabinet " + cabinet.toString());
            response.add(new KVObj("cabinet", cabinet.toString()));
            
            for (Entry<Integer, InventoryShelf> shelf : shelves.entrySet()) {
                Integer shelfId = shelf.getKey();
                InventoryShelf is = shelf.getValue();
                
                System.out.println("* " + shelfId + ": " + is.count + " units of " + is.originalShelf);
                response.add(new KVObj("shelf", shelfId.toString()));
                response.add(new KVObj("count", Integer.toString(is.count)));
                response.add(new KVObj("originalShelf", Integer.toString(is.originalShelf)));
            }
        }
        return Utils.buildJSON(response);
    }
}
