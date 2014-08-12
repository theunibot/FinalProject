/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotoperations;

import route.RouteCompiler;
import utils.FileUtils;
import java.util.ArrayList;
import route.Route;
import route.Cartesian;
import enums.ShelfType;

/**
 *
 * @author kyle
 */
public class ArmOperations
{

    private R12Operations r12o = null;
    private RouteCompiler rc = null;
    private static ArmOperations armOprations = null;

    //Regular Objects
    private ArrayList<String> initCommands = null;

    //Strings
    public static final String RESPONSE_OK = "OK";

    private final String INIT_COMMANDS_FILENAME = "initCommands.txt";
    private final String INIT_COMMANDS_FILEPATH = FileUtils.getFilesFolderString() + INIT_COMMANDS_FILENAME;

    private final String INIT_FILE_HEADER = ""
            + "//This is the R12 Robot Init File. This file is where commands are placed in the RoboForth"
            + "\n//Language and are called upon startup by the ThreadCommand thread. "
            + "\n//Each command should be seperated by a carriage return."
            + "\n//All comments must be on their own line and start with \"//\"";

    public ArmOperations()
    {

    }

    public boolean init()
    {
        r12o = R12Operations.getInstance();
        rc = RouteCompiler.getInstance();
        boolean success = false;
//        rc.init();
        if (r12o.init() && runInitCommands() && rc.init())
        {
            success = true;
        }
        return success;
    }

    private boolean runInitCommands()
    {

        //if init command file exists, read all the commands and write them out to the 
        initCommands = FileUtils.readCommandFileOrGenEmpty(INIT_COMMANDS_FILEPATH, INIT_FILE_HEADER);

        System.out.println("Read " + initCommands.size() + " command(s) from init commands file.");

        for (String command : initCommands)//runs every command in the file
        {
            r12o.write(command);
            ResponseObject response = r12o.getResponse(command);

            System.out.println(response.getMsg());
            if (!response.isSuccessful())
            {
                System.err.println("Command Failed! Cmd: " + command + " Response Msg: " + response.getMsg());
//                    return false;
            }
        }
        return true;
    }

    public static ArmOperations getInstance()
    {
        if (armOprations == null)
        {
            armOprations = new ArmOperations();
        }
        return armOprations;
    }

    /**
     * Run a route, with a modified starting and ending Cartesian coordinates
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return success (true) or failure (false)
     */
    public boolean runRoute(Route route, Cartesian start, Cartesian end)
    {
        ResponseObject response;
        if (route.size() >= 2)//must have start and end pos to modify
        {
            String modStart = cartesianCommandToRouteModifyString(start, route.getRouteProperties().getRouteName(), 0);
            String modEnd = cartesianCommandToRouteModifyString(start, route.getRouteProperties().getRouteName(), route.size() - 1);

            //run the modify start command
            r12o.write(modStart);
            response = r12o.getResponse(modStart);

            if (!response.isSuccessful())
            {
                System.err.println("Command Failed! Cmd: " + modStart + " Response Msg: " + response.getMsg());
                return false;
            }

            //run the modify end command
            r12o.write(modEnd);
            response = r12o.getResponse(modEnd);

            if (!response.isSuccessful())
            {
                System.err.println("Command Failed! Cmd: " + modEnd + " Response Msg: " + response.getMsg());
                return false;
            }
            
            //if reached here, route modified correctly
            String runRoute = "CONTINUOUS ADJUST " + route.getRouteProperties().getRouteName() + " RUN";
            
            //run the route
            r12o.write(runRoute);
            response = r12o.getResponse(runRoute);

            if (!response.isSuccessful())
            {
                System.err.println("Command Failed! Cmd: " + runRoute + " Response Msg: " + response.getMsg());
                return false;
            }
            
            return true;
        }
        else //not enough pos to modify start and end routes
        {
            System.err.println("Route has no commands in it");
            return false;
        }
    }

    /**
     * Pickup a disc from a slot. Assumes that the robot is already at the safe
     * pickup location for the specified disc, and is not already holding one
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @return success (true) or failure (false)
     */
    public boolean pick(ShelfType unit, int stackPosition)
    {
        //unit is used to define angle to the unit
        //stack pos only relevant if CP, used to pick route for depth
        
        return true;
    }

    /**
     * Drop off a disc to a slot. Assumes that the robot is already at the safe
     * dropoff location for the specified disc, and is currently holding a disc
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @return success (true) or failure (false)
     */
    public boolean drop(ShelfType unit, int stackPosition)
    {
        //unit is used to define angle to the unit
        //stack pos only relevant if CP, used to pick route for depth
        
        return true;
    }

    /**
     * Execute the robot calibrate command. WARNING: The robot arm MUST be in
     * HOME and safe/ready for this command to be successful and not damage the
     * table/arm.
     *
     * @return success (true) or failure (false)
     */
    public boolean calibrate()
    {
        String commandString = "CALIBRATE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            System.err.println("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            return false;
        }
        return true;
    }

    /**
     * Move the robot to the HOME position, using a safe route that will not hit
     * any objects
     *
     * @return success (true) or failure (false)
     */
    public boolean home()
    {
        String commandString = "SAFEHOME RUN";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            System.err.println("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            return false;
        }
        return true;
    }

    /**
     * Tell the robot to energize the arm
     *
     * @return success (true) or failure (false)
     */
    public boolean energize()
    {
        String commandString = "ENERGIZE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            System.err.println("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            return false;
        }
        return true;
    }

    /**
     * Tell the robot to de-energize
     *
     * @return success (true) or failure (false)
     */
     public boolean deEnergize()
    {
        String commandString = "DE-ENERGIZE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            System.err.println("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            return false;
        }
        return true;
    }

    /**
     * Program a route into the robot controller
     *
     * @param route route to add to the controller
     * @return success (true) or failure (false)
     */
    public boolean learnRoute(Route route)
    {
        //gets and executes each command in the route
        ArrayList<String> commands = route.getRoboforthCommands();
        for (String commandString : commands)
        {
            System.out.println(commandString);
            if (!runRouteCommand(commandString))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Sends an individual route command to robot and looks for errors
     *
     * @param commandString command to execute
     * @return success (true) or failure (false)
     */
    private boolean runRouteCommand(String commandString)
    {
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            System.err.println("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            return false;
        }
        return true;
    }

    /**
     * Converts the Cartesian object into a RoboForth String to modify the given
     * route at the given position.
     *
     * @param c Cartesian point
     * @param routeName Name of the Route to modify
     * @param pos Position in the route to modify
     * @return RoboForth String to modify the given route at the given position
     */
    private String cartesianCommandToRouteModifyString(Cartesian c, String routeName, int pos)
    {
        return "DECIMAL " + c.getRollStr() + " " + c.getYawStr() + " " + c.getPitchStr() + " " + c.getZ() + " " + c.getY() + " " + c.getX() + " " + routeName + " " + pos + " LINE DLD";
    }

}
