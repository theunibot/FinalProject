/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enums;

/**
 * define the command types that come from the robot REST interface/queue
 */
public enum CommandType
{
    mountLayer, 
    replaceLayer, 
    showLayer, 
    emptyDesktop, 
    showSign, 
    armHome, 
    armCalibrate, 
    armEnergize, 
    armDeEnergize
}
