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
package robotoperations;

import enums.CabinetType;
import java.util.ArrayList;
import route.Position;
import route.PositionLookup;
import route.Route;
import route.RouteCompiler;
import enums.RouteEffectType;
import route.RouteHolder;
import route.RouteProperties;
import utils.FileUtils;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class ArmOperations
{

    private final boolean Simulated = false;
    private R12Operations r12o = null;
    private RouteCompiler rc = null;
    private PositionLookup plt = null;
    private RouteHolder rh = null;
    private static ArmOperations armOperations = null;

    private final double DELTA_Z_CP_BIG = -2.75 * 25.4;//in to mm
    private final double DELTA_Z_CP_SMALL = -1.50 * 25.4;//
    private final double DELTA_Z_DT = -1.50 * 25.4;
    private final double DELTA_FORWARD = 5 * 25.4;

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

    private ArmOperations()
    {

    }

    public Result init()
    {
        r12o = R12Operations.getInstance();
        rc = RouteCompiler.getInstance();
        plt = PositionLookup.getInstance();
        rh = RouteHolder.getInstance();
        boolean success = false;
//        rc.init();
        Result result = r12o.init();
        if (!result.success())
        {
            return result;
        }
        result = runInitCommands();
        if (!result.success())
        {
            return result;
        }
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

            if (!response.isSuccessful())
            {
                return new Result("Command Failed! Cmd: " + command + " Response Msg: " + response.getMsg());
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
     * Run a route, with a modified starting and ending Position coordinates
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return Result with success of failure information)
     */
    public Result runRoute(Route route, Position start, Position end)
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: runRoute " + route.getRouteProperties().getRouteFriendlyName()
                    + " from " + ((start != null) ? start.getName() : "undefined") + " to "
                    + ((end != null) ? end.getName() : "undefined"));
            return new Result();
        }

        ResponseObject response;
        if (route.size() >= 2)//must have start and end pos to modify
        {
            if (start != null)
            {
                //run the modify start command
                String modStart = positionCommandToRouteModifyString(start, route.getRouteProperties().getRouteIDName(), 1);
                r12o.write(modStart);
                response = r12o.getResponse(modStart);
                if (!response.isSuccessful())
                {
                    return new Result("Command Failed! Cmd: " + modStart + " Response Msg: " + response.getMsg());
                }
            }
            if (end != null)
            {
                //run the modify end command
                String modEnd = positionCommandToRouteModifyString(end, route.getRouteProperties().getRouteIDName(), route.size());
                r12o.write(modEnd);

                response = r12o.getResponse(modEnd);

                if (!response.isSuccessful())
                {
                    return new Result("Command Failed! Cmd: " + modEnd + " Response Msg: " + response.getMsg());
                }
            }

            // run the route
            String runRoute = "CONTINUOUS ADJUST " + route.getRouteProperties().getRouteIDName() + " RUN";
            r12o.write(runRoute);
            response = r12o.getResponse(runRoute);
            if (!response.isSuccessful())
            {
                return new Result("Command Failed! Cmd: " + runRoute + " Response Msg: " + response.getMsg());
            }

            return new Result();
        }
        else
        //not enough pos to modify start and end routes
        {
            return new Result("Route named " + route.getRouteProperties().getRouteFriendlyName() + " has " + route.size() + " coordinates; must have at least two (start and end)");
        }
    }

    /**
     * Pickup a disc from a shelf. Assumes that the robot is already at the safe
     * pickup location for the specified disc, and is not already holding one
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @param position off of which the relative route is run
     * @return Result with success/failure info
     */
    
    public Result pick(CabinetType unit, int stackPosition, Position position)
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: pick from " + unit.toString() + " position " + stackPosition + " starting at " + position.getName());
            return new Result();
        }

        String commandString = "";
        double deltaZ = (DELTA_Z_DT);
        if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
        {
            deltaZ = (stackPosition == 2) ? DELTA_Z_CP_SMALL : DELTA_Z_CP_BIG;
        }

        //
        //UNGRIP
        //
        commandString = "UNGRIP";//Ungrip
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }

        //
        //MOVE DOWN
        //
        commandString = "0 0 " + String.valueOf(Utils.formatDouble(deltaZ)) + " MOVE";//moves DOWN set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }

        //
        //Move forward
        //
        double absStartX = Double.parseDouble(position.getX());
        double absStartY = Double.parseDouble(position.getY());
        double deltaAxis = DELTA_FORWARD / (2 ^ (1 / 2));//get the distance forward divided by root2
        double absEndX = 0;
        double absEndY = absStartY - deltaAxis;
        double deltaX = 0;
        double deltaY = -deltaAxis;
        double yaw = 0;
        
        //abs startX/Y used to calc abs endX/Y which are used to calc the Yaw
        //deltaX/Y used for MOVE commands
        if (unit == CabinetType.D2)
        {
            absEndX = absStartX - deltaAxis;
            deltaX = -deltaAxis;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) - 135;
        }
        else if (unit == CabinetType.D1)
        {
            absEndX = absStartX + deltaAxis;
            deltaX = deltaAxis;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + 135;
        }
        else if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
        {
            absEndY = absStartY + DELTA_FORWARD;
            absEndX = absStartX;
            deltaX = 0;
            deltaY = DELTA_FORWARD;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY));
        }
        else
        {
            return new Result("Invalid CabinetType: " + unit.toString());
        }

        commandString = Utils.formatDouble(yaw) + " YAW ! " + Utils.formatDouble(deltaX) + " " + Utils.formatDouble(deltaY) + " 0 MOVE";//moves DOWN set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        //
        //GRIP
        //
        commandString = "GRIP";
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        //
        //MOVE UP
        //
        commandString = "0 0 " + String.valueOf(Utils.formatDouble(-deltaZ)) + " MOVE";//moves UP set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        //
        //MOVE Back to position
        //
        commandString = position.getYaw() + " YAW ! " + position.getX() + " " + position.getY() + " " + position.getZ() + " MOVETO";
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
//        //unit is used to define angle to the unit
//        //stack pos only relevant if CP, used to pick route for depth
//
//        RouteEffectType retIn = RouteEffectType.GRIPPER_IN2;
//        RouteEffectType retOut = RouteEffectType.GRIPPER_OUT2;
//        if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
//        {
//
//            if (stackPosition == 1)
//            {
//                retIn = RouteEffectType.GRIPPER_IN1;
//                retOut = RouteEffectType.GRIPPER_OUT1;
//            }
//        }
//        Route routeIn = rh.getRoute(unit, unit, retIn);
//        Route routeOut = rh.getRoute(unit, unit, retOut);
//
//        if (routeIn == null || routeOut == null)
//        {
//            return new Result("Gripper Route for " + unit.toString() + " not found.");
//        }
//
//        //stuff not null
//        String commandString = position.getName() + " GOTO " + routeIn.getRouteProperties().getRouteIDName() + " START-HERE ADJUST CONTINUOUS RUN";
//        r12o.write(commandString);
//        ResponseObject response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }
//        //get to position successful
//
//        commandString = "GRIP";
//        r12o.write(commandString);
//        response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }
//
//        //grip successful
//        commandString = position.getName() + " " + routeIn.getRouteProperties().getRouteIDName() + " END-THERE ADJUST CONTINUOUS RUN";
//        r12o.write(commandString);
//        response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }
        //move back to start pos succesful.
        return new Result();
    }

    /**
     * Drop off a disc to a shelf. Assumes that the robot is already at the safe
     * dropoff location for the specified disc, and is currently holding a disc
     *
     * @param unit if this is a CP or a desktop
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @param position off of which the relative route is run
     * @return Result with success/fail info
     */
    public Result drop(CabinetType unit, int stackPosition, Position position)
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: drop at " + unit.toString() + " position " + stackPosition + " starting at " + position.getName());
            return new Result();
        }
        String commandString = "";
        ResponseObject response = null;
        
        //
        //Move forward
        //
        double absStartX = Double.parseDouble(position.getX());
        double absStartY = Double.parseDouble(position.getY());
        double deltaAxis = DELTA_FORWARD / (2 ^ (1 / 2));//get the distance forward divided by root2
        double absEndX = 0;
        double absEndY = absStartY - deltaAxis;
        double deltaX = 0;
        double deltaY = -deltaAxis;
        double yaw = 0;
        
        //abs startX/Y used to calc abs endX/Y which are used to calc the Yaw
        //deltaX/Y used for MOVE commands
        if (unit == CabinetType.D2)
        {
            absEndX = absStartX - deltaAxis;
            deltaX = -deltaAxis;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) - 135;
        }
        else if (unit == CabinetType.D1)
        {
            absEndX = absStartX + deltaAxis;
            deltaX = deltaAxis;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + 135;
        }
        else if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
        {
            absEndY = absStartY + DELTA_FORWARD;
            absEndX = absStartX;
            deltaY = DELTA_FORWARD;
            deltaX = 0;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY));
        }
        else
        {
            return new Result("Invalid CabinetType: " + unit.toString());
        }

        commandString = Utils.formatDouble(yaw) + " YAW ! " + Utils.formatDouble(deltaX) + " " + Utils.formatDouble(deltaY) + " 0 MOVE";//moves DOWN set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        commandString = "";
        double deltaZ = (DELTA_Z_DT);
        if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
        {
            deltaZ = (stackPosition == 2) ? DELTA_Z_CP_SMALL : DELTA_Z_CP_BIG;
        }
        
        //
        //MOVE DOWN
        //
        commandString = "0 0 " + String.valueOf(Utils.formatDouble(deltaZ)) + " MOVE";//moves DOWN set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }

        //
        //UNGRIP
        //
        commandString = "UNGRIP";//Ungrip
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }

        //
        //MOVE Back
        //
        commandString = Utils.formatDouble(Double.parseDouble(position.getYaw())) + " YAW ! " + Utils.formatDouble(-deltaX) + " " + Utils.formatDouble(-deltaY) + " 0 MOVE";
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }        
        
        //
        //GRIP
        //
        commandString = "GRIP";
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        //
        //MOVE UP
        //
        commandString = "0 0 " + String.valueOf(Utils.formatDouble(-deltaZ)) + " MOVE";//moves UP set amount
        r12o.write(commandString);
        response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        
        
