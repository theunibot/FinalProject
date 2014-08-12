/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.*;

/**
 *
 * @author cmidgley
 */
public class CommandEmptyDesktop extends CommandInterface {
    private int desktop;
    
    public CommandEmptyDesktop(int desktop) {
        this.desktop = desktop;
    }
    
    public CommandCompletion execute() {
        return CommandCompletion.error;
    }
    
    public String details() {
        return "EmptyDesktop(" + this.desktop + ")";
    }
}
