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
import route.Cartesian;
import utils.*;

/**
 * defines the command to empty a desktop of all layers
 */
public class CommandEmptyDesktop extends CommandInterface {
    private int desktop;
    private CabinetType desktopCabinet;
    private int nextDesktopShelf = 0;
    
    /**
     * constructor identifies the desktop to clear
     * 
     * @param desktop to clear
     */
    public CommandEmptyDesktop(int desktop) {
        this.desktop = desktop;
        if (this.desktop == 1)
            this.desktopCabinet = CabinetType.D1;
        else
            this.desktopCabinet = CabinetType.D2;
    }
    
    /**
     * Executes the robot operations to clear (return to the CP) the discs in the desktop
     * 
     * @param args Info on where arm is located
     * @return Result containing success or failure
     */
    public Result execute(CommandArguments args) {
        Inventory inventory = Inventory.getInstance();
        Result result;
        
        while (this.nextDesktopShelf < 6) {
            int returnShelf = inventory.getDisc(this.desktopCabinet, this.nextDesktopShelf);
            if (returnShelf >= 0) {
                // we need to return this item (move from desktopCabinet/nextDesktopShelf to returnShelf)
                CabinetType cpCabinet = Utils.shelfToCabinet(returnShelf);
                result = this.moveLayer(args, desktopCabinet, nextDesktopShelf, cpCabinet, returnShelf, RouteEffectType.EFFICIENT);
                if (!result.success())
                    return result;
                
                // we did enough work -- let's free up the robot and come back around again
                return new Result(CommandCompletion.INCOMPLETE);
            }
            ++this.nextDesktopShelf;
        }
        
        // done
        return new Result();
    }
    
    /**
     * Returns a string that describes this command in human text
     * 
     * @return string with description text
     */
    public String details() {
        return "EmptyDesktop(" + this.desktop + ")";
    }
}
