/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotoperations;

import enums.CabinetType;
import java.util.ArrayList;
import route.Cartesian;
import route.Route;
import route.RouteCompiler;
import utils.FileUtils;
import utils.Result;

/**
 *
 * @author kyle
 */
public class ArmOperations
{

    private R12Operations r12o = null;
    private RouteCompiler rc = null;
    private static ArmOperations armOperations = null;

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

    public Result init()
    {
        r12o = R12Operations.getInstance();
        rc = RouteCompiler.getInstance();
        boolean success = false;
//        rc.init();
        Result result = r12o.init();
        if (!result.success())
            return result;
        result = runInitCommands();
        if (!result.success())
            return result;
        result = rc.init();
        return result;
    }

    private Result runInitCommands()
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
        return new Result();
    }

    public static ArmOperations getInstance()
    {
        if (armOperations == null)
        {
            armOperations = new ArmOperations();
        }
        return armOperations;
    }

    /**
     * Run a route, with a modified starting and ending Cartesian coordinates
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return Result with success of failure information)
     */
    public Result runRoute(Route route, Cartesian start, Cartesian end)
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
                return new Result("Command Failed! Cmd: " + modStart + " Response Msg: " + response.getMsg());

            //run the modify end command
            r12o.write(modEnd);
            response = r12o.getResponse(modEnd);

            if (!response.isSuccessful())
                return new Result("Command Failed! Cmd: " + modEnd + " Response Msg: " + response.getMsg());
            
            //if reached here, route modified correctly
            String runRoute = "CONTINUOUS ADJUST " + route.getRouteProperties().getRouteName() + " RUN";
            
            //run the route
            r12o.write(runRoute);
            response = r12o.getResponse(runRoute);

            if (!response.isSuccessful())
                return new Result("Command Failed! Cmd: " + runRoute + " Response Msg: " + response.getMsg());
            
            return new Result();
        }
        else //not enough pos to modify start and end routes
            return new Result("Route has no commands in it");
    }

    /**
     * Pickup a disc from a shelf. Assumes that the robot is already at the safe
     * pickup location for the specified disc, and is not already holding one
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @return Result with success/failure info
     */
    public Result pick(CabinetType unit, int stackPosition)
    {
        //unit is used to define angle to the unit
        //stack pos only relevant if CP, used to pick route for depth
        
        return new Result();
    }

    /**
     * Drop off a disc to a shelf. Assumes that the robot is already at the safe
     * dropoff location for the specified disc, and is currently holding a disc
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @return Result with success/fail info
     */
    public Result drop(CabinetType unit, int stackPosition)
    {
        //unit is used to define angle to the unit
        //stack pos only relevant if CP, used to pick route for depth
        
        return new Result();
    }

    /**
     * Execute the robot calibrate command. WARNING: The robot arm MUST be in
     * HOME and safe/ready for this command to be successful and not damage the
     * table/arm.
     *
     * @return Result with success/fail info
     */
    public Result calibrate()
    {
        String commandString = "CALIBRATE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        return new Result();
    }

    /**
     * Move the robot to the HOME position, using a safe route that will not hit
     * any objects
     *
     * @return Result with success/fail info
     */
    public Result home()
    {
        String commandString = "SAFEHOME RUN";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        return new Result();
    }

    /**
     * Tell the robot to energize the arm
     *
     * @return Result with success/fail info
     */
    public Result energize()
    {
        String commandString = "ENERGIZE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        return new Result();
    }

    /**
     * Tell the robot to de-energize
     *
     * @return Result with success/fail info
     */
     public Result deEnergize()
    {
        String commandString = "DE-ENERGIZE";
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        return new Result();
    }

    /**
     * Program a route into the robot controller
     *
     * @param route route to add to the controller
     * @return Result with success/fail info
     */
    public Result learnRoute(Route route)
    {
        //gets and executes each command in the route
        ArrayList<String> commands = route.getRoboforthCommands();
        for (String commandString : commands)
        {
            System.out.println(commandString);
            Result result = runRouteCommand(commandString);
            if (!result.success())
                return result;
        }
        return new Result();
    }

    /**
     * Sends an individual route command to robot and looks for errors
     *
     * @param commandString command to execute
     * @return Result with success/fail info
     */
    private Result runRouteCommand(String commandString)
    {
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        return new Result();
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
