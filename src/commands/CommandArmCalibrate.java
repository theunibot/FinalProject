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
public class CommandArmCalibrate extends CommandInterface {
    public CommandArmCalibrate() {
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();
        return ao.calibrate();
    }
    
    public String details() {
        return "Calibrate()";
    }

    /**
     * This command should be allowed to run, even if we have outstanding robot errors
     * 
     * @return true to indicate we should run even during errors
     */
    public boolean ignoreErrors() {
        return true;
    }
    
    /**
     * The calibrate command, if successful, should clear errors and let life continue normally...
     * 
     * @return true to specify errors should be cleared 
     */
    public boolean successClearsError() {
        return true;
    }
}
