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
    private Integer desktop;
    
    public CommandEmptyDesktop(Integer desktop) {
        this.desktop = desktop;
    }
    
    public Boolean execute() {
        return false;
    }
    
    public String details() {
        return "EmptyDesktop(" + this.desktop + ")";
    }
}
