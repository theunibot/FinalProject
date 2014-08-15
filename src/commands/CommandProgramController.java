/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import robotoperations.ArmOperations;
import route.PositionLookup;
import route.RouteCompiler;
import utils.Result;

/**
 *
 */
public class CommandProgramController extends CommandInterface {
    private String name;
    
    public CommandProgramController(String name) {
        this.name = name;
    }
    
    public Result execute(CommandArguments args) {
        ArmOperations ao = ArmOperations.getInstance();

        // if all, do a full reset of the controller
        if (name == null) {
            Result result = ao.restartController();
            if (!result.success())
                return result;
        }
        Result result = PositionLookup.getInstance().programPositions(name);
        if (!result.success())
            return result;
        result = RouteCompiler.getInstance().programRoutes(name);
        if (!result.success())
            return result;
        // now cause these to be permanent
        return ao.persist();
    }
    
    public String details() {
        if (name == null)
            return "ProgramController()";
        return "ProgramController(" + name + ")";
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
