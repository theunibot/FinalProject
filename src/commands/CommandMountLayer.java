/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;
import inventory.Inventory;
import utils.*;

/**
 *
 * @author cmidgley
 */
public class CommandMountLayer extends CommandInterface {
    int cpShelf;
    int desktopShelf;
    int desktop;
    CabinetType desktopCabinet;
    EffectType effect;
    
    public CommandMountLayer(int cpShelf, int desktopShelf, int desktop, EffectType effect) {
        this.cpShelf = cpShelf;
        this.desktopShelf = desktopShelf;
        this.desktop = desktop;
        if (this.desktop == 1)
            desktopCabinet = CabinetType.D1;
        else
            desktopCabinet = CabinetType.D2;
        this.effect = effect;
    }
    
    public Result execute(CommandArguments args) {
        Inventory inventory = Inventory.getInstance();
        Result result;
        
        // determine which cabinet we are moving towards
        CabinetType newCabinet = utils.Utils.shelfToCabinet(this.cpShelf);
        
        // check inventory to see if there is already a disc in the slot.  If so, return it to the cachepoint
        int existingDesktopLayer = inventory.desktopShelf(this.desktopCabinet, this.desktopShelf);
        if (existingDesktopLayer >= 0) {
            // there is a disc in there that we need to remove.
            result = moveLayer(args, this.desktopCabinet, this.desktopShelf, Utils.shelfToCabinet(existingDesktopLayer), existingDesktopLayer, "default");
            if (!result.success())
                return result;
        }
        
        // now do the real move cpShelf
        result = moveLayer(args, Utils.shelfToCabinet(this.cpShelf), this.cpShelf, this.desktopCabinet, this.desktopShelf, "exciting");
        return result;
    }
    
    public String details() {
        return "MountLayer(" + cpShelf + ", " + desktopShelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
