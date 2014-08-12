/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;
import robotoperations.ArmOperations;
import route.Cartesian;
import utils.Result;

/**
 *
 * @author cmidgley
 */
public class CommandArmEnergize extends CommandInterface {
    public CommandArmEnergize() {
        
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        return ao.energize();
    }
    
    public String details() {
        return "Energize()";
    }
}
