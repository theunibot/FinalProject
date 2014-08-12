/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;
import route.Cartesian;
import utils.Result;

/**
 *
 * @author cmidgley
 */
public class CommandEmptyDesktop extends CommandInterface {
    private int desktop;
    
    public CommandEmptyDesktop(int desktop) {
        this.desktop = desktop;
    }
    
    public Result execute(CommandArguments args) {
        return new Result("Empty desktop not implemented");
    }
    
    public String details() {
        return "EmptyDesktop(" + this.desktop + ")";
    }
}
