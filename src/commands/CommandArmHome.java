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
public class CommandArmHome extends CommandInterface {
    public CommandArmHome() {
        
    }
    
    public CommandCompletion execute() {
        ArmOperations ao = ArmOperations.getInstance();
        return (ao.home() ? CommandCompletion.complete : CommandCompletion.error);
    }
    
    public String details() {
        return "Home()";
    }
}
