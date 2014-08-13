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
import route.Cartesian;
import utils.Result;

/**
 *
 */
public class CommandShowLayer extends CommandInterface {
    int shelf;
    int desktop;
    EffectType effect;
    
    public CommandShowLayer(int shelf, int desktop, EffectType effect) {
        this.shelf = shelf;
        this.desktop = desktop;
        this.effect = effect;
    }
    
    public Result execute(CommandArguments args) {
        return new Result();
    }
    
    public String details() {
        return "ShowLayer(" + shelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
