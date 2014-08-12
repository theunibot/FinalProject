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
    Integer shelf;
    Integer desktop;
    EffectType effect;
    
    public CommandShowLayer(Integer shelf, Integer desktop, EffectType effect) {
        this.shelf = shelf;
        this.desktop = desktop;
        this.effect = effect;
    }
    
    public Boolean execute() {
        return false;
    }
    
    public String details() {
        return "ShowLayer(" + shelf + ", " + desktop + ", " + effect.toString() + ")";
    }
}
