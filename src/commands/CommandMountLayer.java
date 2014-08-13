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

package commands;

import enums.*;
import inventory.Inventory;
import utils.*;

/**
 * Command that executes the actual MountLayer feature (also ReplaceLayer)
 */
public class CommandMountLayer extends CommandInterface {
    private int cpShelf;
    private int desktopShelf;
    private int desktop;
    private CabinetType desktopCabinet;
    private RouteEffectType effect;
    
    /**
     * Constructor initializes the parameters for the mount layer command
     * 
     * @param cpShelf shelf on the CP to move layer from
     * @param desktopShelf shelf on the desktop to move layer to
     * @param desktop the desktop to move to (1 or 2)
     * @param effect the route effect to use
     */
    public CommandMountLayer(int cpShelf, int desktopShelf, int desktop, RouteEffectType effect) {
        this.cpShelf = cpShelf;
        this.desktopShelf = desktopShelf;
        this.desktop = desktop;
        if (this.desktop == 1)
            this.desktopCabinet = CabinetType.D1;
        else
            this.desktopCabinet = CabinetType.D2;
        this.effect = effect;
    }
    
    /**
     * Execute the actual robot operations to move the layer
     * 
     * @param args Standard command args to track the arm location
     * 
     * @return Result with success/fail info
     */
    public Result execute(CommandArguments args) {
        Inventory inventory = Inventory.getInstance();
        Result result;
        
        // determine which cabinet we are moving towards
        CabinetType cpCabinet = utils.Utils.shelfToCPCabinet(this.cpShelf);
        
        // check inventory to see if there is already a disc in the slot.  If so, return it to the cachepoint
        int existingDesktopLayer = inventory.getDisc(this.desktopCabinet, this.desktopShelf);
        if (existingDesktopLayer >= 0) {
            // there is a disc in there that we need to remove.
            result = moveLayer(args, this.desktopCabinet, this.desktopShelf, this.desktopCabinet, existingDesktopLayer, RouteEffectType.EFFICIENT);
            if (!result.success())
                return result;
        }
        
        // now do the real move cpShelf
        result = moveLayer(args, cpCabinet, this.cpShelf, this.desktopCabinet, this.desktopShelf, RouteEffectType.FANCY);
        return result;
    }
    
    /**
     * Return details about this command
     * 
     * @return Human readable string that describes this command
     */
    public String details() {
        return "MountLayer(" + cpShelf + ", " + desktopShelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
