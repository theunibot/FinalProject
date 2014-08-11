/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import robotoperations.ArmOperations;
import commandprocessor.CommandProcessor;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class ThreadCommand extends Thread
{
    private ArmOperations ao;
    private CommandProcessor cp = null;

    @Override
    public void run()
    {
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
        // indicate we are down
        cp = null;
    }
    
    /**
     * kill the background command processor and wait for it to complete
     */
    public void kill() {
        if (cp != null) {
            // kill the thread
            cp.kill();
            // and wait for it to complete
            while (cp != null)
                Utils.sleep(250);            
        }
    }

}