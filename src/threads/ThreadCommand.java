/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import robotoperations.ArmOperations;
import commandprocessor.CommandProcessor;

/**
 *
 * @author kyle
 */
public class ThreadCommand extends Thread
{
    private ArmOperations ao;

    @Override
    public void run()
    {
        init();
    }

    private void init()
    {
        CommandProcessor cp;
        ao = ArmOperations.getInstance();
        boolean success = ao.init();
        if (success)
        {
            System.out.println("All Arm Inits successful");
            // now bring up the command processor
            cp = new CommandProcessor();
            cp.processCommands();
        }
        System.out.println("CommandProcess thread terminating");
 
    }

}