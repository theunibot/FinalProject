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
import route.Position;
import utils.Result;

/**
 * Command that shows a layer being repaired (personalization layer)
 */
public class CommandShowLayer extends CommandInterface {
    int shelf;
    int desktop;
    private CabinetType desktopCabinet;
    RouteEffectType effect;
    
    /**
     * Constructor initializes the command
     * 
     * @param shelf Shelf to show/shake
     * @param desktop Desktop that has the shelf
     * @param effect Effect to run on the route when showing the shelf
     */
    public CommandShowLayer(int shelf, int desktop, RouteEffectType effect) {
        this.shelf = shelf;
        this.desktop = desktop;
        if (this.desktop == 1)
            this.desktopCabinet = CabinetType.D1;
        else
            this.desktopCabinet = CabinetType.D2;
        this.effect = effect;
    }
    
    /**
     * Executes the robot commands to show the layer
     * 
     * @param args Arm position information
     * 
     * @return Result with success/failure information
     */
    public Result execute(CommandArguments args) {
        Result result = this.moveLayer(args, desktopCabinet, shelf, desktopCabinet, shelf, effect);
        return result;
    }

    /**
     * Return details about this command
     * 
     * @return Human readable string that describes this command
     */
    public String details() {
        return "ShowLayer(" + shelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
