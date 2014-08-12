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
public class CommandShowSign extends CommandInterface
{

    int layer;
    EffectType effect;

    public CommandShowSign(int layer, EffectType effect)
    {
        this.layer = layer;
        this.effect = effect;
    }

    public Result execute(CommandArguments args)
    {
        return new Result("ShowSign not implemented");
    }

    public String details()
    {
        return "ShowSign(" + layer + ", " + effect.toString() + ")";
    }
}
