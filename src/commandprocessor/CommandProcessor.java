/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandprocessor;

import commandqueue.CommandQueueWrapper;
import robotoperations.ArmOperations;

/**
 *
 * @author kyle
 */
public class CommandProcessor
{

    private CommandProcessor cp = null;
    private CommandQueueWrapper cmdq = null;
    private ArmOperations ao = null;

    public CommandProcessor getInstance()
    {
        if (cp == null)
        {
            cp = new CommandProcessor();
        }
        return cp;
    }

    private CommandProcessor()
    {
        cmdq = CommandQueueWrapper.getInstance();
        ao = ArmOperations.getInstance();
    }
    
    //hello world
    /*
    The goal of the Command Processor is to take the first item from the Command
    Queue and process it into chuncks - "Clear Desktop 1" becomes RoboForth to 
    do that.
    
    To do so, the Command Processor leverages the library of routes held in the
    Route Complier as well as uses a lookup table to figure out what to set the
    start and end points of each route to.
    */

}
