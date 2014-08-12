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
public class CommandShowSign extends CommandInterface {
    int layer;
    EffectType effect;
    
    public CommandShowSign(int layer, EffectType effect) {
        this.layer = layer;
        this.effect = effect;
    }
    
    public CommandCompletion execute() {
        return CommandCompletion.error;
    }
    
    public String details() {
        return "ShowSign(" + layer + ", " + effect.toString() + ")";
    }
}
