/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory;

/**
 * Class defines a shelf in inventory.  
 */
public class InventoryShelf {
    public int count;               // count of discs in the shelf
    public int originalShelf;       // the original shelf ID (for discs in desktops)
    
    /**
     * Constructor builds an inventory shelf with the specified number of discs
     * @param count Count of discs
     */
    public InventoryShelf(int count) {
        this.count = count;
        this.originalShelf = -1;
    }
    
    /**
     * Constructor builds an inventory shelf with the specified number of discs, of a specific shelf id
     * 
     * @param count Count of discs
     * @param originalShelf ID of the original shelf
     */
    public InventoryShelf(int count, int originalShelf) {
        this.count = count;
        this.originalShelf = originalShelf;
    }
}
