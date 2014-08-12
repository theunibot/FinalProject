/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandprocessor;

import commandqueue.CommandQueueWrapper;
import commands.CommandInterface;
import enums.CommandCompletion;
import enums.CommandStatus;
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

    public CommandProcessor()
    {
        cmdq = CommandQueueWrapper.getInstance();
        ao = ArmOperations.getInstance();
    }
    
    public void processCommands() {
        System.out.println("Command processor started");
        while (true) 
        {
            CommandInterface cmd = cmdq.pop();
            if (cmd == null) {
                System.out.println("Received kill in processCommands; terminating");
                break;
            }
            // process the command
            System.out.println("Processing command " + cmd.details());
            cmd.setStatus(CommandStatus.EXECUTING);
            CommandCompletion completion = cmd.execute();
            switch (completion) {
                case error:
                    System.out.println("Command failed");
                    cmd.setStatus(CommandStatus.ERROR);
                    break;
                case complete:
                    System.out.println("Command completed successfully");
                    cmd.setStatus(CommandStatus.COMPLETE);
                    break;
                case incomplete:
                    System.out.println("Command incomplete; queueing for another run");
                    cmdq.push(cmd);
                    break;                    
            }
        }
        // done - terminate
    }
    
    public void kill() {
        cmdq.kill();
    }
    

}
