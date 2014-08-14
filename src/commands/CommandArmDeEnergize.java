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
import robotoperations.ArmOperations;
import route.Position;
import utils.Result;

/**
 *
 */
public class CommandArmDeEnergize extends CommandInterface {
    public CommandArmDeEnergize() {
        
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        ao.ungrip();
        utils.Utils.sleep(1000);
        ao.grip();
        return ao.deEnergize();
    }
    
    public String details() {
        return "DeEnergize()";
    }
    
    /**
     * This command should be allowed to run, even if we have outstanding robot errors
     * 
     * @return true to indicate we should run even during errors
     */
    public boolean ignoreErrors() {
        return true;
    }
    
}
