/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandprocessor;

import commandqueue.CommandQueues;
import commands.CommandArguments;
import commands.CommandInterface;
import enums.CabinetType;
import enums.CommandCompletion;
import enums.CommandStatus;
import robotoperations.ArmOperations;
import route.Cartesian;
import route.PositionLookupTable;
import utils.Result;

/**
 *
 */
public class CommandProcessor
{

    private static CommandProcessor cp = null;
    private CommandQueues cmdq = null;
    private ArmOperations ao = null;
    
    /**
     * Returns a singleton instead of this CommandProcessor
     * 
     * @return CommandProcessor single instance
     */
    public static CommandProcessor getInstance()
    {
        if (cp == null)
        {
            cp = new CommandProcessor();
        }
        return cp;
    }

    /**
     * Constructor sets up the command processor
     */
    public CommandProcessor()
    {
        cmdq = CommandQueues.getInstance();
        ao = ArmOperations.getInstance();
    }
    
    /**
     * Main command processor thread - blocks and executes command until terminated with kill
     */
    public void processCommands() {
        System.out.println("Command processor started");
        Result activeError = new Result();
        
        // set up our command arguments to track the arm position
        PositionLookupTable plt = PositionLookupTable.getInstance();
        CommandArguments commandArgs = new CommandArguments();
        commandArgs.cabinet = CabinetType.HOME;
        commandArgs.coordinates = plt.shelfToCartesian(CabinetType.HOME, 0);
        
        while (true) 
        {
            CommandInterface cmd = cmdq.pop();
            if (cmd == null) {
                if (cmdq.killed()) {
                    System.out.println("Received kill in processCommands; terminating");
                    break;
                }
                continue;
            }
            // do we have an error pending?
            if (!activeError.success() && !cmd.ignoreErrors()) {
                System.out.println("Ignoring command " + cmd.details() + " (due to outstanding error)");
                cmd.setResult(activeError);
                cmd.setStatus(CommandStatus.ERROR);
                continue;
            }
            // process the command
            System.out.println("Processing command " + cmd.details());
            cmd.setStatus(CommandStatus.EXECUTING);
            Result result = cmd.execute(commandArgs);
            switch (result.completion) {
                case ERROR:
                    System.out.println("Command failed");
                    cmd.setResult(result);
                    cmd.setStatus(CommandStatus.ERROR);
                    activeError = result;
                    break;
                case COMPLETE:
                    System.out.println("Command completed successfully");
                    cmd.setStatus(CommandStatus.COMPLETE);
                    // can this clear the error flag?
                    if (!activeError.success() && cmd.successClearsError()) {
                        System.out.println("Command has cleared error flag; operations to continue normally");
                        activeError = new Result();
                    }
                    break;
                case INCOMPLETE:
                    System.out.println("Command incomplete; queueing for another run");
                    cmd.setStatus(CommandStatus.PENDING);
                    cmdq.push(cmd);
                    break;                    
            }
        }
        // done - terminate
    }
    
    /**
     * Instructs the main processing thread to do a clean shutdown
     */
    public void kill() {
        cmdq.kill();
    }
}
