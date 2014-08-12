/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;

import enums.CabinetType;
import route.Cartesian;

/**
 *
 * @author cmidgley
 */
public class CommandArguments {
    public Cartesian coordinates;              // coordinates of the arm
    public CabinetType cabinet;                // cabinet that the arm is closest to
}
