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
public class CommandShowLayer extends CommandInterface {
    int shelf;
    int desktop;
    EffectType effect;
    
    public CommandShowLayer(int shelf, int desktop, EffectType effect) {
        this.shelf = shelf;
        this.desktop = desktop;
        this.effect = effect;
    }
    
    public CommandCompletion execute() {
        return CommandCompletion.error;
    }
    
    public String details() {
        return "ShowLayer(" + shelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
