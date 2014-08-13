/*
    This file is part of theunibot.

    theunibot is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    theunibot is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package commandprocessor;

import commandqueue.CommandQueues;
import commands.CommandArguments;
import commands.CommandInterface;
import enums.CabinetType;
import enums.CommandCompletion;
import enums.CommandStatus;
import inventory.Inventory;
import robotoperations.ArmOperations;
import route.Position;
import route.PositionLookup;
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
    private CommandProcessor()
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
        PositionLookup plt = PositionLookup.getInstance();
        CommandArguments commandArgs = new CommandArguments();
        commandArgs.cabinet = CabinetType.HOME;
        commandArgs.coordinates = plt.shelfToPosition(CabinetType.HOME, 0);
        
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
                    System.err.println("Command failed; setting status on command");
                    cmd.setResult(result);
                    cmd.setStatus(CommandStatus.ERROR);
                    activeError = result;
                    break;
                case COMPLETE:
                    System.out.println("Command completed successfully");
                    cmd.setStatus(CommandStatus.COMPLETE);
                    // can this clear the error flag?
                    if (!activeError.success() && cmd.successClearsError()) {
                        Inventory.getInstance().resetInventory();
                        System.out.println("Command has cleared error flag; inventory reset and operations to continue normally");
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
