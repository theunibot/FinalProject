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
import utils.Result;

/**
 * Command to show a sign
 */
public class CommandShowSign extends CommandInterface
{

    int layer;
    RouteEffectType effect;

    /**
     * Constructor that sets up the command parameters
     * 
     * @param layer Layer shelf to show
     * @param effect effect to use
     */
    public CommandShowSign(int layer, RouteEffectType effect)
    {
        this.layer = layer;
        this.effect = effect;
    }

    /**
     * Executes the robot commands for showing the sign
     * 
     * @param args Robot positional information
     * @return Result with success/fail information
     */
    public Result execute(CommandArguments args)
    {
        CabinetType cabinet = utils.Utils.shelfToCabinet(layer);

        Result result = this.moveLayer(args, cabinet, layer, cabinet, layer, effect);
        return new Result();
    }

    /**
     * Return details about this command
     * 
     * @return Human readable string that describes this command
     */

    public String details()
    {
        return "ShowSign(" + layer + ", " + effect.toString() + ")";
    }
}
