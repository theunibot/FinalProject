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
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import route.DynamicRoute;
import route.Position;
import route.PositionLookup;
import route.Route;
import route.RouteCompiler;
import route.RouteHolder;
import utils.FileUtils;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class ArmOperations
{

    private final boolean armOpsSimulated = true;
    private final boolean r12OpsSimulated = true;

    public final static int ARM_MAX_SPEED = 3000;
    public final static int ARM_MAX_ACCEL = 2000;
    private int armSpeed = ARM_MAX_SPEED;
    private int armAccel = ARM_MAX_ACCEL;

    private final boolean armOpsLogging = true;
    private R12Operations r12o = null;
    private RouteCompiler rc = null;
    private PositionLookup plt = null;
    private RouteHolder rh = null;
    private static ArmOperations armOperations = null;
    private boolean debugMode = false;
    private int speed;
    private boolean changeSpeed = false;
    private Semaphore debugSemaphore = new Semaphore(0);
    private boolean debugSkip = false;
    private boolean debugFail = false;
    private boolean debugAdjust = false;
    private String debugAdjustAxis;
    private double debugAdjustValue;
    private Position calibratePosition = null;
    private Position adjustmentPosition = null;
    private CabinetType calibrateCabinet = null;
    private int calibrateShelf;
    private String calibratePlunge;
    private int calibrateDepth;

    //Regular Objects
    private ArrayList<String> initCommands = null;

    //Strings
    public static final String RESPONSE_OK = "OK";

    private final String INIT_COMMANDS_FILENAME = "R12InitCommands.txt";
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

        Result result;
        
        if (!(armOpsSimulated && !r12OpsSimulated)) {
            result = r12o.init(r12OpsSimulated);
            if (!result.success())
            {
                return result;
            }
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

        System.out.println("Read " + initCommands.size() + " command(s) from init commands file " + INIT_COMMANDS_FILEPATH);

        // also set defaults for speed and acceleration
        initCommands.add(String.valueOf(armSpeed) + " SPEED !");
        initCommands.add(String.valueOf(armAccel) + " ACCEL !");
        for (String command : initCommands)//runs every command in the file
        {
            Result result = runRobotCommand(command);
            if (!result.success())
            {
                return result;
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
     * Front-end for running routes, to make it easy to switch between dynamic and static routes
     * 
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return Result with success of failure information)
     */
    public Result runRoute(Route route, Position start, Position end) {
        return runDynamicRoute(route, start, end);
//        return runStaticRoute(route, start, end);
    }

    /**
     * Run a static route, with a modified starting and ending Position coordinates
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return Result with success of failure information)
     */
    public Result runStaticRoute(Route route, Position start, Position end)
    {
        if (armOpsLogging)
            System.out.println("    ArmOperations: runRoute " + route.getRouteProperties().getRouteFriendlyName()
                    + " from " + ((start != null) ? start.getName() : "undefined") + " to "
                    + ((end != null) ? end.getName() : "undefined"));

        if (armOpsSimulated && !r12OpsSimulated){            
            Utils.sleep(2000);
            return new Result();
        }

        ResponseObject response;
        if (route.size() >= 2) {
            //must have start and end pos to modify
            if (start != null) {
                //run the modify start command
                String modStart = positionCommandToRouteModifyString(start, route.getRouteProperties().getRouteIDName(), 1);
                Result result = runRobotCommand(modStart);
                if (!result.success())
                    return result;
            }

            if (end != null) {
                //run the modify end command
                String modEnd = positionCommandToRouteModifyString(end, route.getRouteProperties().getRouteIDName(), route.size());
                Result result = runRobotCommand(modEnd);
                if (!result.success())
                    return result;
            }

            // see if any positions in the route require delta adjustment
            Position priorPos = null;
            for (int routeIndex = 0; routeIndex < route.size(); ++routeIndex) {
                Position curPos;

                // determine the current route position in the route
                if (routeIndex == 0)
                    curPos = start;
                else if (routeIndex == (route.size() - 1))
                    curPos = end;
                else
                    curPos = route.get(routeIndex).getPosition();
                
//System.out.println("***** Processing " + routeIndex + ": " + curPos);
                if ( (curPos != null) && (curPos.hasDelta()) ) {
//System.out.println("***** Has delta");
                    // determine prior position
                    Position nextPos;

                    if (routeIndex >= (route.size() - 2))
                        nextPos = end;
                    else
                        nextPos = route.get(routeIndex + 1).getPosition();

                    // this line is a delta - so compute the varient position
//System.out.println("***** prior: " + priorPos);
//System.out.println("***** next: " + nextPos);
                    Position adjPos;
                    if (route.getRouteProperties().getReverse())
                        adjPos = curPos.getDeltaPosition(nextPos, priorPos);
                    else
                        adjPos = curPos.getDeltaPosition(priorPos, nextPos);
//System.out.println("***** adusted is: " + adjPos);
                    String modMiddle = positionCommandToRouteModifyString(adjPos, route.getRouteProperties().getRouteIDName(), routeIndex + 1);
                    // execute the route change
                    Result result = runRobotCommand(modMiddle);
                    if (!result.success())
                        return result;
                    // save our adjusted position for next time...
                    priorPos = adjPos;
                } else
                    // save our current position for next time
                    priorPos = curPos;
            
            }

            // run the route
            int routeSpeed = route.getRouteProperties().getRouteSpeed();
            String runRoute = Integer.toString((armSpeed < routeSpeed) ? armSpeed : routeSpeed) + " SPEED !  CONTINUOUS ADJUST " + route.getRouteProperties().getRouteIDName() + " RUN";
            Result result = runRobotCommand(runRoute);
            if (!result.success())
                return result;

            return new Result();
        }
        else {
            //not enough pos to modify start and end routes
            return new Result("Route named " + route.getRouteProperties().getRouteFriendlyName() + " has " + route.size() + " coordinates; must have at least two (start and end)");
        }
    }
    
    
    /**
     * Run a dynamic route (one not previously programmed)
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end the ending coordinate to use on the route
     * @return Result with success of failure information)
     */
    public Result runDynamicRoute(Route route, Position start, Position end)
    {
        
        if (armOpsLogging)
            System.out.println("    ArmOperations: runDynamicRoute " + route.getRouteProperties().getRouteFriendlyName()
                    + " from " + ((start != null) ? start.getName() : "undefined") + " to "
                    + ((end != null) ? end.getName() : "undefined"));

        if (armOpsSimulated && !r12OpsSimulated){            
            Utils.sleep(2000);
            return new Result();
        }
        // set up our dynamic route for tracking all positions
        DynamicRoute dr = new DynamicRoute();

        // see if any positions in the route require delta adjustment
        Position priorPos = null;
        for (int routeIndex = 0; routeIndex < route.size(); ++routeIndex) {
            Position curPos;

            // determine the current route position in the route, modifying for the custom start/end positions
            if (routeIndex == 0)
                curPos = start;
            else if (routeIndex == (route.size() - 1))
                curPos = end;
            else
                curPos = route.get(routeIndex).getPosition();

            // do we need to adjust the position relative to other ones? 
            if ( (curPos != null) && (curPos.hasDelta()) ) {
                // determine prior position
                Position nextPos;

                if (routeIndex >= (route.size() - 2))
                    nextPos = end;
                else
                    nextPos = route.get(routeIndex + 1).getPosition();

                // this line is a delta - so compute the varient position
                if (route.getRouteProperties().getReverse())
                    curPos = curPos.getDeltaPosition(nextPos, priorPos);
                else
                    curPos = curPos.getDeltaPosition(priorPos, nextPos);
            }
            // add to the dynamic route
            dr.addPosition(curPos);
            
            // save our current position for next time
            priorPos = curPos;
        }

        // run the route
        int routeSpeed = route.getRouteProperties().getRouteSpeed();
        if (routeSpeed > armSpeed)
            routeSpeed = armSpeed;
        int routeAccel = route.getRouteProperties().getRouteAccel();
        if (routeAccel > armAccel)
            routeAccel = armAccel;
        String runRoute = Integer.toString(routeAccel) + " ACCEL ! " + Integer.toString(routeSpeed) + " SPEED ! ROUTE UDTEMP";
        Result result = runRobotCommand(runRoute);
        if (!result.success())
            return result;
        runRoute = dr.routeCommand();
        result = runRobotCommand(runRoute);
        if (!result.success())
            return result;

        return new Result();
    }


    /**
     * Executes calibration services for the arm for a specific point. Moves the
     * arm to the specified cabinet / shelf location, at one of a variety of
     * specific points (called plunge positions). Once in any of these
     * positions, the calibrateAdjust call can be made which will move the arm
     * by specified relative routes and track those changes with the specified
     * point. Finally, once one or more points have been calibrated, the
     * adjustment table should be persisted to disk (which is
     * PositionLookup.saveAdjustmentFile).
     *
     * @param cabinet Cabinet to locate
     * @param shelf Shelf within the cabinet
     * @param plungePosition Name of the plunge position (in-top, in-bottom,
     * out-top, out-bottom)
     * @param depth Plunge depth (1 for desktop, 1=bottom and 2=top for
     * cachepoint)
     * @param speed Speed to set the arm to before doing the move
     * @return Result with success/fail info
     */
    public Result calibratePoint(CabinetType cabinet, int shelf, String plungePosition, int depth, int speed)
    {
        // save our cabinet and shelf
        calibrateCabinet = cabinet;
        calibrateShelf = shelf;
        calibratePlunge = plungePosition;
        calibrateDepth = depth;

        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: calibratePoint " + cabinet.toString()
                    + ", shelf " + shelf + ", plungePosition " + plungePosition + ", depth " + depth + ", speed " + speed);
        }

        // set speed for all actions
        if (armSpeed < ARM_MAX_SPEED)
            armSpeed = speed;
        // determine our move position, our adjustments to alter, and the various plunge positions
        calibratePosition = PositionLookup.getInstance().shelfToPosition(cabinet, shelf);
        if (calibratePosition == null)
        {
            return new Result("Unable to locate cabinet " + cabinet.toString() + " shelf " + shelf);
        }
        adjustmentPosition = PositionLookup.getInstance().shelfToAdjustmentPosition(cabinet, shelf);
        HashMap<String, Position> plungeMap = plungePositions(cabinet, shelf, depth, calibratePosition);

        // set the speed
        Result result = runRobotCommand(String.valueOf(speed) + " SPEED !");
        if (!result.success())
        {
            return result;
        }

        // move to the requested position
        Position plungePos;
        if (plungeMap != null)
        {
            plungePos = plungeMap.get(calibratePlunge);
            if (plungePos == null)
            {
                return new Result("Unable to locate plungePosition " + calibratePlunge);
            }
        }
        else
        {
            plungePos = calibratePosition;
        }
        return moveTo(plungePos);
    }

    /**
     * Calibrate the last point specified to the calibrate call. Use this
     * carefully, because if the arm is not still at the last point called by
     * calibration, you could be calibrating to the wrong point. The result of
     * this calibration will move the arm and also record the change in the
     * adjustment positions map. To persist these changes, use the
     * PositionLookup.saveAdjustmentFile() call. These changes will persist in
     * memory and take effect for live testing.
     *
     * @param axis Axis to adjust (x, y, z, pitch, yaw, roll)
     * @param value Value to move by (in mm)
     * @return Result with success/fail info
     */
    public Result calibrateAdjust(String axis, double value)
    {
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: calibrateAdjust, axis " + axis + ", value " + value);
        }

        if (adjustmentPosition == null)
        {
            return new Result("Unknown calibrate location");
        }

        // record the adjustment
        switch (axis)
        {
            case "x":
                adjustmentPosition.setX(adjustmentPosition.getX() + value);
                break;
            case "y":
                adjustmentPosition.setY(adjustmentPosition.getY() + value);
                break;
            case "z":
                adjustmentPosition.setZ(adjustmentPosition.getZ() + value);
                break;
            case "pitch":
                adjustmentPosition.setPitch(adjustmentPosition.getPitch() + value);
                break;
            case "yaw":
                adjustmentPosition.setYaw(adjustmentPosition.getYaw() + value);
                break;
            case "roll":
                adjustmentPosition.setRoll(adjustmentPosition.getRoll() + value);
                break;
        }

        // now locate the point again (which will execute the adjustment) and move to it
        Position newPosition = PositionLookup.getInstance().shelfToPosition(calibrateCabinet, calibrateShelf);
        if (newPosition == null)
        {
            return new Result("calibrateAdjust unable to locate cabinet " + calibrateCabinet.toString() + " shelf " + calibrateShelf);
        }

        HashMap<String, Position> plungeMap = plungePositions(calibrateCabinet, calibrateShelf, calibrateDepth, newPosition);
        Position plungePos = plungeMap.get(calibratePlunge);
        if (plungePos == null)
        {
            return new Result("Unable to locate plungePosition " + calibratePlunge);
        }
        return moveTo(plungePos);
    }

    /**
     * Pickup a disc from a shelf. Assumes that the robot is already at the safe
     * pickup location for the specified disc, and is not already holding one
     *
     * @param cabinet if this is a CP or a desktop
     * @param shelf shelf within the cabinet
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @param position current position of the arm
     * @return Result with success/failure info
     */
    public Result pick(CabinetType cabinet, int shelf, int stackPosition, Position position) {
        HashMap<String, Position> plunge = plungePositions(cabinet, shelf, stackPosition, position);
        Result result;
        
        if (armOpsLogging)
            System.out.println("    ArmOperations: pick from " + cabinet.toString() + " position " + stackPosition + " starting at " + position.getName());

        // make sure the stackPosition is legit
        if ((stackPosition < 1) || (stackPosition > 2))
            return new Result("Invalid stackPosition of " + stackPosition + " passed to pick");

        if (armOpsSimulated && !r12OpsSimulated){
            Utils.sleep(1000);
            return new Result();
        }

        result = runRobotCommand(plunge.get("out-bottom"));
        if (!result.success())
            return result;
        
        // ungrip in prep to get the disc
        result = runRobotCommand("UNGRIP");
        if (!result.success())
            return result;

        // move into the cabinet
        result = runRobotCommand(plunge.get("in-bottom"));
        if (!result.success())
            return result;

        // now grip the disc
        result = runRobotCommand("GRIP");
        if (!result.success())
            return result;

        // lift the disc
        result = runRobotCommand(plunge.get("in-top"));
        if (!result.success())
            return result;

        // and return to out-top
        result = runRobotCommand(plunge.get("out-top"));
        if (!result.success())
            return result;

        return new Result();
    }

    /**
     * Drop off a disc to a shelf. Assumes that the robot is already at the safe
     * dropoff location for the specified disc, and is currently holding a disc
     *
     * @param cabinet if this is a CP or a desktop
     * @param shelf shelf within the cabinet
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     * and 2 is top disc)
     * @param position off of which the relative route is run
     * @return Result with success/fail info
     */
    public Result drop(CabinetType cabinet, int shelf, int stackPosition, Position position) {
        HashMap<String, Position> plunge = plungePositions(cabinet, shelf, stackPosition, position);
        Result result;
        
        if (armOpsLogging)
            System.out.println("    ArmOperations: drop at " + cabinet.toString() + " position " + stackPosition + " starting at " + position.getName());

        // make sure the stackPosition is legit
        if ((stackPosition < 1) || (stackPosition > 2))
            return new Result("Invalid stackPosition of " + stackPosition + " passed to drop");

        if (armOpsSimulated && !r12OpsSimulated){
            Utils.sleep(1000);
            return new Result();
        }

        // move in
        result = runRobotCommand(plunge.get("in-top"));
        if (!result.success())
            return result;

        // and down to position
        result = runRobotCommand(plunge.get("in-bottom"));
        if (!result.success())
            return result;

        // now ungrip
        result = runRobotCommand("UNGRIP");
        if (!result.success())
            return result;

        // and pull out
        result = runRobotCommand(plunge.get("out-bottom"));
        if (!result.success())
            return result;

        // regrip
        result = runRobotCommand("GRIP");
        if (!result.success())
            return result;

        // and return to out-top
        result = runRobotCommand(plunge.get("out-top"));
        if (!result.success())
            return result;

        return new Result();
    }

    /**
     * Compute a series of Positions for plunging into a cabinet to pick up a
     * disc.
     *
     * @param cabinet Cabinet we are working with
     * @param shelf Shelf within the cabinet
     * @param stackPosition Stack position (desktop always 1, but can be 1 or 2
     * for a CachePoint, with 2 being top disc)
     * @param position Current position of the arm in front of the cabinet
     * @return Map of Positions, containing "in-top", "in-bottom", and
     * "out-bottom" (position is the "out-top" so not computed)
     */
    private HashMap<String, Position> plungePositions(CabinetType cabinet, int shelf, int stackPosition, Position position)
    {
        // if not in a plunge cabinet, just make a simple map of the out-top position only
        if ((cabinet != CabinetType.D1) && (cabinet != CabinetType.D2) && (cabinet != CabinetType.CPL)
                && (cabinet != CabinetType.CPM) && (cabinet != CabinetType.CPR))
        {
            HashMap<String, Position> map = new HashMap<>();
            map.put("out-top", position);
            return map;
        }
        // locate the values for the plunge depth (X) and Z movement (Y=2 deep, Z=1 deep)
        Position posInfo = plt.shelfToPosition(cabinet, 91);
        // locate the relative offsets that we apply to all insertion positions for this cabinet (X/Y/Z/P/Y/R)
        Position posOffsetInfo = plt.shelfToPosition(cabinet, 90);

//        System.out.println("90: " + posOffsetInfo.toString());
//        System.out.println("91: " + posInfo.toString());

        // convert values into useful double format for our math
        double bigZval = posInfo.getY();
        double smallZval = posInfo.getZ();
        double desktopZval = posInfo.getZ();
        double moveval = posInfo.getX();

        double offsetX = posOffsetInfo.getX();
        double offsetY = posOffsetInfo.getY();
        double offsetZ = posOffsetInfo.getZ();
        double offsetYaw = posOffsetInfo.getYaw();

        // because we are hand computing Yaw from an X/Y position, we need to adjust offSetYaw
        // by the Adustment table amount for Yaw.  The other positions will already have that
        // value taken into account
        Position adjustmentPos = plt.shelfToAdjustmentPosition(cabinet, shelf);
        if (adjustmentPos != null)
        {
            offsetYaw += adjustmentPos.getYaw();
        }

        // setup deltaZ based on the cabinet type and stack position
        double deltaZ = (desktopZval);
        if (cabinet == CabinetType.CPL || cabinet == CabinetType.CPM || cabinet == CabinetType.CPR)
        {
            deltaZ = (stackPosition == 2) ? smallZval : bigZval;
        }

        // determine the out-bottom position
        Position outBottom = new Position(position.getName() + "_OB", position);
        outBottom.setZ(outBottom.getZ() + deltaZ + offsetZ);

        // determine the in-bottom position
        double absStartX = position.getX();
        double absStartY = position.getY();
        double deltaAxis = (moveval) / (Math.sqrt(2.0d));//get the distance forward divided by root2
        double absEndX = 0;
        double absEndY = absStartY - deltaAxis + offsetY;
        double deltaX = 0;
        double deltaY = -deltaAxis + offsetY;
        double yaw = 0;

        //abs startX/Y used to calc abs endX/Y which are used to calc the Yaw
        //deltaX/Y used for MOVE commands
        if (cabinet == CabinetType.D2)
        {
            absEndX = absStartX - deltaAxis + offsetX;
            deltaX = -deltaAxis + offsetX;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) - 135 + offsetYaw;
        }
        else if (cabinet == CabinetType.D1)
        {
            absEndX = absStartX + deltaAxis + offsetX;
            deltaX = deltaAxis + offsetX;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + 135 + offsetYaw;
        }
        else if (cabinet == CabinetType.CPL || cabinet == CabinetType.CPM || cabinet == CabinetType.CPR)
        {
            absEndY = absStartY + moveval + offsetY;
            absEndX = absStartX + offsetX;
            deltaX = 0 + offsetX;
            deltaY = moveval + offsetY;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + offsetYaw;
        }
        else
        {
            System.err.println("Invalid CabinetType: " + cabinet.toString());
            return null;
        }

        // set the in bottom position
        Position inBottom = new Position(position.getName() + "_IB", outBottom);
        inBottom.setYaw(yaw);
        inBottom.setX(inBottom.getX() + deltaX);
        inBottom.setY(inBottom.getY() + deltaY);

        // figure out the in up position
        Position inTop = new Position(position.getName() + "_IU", inBottom);
        inTop.setZ(inTop.getZ() - (deltaZ + offsetZ));

        // store these values into the map
        HashMap<String, Position> map = new HashMap<>();
        map.put("out-top", position);
        map.put("in-bottom", inBottom);
        map.put("out-bottom", outBottom);
        map.put("in-top", inTop);

        return map;
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
        return new Result();
        /*
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: calibrate");
        }

        // reset speed to fast
        armSpeed = ARM_MAX_SPEED;

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(2000);
            return new Result();
        }

        return runRobotCommand(Integer.toString(armSpeed) + " SPEED ! CALIBRATE");
        */
    }

    /**
     * Move the robot to the HOME position, using a safe route that will not hit
     * any objects
     *
     * @return Result with success/fail info
     */
    public Result home()
    {
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: home");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(1000);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: energize");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(100);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: de-energize");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(100);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: position to " + position.getName());
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(1000);
            return new Result();
        }

        //return runRobotCommand(position.getName() + " GOTO");
        return runRobotCommand(
                Integer.toString(armSpeed) + " SPEED ! "
                + Integer.toString(armAccel) + " ACCEL ! "
                + position.getPitchStr() + " PITCH ! "
                + position.getYawStr() + " YAW ! "
                + position.getRollStr() + " ROLL ! "
                + position.getXStr() + " "
                + position.getYStr() + " "
                + position.getZStr() + " MOVETO");
    }

    /**
     * tell the robot to persist all settings into flash memory
     *
     * @return Result with success/fail info
     */
    public Result persist()
    {
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: persist");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(500);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: learnRoute " + route.getRouteProperties().getRouteFriendlyName());
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: learnPoint " + position.getName());
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(100);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: grip");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(100);
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
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: ungrip");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            Utils.sleep(100);
            return new Result();
        }

        return runRobotCommand("UNGRIP");
    }

    /**
     * restart the controller, which resets all routes and points and starts
     * fresh
     *
     * @return Result with success/fail info
     */
    public Result restartController()
    {
        if (armOpsLogging)
        {
            System.out.println("    ArmOperations: restartController");
        }

        if (armOpsSimulated && !r12OpsSimulated)
        {
            return new Result();
        }

        Result result = runRobotCommand("ROBOFORTH");
        if (!result.success())
        {
            return result;
        }
        return runRobotCommand("START");
    }

    /**
     * Turns on/off robot debugging mode, which lets you step through robot
     * commands one at a time
     *
     * @param enable true to enable, false to disable (and return to normal
     * operations)
     * @return Result w/success/fail info
     */
    public Result debug(boolean enable)
    {
        // are we changing mode?
        if (debugMode == enable)
        {
            return new Result();
        }

        // are we currently in debug mode?  If so, make sure nobody is blocked from execution
        if (debugMode)
        // ending debug mode - release any blocked operation
        {
            debugSemaphore.release();
        }
        else
        // starting debug mode - make sure no permits are pending
        {
            debugSemaphore.drainPermits();
        }
        // set the mode
        debugMode = enable;
        return new Result();
    }

    /**
     * Changes the robot speed. Only works when in debug mode.
     *
     * @param speed speed to change robot to
     * @return Result w/success/fail info
     */
    public Result debugSpeed(int speed)
    {
        if (debugMode)
        {
            this.speed = speed;
            this.changeSpeed = true;
            // tell the other thread it can do something
            debugSemaphore.release();
            return new Result();
        }
        return new Result("Robot not in debug mode; change not change speed");
    }

    /**
     * When in debug mode, steps the robot one roboforth statement
     *
     * @return Result w/success/fail info
     */
    public Result debugStep()
    {
        if (debugMode)
        {
            // release one instruction worth of execution
            debugSemaphore.release();
            return new Result();
        }
        return new Result("Robot not in debug mode; cannot step");
    }

    /**
     * When in debug mode, skips over the next roboforth statement
     *
     * @return Result w/success/fail info
     */
    public Result debugSkip()
    {
        if (debugMode)
        {
            debugSkip = true;
            debugSemaphore.release();
            return new Result();
        }
        return new Result("Robot not in debug mode; nothing skipped");
    }

    /**
     * When in debug mode, causes next RF statement to fail
     *
     * @return Result w/success/fail info
     */
    public Result debugFail()
    {
        if (debugMode)
        {
            debugFail = true;
            debugSemaphore.release();
            return new Result();
        }
        return new Result("Robot not in debug mode; cannot set failure");
    }

    /**
     * Adjusts the robot x/y/z/raw/pitch/roll by a specific amount (live)
     *
     * @param axis one of "x", "y", "z", "yaw", "pitch", or "roll"
     * @param value number of 10th mm to move
     * @return Result with success/fail info
     */
    public Result debugAdjust(String axis, double value)
    {
        if (debugMode)
        {
            debugAdjust = true;
            debugAdjustAxis = axis;
            debugAdjustValue = value;
            debugSemaphore.release();
            return new Result();
        }
        return new Result("Robot not in debug mode; can not adjust");
    }

    /**
     * Move the arm to the specified absolute position
     *
     * @param position Absolute position to move to
     * @return Result with success/failure
     */
    private Result runRobotCommand(Position position)
    {
        String command = position.getPitchStr() + " PITCH ! "
                + position.getYawStr() + " YAW ! "
                + position.getRollStr() + " ROLL ! "
                + position.getXStr() + " " + position.getYStr() + " " + position.getZStr() + " MOVETO";
        return runRobotCommand(command);
    }

    /**
     * Sends an individual command to robot and looks for errors
     *
     * @param commandString command to execute
     * @return Result with success/fail info
     */
    private Result runRobotCommand(String commandString)
    {
        // are we in debug mode?
        while (debugMode)
        {
            System.out.println("      Debug: Next command is: " + commandString);
            try
            {
                debugSemaphore.acquire();
            }
            catch (InterruptedException e)
            {
                // let things roll...
            }
            // are we being asked to change speed?
            if (this.changeSpeed)
            {
                this.changeSpeed = false;
                String speedCmd = String.valueOf(this.speed) + " SPEED !";
                r12o.write(speedCmd);
                armSpeed = this.speed;
                ResponseObject response = r12o.getResponse(speedCmd);
                if (!response.isSuccessful())
                {
                    return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
                }
                // return to the debug loop
                continue;
            }
            // do we need to execute an adjustment?
            if (this.debugAdjust)
            {
                String adjustCmd = "";
                this.debugAdjust = false;
                switch (this.debugAdjustAxis)
                {
                    case "x":
                        adjustCmd = String.valueOf(this.debugAdjustValue) + " 0 0 MOVE";
                        break;
                    case "y":
                        adjustCmd = "0 " + String.valueOf(this.debugAdjustValue) + " 0 MOVE";
                        break;
                    case "z":
                        adjustCmd = "0 0 " + String.valueOf(this.debugAdjustValue) + " MOVE";
                        break;
                    case "pitch":
                        adjustCmd = String.valueOf(this.debugAdjustValue) + " PITCH +! 0 0 0 MOVE";
                        break;
                    case "yaw":
                        adjustCmd = String.valueOf(this.debugAdjustValue) + " YAW +! 0 0 0 MOVE";
                        break;
                    case "roll":
                        adjustCmd = String.valueOf(this.debugAdjustValue) + " ROLL +! 0 0 0 MOVE";
                        break;
                }
                r12o.write(adjustCmd);
                ResponseObject response = r12o.getResponse(adjustCmd);
                if (!response.isSuccessful())
                {
                    return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
                }
                // return to the debug loop
                continue;
            }
            // were we asked to skip this instruction?
            if (debugSkip)
            {
                debugSkip = false;
                System.out.println("        Debug: SKIPPING: " + commandString);
                return new Result();
            }
            if (debugFail)
            {
                debugFail = false;
                System.out.println("        Debug: FAILING: " + commandString);
                return new Result("Debug fail requested");
            }
            break;
        }

        // we got a command - go execute it
        if (!(armOpsSimulated && !r12OpsSimulated)) {
            r12o.write(commandString);
            ResponseObject response = r12o.getResponse(commandString);

            if (!response.isSuccessful())
            {
                return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
            }
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
        return "DECIMAL " + c.getRollStr() + " " + c.getYawStr() + " " + c.getPitchStr() + " " + c.getZStr() + " " + c.getYStr() + " " + c.getXStr() + " " + routeName + " " + pos + " LINE DLD";
    }

}