//        //unit is used to define angle to the unit
//        //stack pos only relevant if CP, used to pick route for depth
//        RouteEffectType retIn = RouteEffectType.GRIPPER_IN2;
//        RouteEffectType retOut = RouteEffectType.GRIPPER_OUT2;
//        if (unit == CabinetType.CPL || unit == CabinetType.CPM || unit == CabinetType.CPR)
//        {
//
//            if (stackPosition == 1)
//            {
//                retIn = RouteEffectType.GRIPPER_IN1;
//                retOut = RouteEffectType.GRIPPER_OUT1;
//            }
//        }
//        Route routeIn = rh.getRoute(unit, unit, retIn);
//        Route routeOut = rh.getRoute(unit, unit, retOut);
//
//        if (routeIn == null || routeOut == null)
//        {
//            return new Result("Gripper Route for " + unit.toString() + " not found.");
//        }
//
//        //stuff not null
//        String commandString = positon.getName() + " GOTO " + routeIn.getRouteProperties().getRouteIDName() + " START-HERE ADJUST CONTINUOUS RUN";
//        r12o.write(commandString);
//        ResponseObject response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }
//        //get to position successful
//
//        commandString = "UNGRIP";
//        r12o.write(commandString);
//        response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }
//
//        //grip successful
//        commandString = positon.getName() + " " + routeIn.getRouteProperties().getRouteIDName() + " END-THERE ADJUST CONTINUOUS RUN";
//        r12o.write(commandString);
//        response = r12o.getResponse(commandString);
//
//        if (!response.isSuccessful())
//        {
//            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
//        }

        //move back to start pos succesful.
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
        if (Simulated)
        {
            System.out.println("ArmOperations: calibrate");
            return new Result();
        }
        return runRobotCommand("CALIBRATE");
    }

    /**
     * Move the robot to the HOME position, using a safe route that will not hit
     * any objects
     *
     * @return Result with success/fail info
     */
    public Result home()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: home");
            return new Result();
        }
        return runRobotCommand("HOME");
    }

    /**
     * Tell the robot to energize the arm
     *
     * @return Result with success/fail info
     */
    public Result energize()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: energize");
            return new Result();
        }
        return runRobotCommand("ENERGIZE");
    }

    /**
     * Tell the robot to de-energize
     *
     * @return Result with success/fail info
     */
    public Result deEnergize()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: de-energize");
            return new Result();
        }
        return runRobotCommand("DE-ENERGIZE");
    }

    /**
     * move the robot arm to a specific position
     *
     * @param position position to move to
     *
     * @return Result with success/fail info
     */
    public Result moveTo(Position position)
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: position to " + position.getName());
            return new Result();
        }
        return runRobotCommand(position.getName() + " GOTO");
    }

    /**
     * tell the robot to persist all settings into flash memory
     *
     * @return Result with success/fail info
     */
    public Result persist()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: persist");
            return new Result();
        }
        return runRobotCommand("USAVE");
    }

    /**
     * Program a route into the robot controller
     *
     * @param route route to add to the controller
     * @return Result with success/fail info
     */
    public Result learnRoute(Route route)
    {

        if (Simulated)
        {
            System.out.println("ArmOperations: learnRoute " + route.getRouteProperties().getRouteFriendlyName());
            return new Result();
        }

        ArrayList<String> routeCommands = route.getRoboforthCommands();

        //input the Fwd commands
        for (String commandString : routeCommands)
        {
            Result result = runRobotCommand(commandString);
            if (!result.success())
            {
                return result;
            }
        }
        return new Result();
    }

    /**
     * Learn an individual point on the robot
     *
     * @param position Point to learn
     * @return Result with success/failure
     */
    public Result learnPoint(Position position)
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: learnPoint " + position.getName());
            return new Result();
        }
        return runRobotCommand(position.getRoboforth());
    }

    /**
     * Cause the gripper to close
     *
     * @return Result with success/fail info
     */
    public Result grip()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: grip");
            return new Result();
        }
        return runRobotCommand("GRIP");
    }

    /**
     * Cause the gripper to open
     *
     * @return Result with success/fail info
     */
    public Result ungrip()
    {
        if (Simulated)
        {
            System.out.println("ArmOperations: ungrip");
            return new Result();
        }
        return runRobotCommand("UNGRIP");
    }

    /**
     * restart the controller, which resets all routes and points and starts fresh
     * 
     * @return Result with success/fail info
     */
    public Result restartController() {
        if (Simulated) {
            System.out.println("ArmOperations: restartController");
            return new Result();
        }
        Result result = runRobotCommand("ROBOFORTH");
        if (!result.success())
            return result;
        return runRobotCommand("START");
    }
    
    /**
     * Sends an individual command to robot and looks for errors
     *
     * @param commandString command to execute
     * @return Result with success/fail info
     */
    private Result runRobotCommand(String commandString)
    {
        r12o.write(commandString);
        ResponseObject response = r12o.getResponse(commandString);

        if (!response.isSuccessful())
        {
            return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        return new Result();
    }

    /**
     * Converts the Position object into a RoboForth String to modify the given
     * route at the given position.
     *
     * @param c Position point
     * @param routeName Name of the Route to modify
     * @param pos Position in the route to modify
     * @return RoboForth String to modify the given route at the given position
     */
    private String positionCommandToRouteModifyString(Position c, String routeName, int pos)
    {
        return "DECIMAL " + c.getRoll() + " " + c.getYaw() + " " + c.getPitch() + " " + c.getZ() + " " + c.getY() + " " + c.getX() + " " + routeName + " " + pos + " LINE DLD";
    }

}
