/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandprocessor;

import commandqueue.CommandQueues;
import commands.CommandInterface;
import enums.CabinetType;
import enums.CommandCompletion;
import enums.CommandStatus;
import robotoperations.ArmOperations;
import route.Cartesian;
import route.PositionLookupTable;

/**
 *
 */
public class CommandProcessor
{

    private static CommandProcessor cp = null;
    private CommandQueues cmdq = null;
    private ArmOperations ao = null;
    private PositionLookupTable plt = PositionLookupTable.getInstance();

    // the following are used to track where the arm is currently located in 3D space
    private CabinetType currentCabinet = CabinetType.HOME;
    private Cartesian currentCartesian = plt.shelfToCartesian(CabinetType.HOME, 0);
    
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
    
    /**
     * Instructs the main processing thread to do a clean shutdown
     */
    public void kill() {
        cmdq.kill();
    }
    
    /**
     * Returns the cabinet that the robot arm is closest to
     * 
     * @return CabinetType
     */
    public CabinetType getCabinet() {
        return currentCabinet;
    } 
    
    /**
     * Sets the cabinet that the robot arm is currently resting near
     * 
     * @param cabinet that the arm is near
     */
    public void setCabinet(CabinetType cabinet) {
        this.currentCabinet = cabinet;
    }
    
    /**
     * Gets the point in space that the arm is currently nearest
     * 
     * @return Cartesian with the coordinates of the arm
     */
    public Cartesian getCartesian() {
        return currentCartesian;
    }
    
    /**
     * Sets the point in space that the arm is currently nearest
     * 
     * @param coord with the coordinates of the arm
     */
    public void setCartesian(Cartesian coord) {
        this.currentCartesian = coord;
    }
    
}
