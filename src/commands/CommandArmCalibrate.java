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
public class CommandArmCalibrate extends CommandInterface {
    public CommandArmCalibrate() {
    }
    
    public Boolean execute() {
        ArmOperations ao = ArmOperations.getInstance();
        return ao.calibrate();
    }
    
    public String details() {
        return "Calibrate()";
    }
}
