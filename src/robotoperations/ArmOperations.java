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
import java.util.Map;
import route.Calibration;
import route.DynamicRoute;
import route.Position;
import route.PositionLookup;
import route.Route;
import route.RouteCompiler;
import utils.FileUtils;
import static utils.FileUtils.readINIFile;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class ArmOperations {

    private boolean armOpsSimulated = false;
    private boolean r12OpsSimulated = false;

    public static int armMaxSpeed = 30000;
    public static int armMaxAccel = 3000;
	public static int armSpeedOffset = 250;

    private final boolean armOpsLogging = true;
    private R12Operations r12o = null;
    private RouteCompiler rc = null;
    private PositionLookup plt = null;
    private static ArmOperations armOperations = null;
    private Position calibratePosition = null;				// tracks current arm position when doing calibration, at the OUT-TOP plunge position
	private String calibratePlunge = "OUT-TOP";				// tracks current arm plunge position when doing calibration
	private HashMap<String, Position> calibratePlungeMap;	// tracks the plunge map for the current calibrate position
/*
	private Position adjustmentPosition = null;
    private CabinetType calibrateCabinet = null;
    private int calibrateShelf;
    private String calibratePlunge;
    private int calibrateDepth;
*/
	
    private DynamicRoute dynRoute = new DynamicRoute();
    private enum Gripper { UNKNOWN, GRIPPED, UNGRIPPED }
    private Gripper isGripped = Gripper.UNKNOWN;


    /**
     * Constructor is private since this is a singleton
     */
    private ArmOperations() {
    }

    /**
     * initialize the various modules for controlling the arm
     * 
     * @return Result w/success or fail info
     */
    public Result init() {
        r12o = R12Operations.getInstance();
        rc = RouteCompiler.getInstance();
        plt = PositionLookup.getInstance();

        Result result;

        // load up the INI file to establish our settings
        // load up the INI file with configuration info
        Map<String, String> map = readINIFile(FileUtils.getFilesFolderString() + "R12Setup.ini", 
            "arm", new String[] { "max_speed", "max_accel", "simulate", "speed_offset" });
        if (map == null)
            return new Result("Unable to load INI file " + FileUtils.getFilesFolderString() + "R12Setup.ini");

        // now load the values from the map
        if ( (map.get("simulate") != null) && 
            (map.get("simulate").toLowerCase().equals("true")) )
            armOpsSimulated = true;
        else
            armOpsSimulated = false;
        if (map.get("max_speed") != null)
            armMaxSpeed = Integer.parseInt(map.get("max_speed"));
        if (map.get("max_accel") != null)
            armMaxAccel = Integer.parseInt(map.get("max_accel"));
		if (map.get("speed_offset") != null)
			armSpeedOffset = Integer.parseInt(map.get("speed_offset"));

        // if not simulated, proceed to initialize the r12 ops
        if (!armOpsSimulated) {
            result = r12o.init();
            if (!result.success())
                return result;
            // track if r12 is simulated
            r12OpsSimulated = r12o.simulated();
        }

        // run the controller initialization commands (program forth, etc)
        result = runInitCommands();
        if (!result.success())
            return result;

        // initialize the route compiler
        result = rc.init();
        return result;
    }

    private Result runInitCommands() {
        final String INIT_COMMANDS_FILEPATH = FileUtils.getFilesFolderString() + "R12InitCommands.txt";
        final String INIT_FILE_HEADER = ""
            + "//This is the R12 Robot Init File. This file is where commands are placed in the RoboForth"
            + "\n//Language and are called upon startup by the ThreadCommand thread. "
            + "\n//Each command should be seperated by a carriage return."
            + "\n//All comments must be on their own line and start with \"//\"";

        //if init command file exists, read all the commands and write them out to the 
        ArrayList<String> initCommands = FileUtils.readCommandFileOrGenEmpty(INIT_COMMANDS_FILEPATH, INIT_FILE_HEADER);

        System.out.println("Read " + initCommands.size() + " command(s) from init commands file " + INIT_COMMANDS_FILEPATH);

        for (String command : initCommands) {
            Result result = runRobotCommand(command);
            if (!result.success())
                return result;
        }
        return new Result();
    }

    /**
     * Implement singleton interface to this class
     * 
     * @return the singleton of this class
     */
    public static ArmOperations getInstance() {
        if (armOperations == null)
            armOperations = new ArmOperations();
        return armOperations;
    }


    /**
     * Run a dynamic route.  Note that this does not actually run the route, but enqueues
     * it along with other route runs.  An operation that requires the route to
     * execute (like a grip or ungrip, via pick/drop) will run it, or you can use
     * flush to force it to execute.
     *
     * @param route the route to run
     * @param start the starting coordinate to use on the route
     * @param end   the ending coordinate to use on the route
     *
     * @return Result with success of failure information)
     */
    public Result runRoute(Route route, Position start, Position end) {
        if (armOpsLogging)
            System.out.println("    ArmOperations: runDynamicRoute " + route.getRouteProperties().getRouteFriendlyName()
                + " from " + ((start != null) ? start.getName() : "undefined") + " to "
                + ((end != null) ? end.getName() : "undefined"));

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(2000);
            return new Result();
        }

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
            if ((curPos != null) && (curPos.hasDelta())) {
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
            if (curPos != null)
                dynRoute.addPosition(curPos);

            // save our current position for next time
            priorPos = curPos;
        }

        return new Result();
    }
	
	/**
	 * Flushes any pending dynamic route to the robot controller
	 * 
	 * @return Result with success/fail info
	 */
	public Result flush() {
		return dynRoute.run();
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
     * @param cabinet        Cabinet to locate
     * @param shelf          Shelf within the cabinet
     * @param plungePosition Name of the plunge position (in-top, in-bottom,
     *                       out-top, out-bottom)
     * @param depth          Plunge depth (1 for desktop, 1=bottom and 2=top for
     *                       cachepoint)
     * @param speed          Speed to run at, or -1 for default
     *
     * @return Result with success/fail info
     */
    public Result calibratePoint(CabinetType cabinet, int shelf, String plungePosition, int depth, int speed) {
        // save our cabinet and shelf
		/*
        calibrateCabinet = cabinet;
        calibrateShelf = shelf;
        calibratePlunge = plungePosition;
        calibrateDepth = depth;
        */
		
        // make sure the dynamic route is empty - drop it on the floor since we are doing a calibrate so we
        // can safely ignore it
        dynRoute.clear();

        if (armOpsLogging)
            System.out.println("    ArmOperations: calibratePoint " + cabinet.toString()
                    + ", shelf " + shelf + ", plungePosition " + plungePosition + ", depth " + depth);

        // determine our move position, our adjustments to alter, and the various plunge positions
        calibratePosition = PositionLookup.getInstance().shelfToPosition(cabinet, shelf);
        if (calibratePosition == null)
            return new Result("Unable to locate cabinet " + cabinet.toString() + " shelf " + shelf);
//        adjustmentPosition = Calibration.getInstance().get(calibratePosition);
		
		// and store the plunge away for further adjustments in calibrateAdjust
		calibratePlunge = plungePosition;
		// and store the plunge map as well
        calibratePlungeMap = plungePositions(cabinet, shelf, depth, calibratePosition);

		return _calibrateMoveArm(speed);
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
     * @param axis  Axis to adjust (x, y, z, pitch, yaw, roll)
     * @param value Value to move by (in mm)
     * @param speed Speed to move at
     *
     * @return Result with success/fail info
     */
    public Result calibrateAdjust(String axis, double value, int speed) {
        if (armOpsLogging)
            System.out.println("    ArmOperations: calibrateAdjust, axis " + axis + ", value " + value);

        // make sure the dynamic route is empty - drop it on the floor since we are doing a calibrate so we
        // can safely ignore it
        dynRoute.clear();

        if (speed > armMaxSpeed)
            speed = armMaxSpeed;

        if (calibratePosition == null)
            return new Result("Unknown calibrate location");

		// perform the adjustment for all four plunge positions
		String plunge;
		for (int index = 0; index < 4; ++index) {
			
			switch (index) {
				case 0:
					plunge = "in-top";
					break;
				case 1:
					plunge = "in-bottom";
					break;
				case 2:
					plunge = "out-top";
					break;
				case 3:
					plunge = "out-bottom";
					break;
				default:
					plunge = null;
					break;
			}
			Position plungePos;
			
			if (calibratePlungeMap != null) {
				plungePos = calibratePlungeMap.get(plunge);
				if (plungePos == null)
						return new Result("Unable to locate plungePosition " + plunge);
			} else if (index == 0)
				plungePos = calibratePosition;
			else
				// no plunge map, and we already did the first index ... so we are done
				break;
			
			// locate the adjustment position
			Position adjustmentPosition = Calibration.getInstance().get(plungePos);

			// record the adjustment
			switch (axis) {
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
		}
		
		/*
        // now locate the point again (which will execute the adjustment) and move to it
        Position newPosition = PositionLookup.getInstance().shelfToPosition(calibrateCabinet, calibrateShelf);
        if (newPosition == null)
            return new Result("calibrateAdjust unable to locate cabinet " + calibrateCabinet.toString() + " shelf " + calibrateShelf);

        HashMap<String, Position> calibratePlungeMap = plungePositions(calibrateCabinet, calibrateShelf, calibrateDepth, newPosition);
        Position plungePos = calibratePlungeMap.get(calibratePlunge);
        if (plungePos == null)
            return new Result("Unable to locate plungePosition " + calibratePlunge);
        return moveTo(plungePos, speed);
		*/
		
		// now move the arm into the new calibration position
		return _calibrateMoveArm(speed);
    }

		/**
	 * Private method that does the actual arm movement for the calibration methods
	 * 
	 * @param speed preferred speed to run at
	 * @return Result with success/fail
	 */
	private Result _calibrateMoveArm(int speed) {
		// adjust the position to offset based on calibration
		Position adjustedPostion = Calibration.getInstance().adjust(calibratePosition);

		// move to the requested plunge position
        Position plungePos;
        if (calibratePlungeMap != null) {
            plungePos = calibratePlungeMap.get(calibratePlunge);
            if (plungePos == null)
                    return new Result("Unable to locate plungePosition " + calibratePlunge);
        } else
            plungePos = adjustedPostion;
		
        // adjust speed
        if ( (speed == -1) || (speed > armMaxSpeed) )
            speed = armMaxSpeed;
		
        // move the arm
        Result result = moveTo(plungePos, speed);
		if (!result.success())
			return result;
		
		// and drain the route to ensure we complete the movements
		return dynRoute.run();
    }

    /**
     * Pickup a disc from a shelf using a dynamic route. Assumes that the robot
     * is already at the safe pickup location for the specified disc, and is not
     * already holding one
     *
     * @param cabinet       if this is a CP or a desktop
     * @param shelf         shelf within the cabinet
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     *                      and 2 is top disc)
     * @param position      current position of the arm
     *
     * @return Result with success/failure info
     */
    public Result pick(CabinetType cabinet, int shelf, int stackPosition, Position position) {
        HashMap<String, Position> plunge = plungePositions(cabinet, shelf, stackPosition, position);
        Result result;

        if (armOpsLogging)
            System.out.println("    ArmOperations: pick dynamic from " + cabinet.toString() + " position " + stackPosition + " starting at " + position.getName());

        // make sure the stackPosition is legit
        if ((stackPosition < 1) || (stackPosition > 2))
            return new Result("Invalid stackPosition of " + stackPosition + " passed to pick");

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(1000);
            return new Result();
        }
		
		// before we ungrip (which will dequeue the dynamic route), let's see if we can optimize
		// the position of the head.  The normal route ends with the last two positions in the out-top
		// position, but then swinging the gripper into position.  Let's see if that is the case, and
		// if so, let's update that to instead use the out-bottom position.
		Position outTop = plunge.get("out-top");
		Position outBottom = plunge.get("out-bottom");
		dynRoute.neighborXYZSwapBackward(outTop, outBottom);
		
        // ungrip in prep to get the disc
        result = ungrip();
        if (!result.success())
            return result;
		
        // move into the cabinet
        dynRoute.addPosition(outBottom);
        dynRoute.addPosition(plunge.get("in-bottom"));

        // now grip the disc
        result = grip();
        if (!result.success())
            return result;

        // lift the disc
        dynRoute.addPosition(plunge.get("in-top"));

        // and return to out-top
        dynRoute.addPosition(plunge.get("out-top"));
        return result;
    }

    /**
     * Drop off a disc to a shelf using dynamic routes. Assumes that the robot
     * is already at the safe dropoff location for the specified disc, and is
     * currently holding a disc
     *
     * @param cabinet       if this is a CP or a desktop
     * @param shelf         shelf within the cabinet
     * @param stackPosition stack position (when CP) - where 1 is bottom disc,
     *                      and 2 is top disc)
     * @param position      off of which the relative route is run
     *
     * @return Result with success/fail info
     */
    public Result drop(CabinetType cabinet, int shelf, int stackPosition, Position position) {
        HashMap<String, Position> plunge = plungePositions(cabinet, shelf, stackPosition, position);
        Result result;

        if (armOpsLogging)
            System.out.println("    ArmOperations: drop dynamic at " + cabinet.toString() + " position " + stackPosition + " starting at " + position.getName());

        // make sure the stackPosition is legit
        if ((stackPosition < 1) || (stackPosition > 2))
            return new Result("Invalid stackPosition of " + stackPosition + " passed to drop");

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(1000);
            return new Result();
        }

        // move in
        dynRoute.addPosition(plunge.get("in-top"));
        // and down to position
        dynRoute.addPosition(plunge.get("in-bottom"));

        // now ungrip
        result = ungrip();
        if (!result.success())
            return result;

        // and pull out
        dynRoute.addPosition(plunge.get("out-bottom"));

        // and return to out-top
// dropping this, because we no longer need to return to the original starting spot
//        dynRoute.addPosition(plunge.get("out-top"));
		
		// and inform dynamic route that it's ok to do switching of positions
		// from out-top (which is likely the next two positions in the route) 
		// to the current position's XYZ coordinates (this is a hyper optimization
		// that eliminates one unnecessary movement of the arm)
		dynRoute.neighborXYZSwapForward(plunge.get("out-top"), plunge.get("out-bottom"));

        return new Result();
    }

    /**
     * Compute a series of Positions for plunging into a cabinet to pick up a
     * disc.
     *
     * @param cabinet       Cabinet we are working with
     * @param shelf         Shelf within the cabinet
     * @param stackPosition Stack position (desktop always 1, but can be 1 or 2
     *                      for a CachePoint, with 2 being top disc)
     * @param position      Current position of the arm in front of the cabinet
     *
     * @return Map of Positions, containing "in-top", "in-bottom", and
     *         "out-bottom" (position is the "out-top" so not computed)
     */
    private HashMap<String, Position> plungePositions(CabinetType cabinet, int shelf, int stackPosition, Position position) {
        // if not in a plunge cabinet, just make a simple map of the out-top position only
        if ((cabinet != CabinetType.D1) && (cabinet != CabinetType.D2) && (cabinet != CabinetType.CPL)
            && (cabinet != CabinetType.CPM) && (cabinet != CabinetType.CPR)) {
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

		// setup deltaZ based on the cabinet type and stack position
        double deltaZ = (desktopZval);
        if (cabinet == CabinetType.CPL || cabinet == CabinetType.CPM || cabinet == CabinetType.CPR)
                deltaZ = (stackPosition == 2) ? smallZval : bigZval;

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
        if (cabinet == CabinetType.D2) {
            absEndX = absStartX - deltaAxis + offsetX;
            deltaX = -deltaAxis + offsetX;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) - 135 + offsetYaw;
        } else if (cabinet == CabinetType.D1) {
            absEndX = absStartX + deltaAxis + offsetX;
            deltaX = deltaAxis + offsetX;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + 135 + offsetYaw;
        } else if (cabinet == CabinetType.CPL || cabinet == CabinetType.CPM || cabinet == CabinetType.CPR) {
            absEndY = absStartY + moveval + offsetY;
            absEndX = absStartX + offsetX;
            deltaX = 0 + offsetX;
            deltaY = moveval + offsetY;
            yaw = -Math.toDegrees(Math.atan2(absEndX, absEndY)) + offsetYaw;
        } else {
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
    public Result calibrate() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: calibrate");

        // since this is a total reset ofthe world, make sure the dynamic route is clear
        dynRoute.clear();

        // is simulated, take a bit of time and move on
        if (armOpsSimulated && !r12OpsSimulated) {
           Utils.sleep(2000);
           return new Result();
        }

        // do calibrate, and then make sure we are ungripped to start
        Result result = runRobotCommand(Integer.toString(armMaxSpeed) + " SPEED ! CALIBRATE");
        if (!result.success())
            return result;

        return ungrip();
    }

    /**
     * Move the robot to the HOME position, using a safe route that will not hit
     * any objects
     *
     * @return Result with success/fail info
     */
    public Result home() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: home");
        
        // flush the dynamic route
        Result result = dynRoute.run();
        if (!result.success())
            return result;

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(1000);
            return new Result();
        }

        result = runRobotCommand("HOME");
        if (!result.success())
            return result;

        return ungrip();
    }

    /**
     * Tell the robot to energize the arm
     *
     * @return Result with success/fail info
     */
    public Result energize() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: energize");

        if (armOpsSimulated && !r12OpsSimulated) {
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
    public Result deEnergize() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: de-energize");

        if (armOpsSimulated && !r12OpsSimulated) {
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
    public Result moveTo(Position position) {
        return moveTo(position, armMaxSpeed);
    }

    /**
     * move the robot arm to a specific position at a specific speed
     *
     * @param speed speed to move at
     * @param position position to move to
     *
     * @return Result with success/fail info
     */
    public Result moveTo(Position position, int speed) {
        if (armOpsLogging)
                System.out.println("    ArmOperations: position to " + position.getName() + ", speed " + speed);

        // make sure dynamic route completes it's movements
        Result result = dynRoute.run();
        if (!result.success())
            return result;

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(1000);
            return new Result();
        }
		
		// get an adjusted position using Calibration tables
		position = Calibration.getInstance().adjust(position);

        return runRobotCommand(
            Integer.toString(speed) + " SPEED ! "
            + Integer.toString(armMaxAccel) + " ACCEL ! "
            + position.getPitchStr() + " PITCH ! "
            + position.getYawStr() + " YAW ! "
            + position.getRollStr() + " ROLL ! "
            + position.getXStr() + " "
            + position.getYStr() + " "
            + position.getZStr() + " MOVETO");
    }

    /**
     * Cause the gripper to close
     *
     * @return Result with success/fail info
     */
    public Result grip() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: grip");

        // if we are already gripped, skip this
        if (isGripped == Gripper.GRIPPED)
            return new Result();

        // make sure the dynamic route is fully executed
        Result result = dynRoute.run();
        if (!result.success())
            return result;
        
        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(100);
            isGripped = Gripper.GRIPPED;
            return new Result();
        }

        result = runRobotCommand("GRIP");
        if (!result.success()) {
            isGripped = Gripper.UNKNOWN;
            return result;
        }
        
        isGripped = Gripper.GRIPPED;
        return result;
    }

    /**
     * Cause the gripper to open
     *
     * @return Result with success/fail info
     */
    public Result ungrip() {
        if (armOpsLogging)
            System.out.println("    ArmOperations: ungrip");

        // if we are already ungripped, skip this
        if (isGripped == Gripper.UNGRIPPED)
            return new Result();


        // make sure the dynamic route is fully executed
        Result result = dynRoute.run();
        if (!result.success())
            return result;

        if (armOpsSimulated && !r12OpsSimulated) {
            Utils.sleep(100);
            isGripped = Gripper.UNGRIPPED;
            return new Result();
        }

        result = runRobotCommand("UNGRIP");
        if (!result.success()) {
            isGripped = Gripper.UNKNOWN;
            return result;
        }
        
        isGripped = Gripper.UNGRIPPED;
        return result;
    }

    /**
     * Sends an individual command to robot and looks for errors
     *
     * @param commandString command to execute
     *
     * @return Result with success/fail info
     */
    public Result runRobotCommand(String commandString) {
        // make sure we are not simulated
        if (!(armOpsSimulated && !r12OpsSimulated)) {
            r12o.write(commandString);
            ResponseObject response = r12o.getResponse(commandString);

            if (!response.isSuccessful())
                return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        return new Result();
    }

    /**
     * Sends an individual command to robot and looks for errors
     *
     * @param commandString command to execute
	 * @param pattern A response pattern to process against the resulting message
     *
     * @return Result with success/fail info
     */
    public Result runRobotCommand(String commandString, ResponsePattern pattern) {
        // make sure we are not simulated
        if (!(armOpsSimulated && !r12OpsSimulated)) {
            r12o.write(commandString);
            ResponseObject response = r12o.getResponse(commandString);
			if (pattern != null) {
				if (!r12OpsSimulated)
					pattern.process(response.getMsg());
				else
					pattern.process("SPEED = 12000 OK");
			}
			
            if (!response.isSuccessful())
                return new Result("Command Failed! Cmd: " + commandString + " Response Msg: " + response.getMsg());
        }
        return new Result();
    }

}
