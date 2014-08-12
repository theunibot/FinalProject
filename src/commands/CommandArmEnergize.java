/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;
import robotoperations.ArmOperations;

/**
 *
 * @author cmidgley
 */
public class CommandArmEnergize extends CommandInterface {
    public CommandArmEnergize() {
        
    }
    
    public Boolean execute() {
        ArmOperations ao = ArmOperations.getInstance();
        return ao.energize();
    }
    
    public String details() {
        return "Energize()";
    }
}
