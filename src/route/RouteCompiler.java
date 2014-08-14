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
package route;

import enums.RouteEffectType;
import enums.CabinetType;
import enums.RouteType;
import robotoperations.ArmOperations;
import utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import utils.Utils;
import utils.Result;

/**
 *
 */
public class RouteCompiler
{

    private static RouteCompiler routeCompiler = null;
    private RouteHolder rh = null;
    private ArmOperations ao = null;

    //String consts
    public static final String ROUTE_FILE_BASENAME = FileUtils.getFilesFolderString() + "allRoutes.txt";
//    pathToFile = FileUtils.getFilesFolderString() + prefix + ROUTE_FILE_BASENAME + ".txt";
    public static final String ROUTE_DEFINE_PREFIX = "route ";

    private final String ROUTE_CLONE_FWD = "F";
    private final String ROUTE_CLONE_REV = "R";

    public static final String[] ROUTE_FILE_PREFIXS =
    {
        "D1_", "D2_", "S_"
    };

    public static final KVPair[] PITCH_ORIENTATION =
    {
        new KVPair("N", "0"), new KVPair("E", "10000"), new KVPair("S", "20000"), new KVPair("W", "30000")
    };

    public static final KVPair[] YAW_ORIENTATION =
    {
        new KVPair("N", "0"), new KVPair("E", "10000"), new KVPair("S", "20000"), new KVPair("W", "30000")
    };

    public static final KVPair[] ROLL_ORIENTATION =
    {
        new KVPair("N", "0"), new KVPair("E", "10000"), new KVPair("S", "20000"), new KVPair("W", "30000")
    };

    private final String ROUTE_COMPILER_FILE_CONTENTS = ""
            + "//This is the R12 Robot Route Compiler file.\n"
            + "\n"
            + ""
            + "//Each command should be on its own line.\n"
            + "//All comments must be on their own line and start with \"//\"\n"
            + "//\n"
            + "//To Be Implemented: To declare a route to be available for use the syntax \"include: <name of route>\" to ensure\n"
            + "//the route is added to a usable list for the program"
            + "\n"
            + "\n#POS1 POS2 EFFECT";

    /**
     * Gets the uniform instance of RouteCompiler
     *
     * @return instance of RouteCompiler
     */
    public static RouteCompiler getInstance()
    {
        if (routeCompiler == null)
        {
            routeCompiler = new RouteCompiler();
        }
        return routeCompiler;
    }

    /**
     * Reads the files to generate the start routes.
     *
     * @return Result with success/fail info
     */
    public Result init()
    {
        rh = RouteHolder.getInstance();
        ao = ArmOperations.getInstance();
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(ROUTE_FILE_BASENAME, ROUTE_COMPILER_FILE_CONTENTS);
        System.out.println("Read " + lines.size() + " line(s) from route compiler file.");
        parseLines(lines);

        for (Route route : rh.getAllRoutes())
        {

            //run fwd route
            Result result = ao.learnRoute(route);
            if (!result.success())
            {
                return result;
            }
        }

        return new Result();
    }

    private void parseLines(ArrayList<String> lines)
    {
        RouteProperties routeProperties = new RouteProperties();
//        ArrayList<Route> listOfRoutes = new ArrayList<Route>();
        Route route = null;       //set route type, D1, D2, etc
//        routeProperties.setRouteType(getRouteType(prefix));
        int lineCount = 1;//used in error tracking
        for (String line : lines)
        {
            if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))//new Route
            {
                String[] chunks = line.replaceFirst(FileUtils.COMMAND_FILE_METADATA_PREFIX, "").split(" ");
                if (chunks.length == 3)
                {
                    if ((routeProperties = parseForMetadata(chunks)) != null)
                    {
                        route = new Route(routeProperties);
                        rh.addRoute(route);
                    }
                    else
                    {
                        System.err.println("Format of the command of line " + lineCount + " wrong. The line: \"" + line + "\"");
                    }
                }
                else if (chunks.length == 7)//clone command
                {
                    System.out.println("Line " + line + " is a clone command.");
                    
                    Route routeToClone = null;
                    if ((routeToClone = rh.getRoute(CabinetType.valueOf(chunks[0]), CabinetType.valueOf(chunks[1]), RouteEffectType.valueOf(chunks[2]))) != null)
                    {
                        RouteProperties cloneProps = new RouteProperties(CabinetType.valueOf(chunks[4]), CabinetType.valueOf(chunks[5]), RouteEffectType.valueOf(chunks[6]));
                        Route clone = new Route(cloneProps);
                        if (chunks[3].equals(ROUTE_CLONE_FWD))
                        {
                            System.out.println("From " + chunks[0] + " to " + chunks[1] + " effect " + chunks[2]);
                            for (int i = 0; i < routeToClone.size(); i++)
                            {
                                clone.add(routeToClone.get(i));
                            }
                        }
                        else if (chunks[3].equals(ROUTE_CLONE_REV))
                        {
                            for (int i = routeToClone.size() - 1; i >= 0; i--)
                            {
                                clone.add(routeToClone.get(i));
                            }
                        }
                        else
                        {
                            System.err.println("In chunks == 7, Format of the command of line " + lineCount + " wrong. The line: \"" + line + "\"");
                        }
                        rh.addRoute(clone);
                    }
                    else
                    {
                        System.err.println("Route not found.");
                    }
                }
                else
                {
                    System.err.println("Format of the metadata command of line " + lineCount + " wrong. The line: \"" + line + "\"");
                }

            }
            else if (route != null)//not defining a new command, so a cartesian position command
            {

                String[] pieces = line.split(" ");//splits the line to pieces
                if (pieces.length == 3)//x,y,z only
                {
                    String pitch = "0", yaw = "0", roll = "0";

                    //grab the pitch,yaw,roll from the last cartesian command
                    if (route.getLastObject() != null)
                    {
                        CommandPosition orcc = route.getLastObject();
                        pitch = String.valueOf(orcc.getPosition().getPitch());
                        yaw = String.valueOf(orcc.getPosition().getYaw());
                        roll = String.valueOf(orcc.getPosition().getRoll());
                    }
                    pieces[0] = Utils.xyInToMmStr(pieces[0]);
                    pieces[1] = Utils.xyInToMmStr(pieces[1]);
                    pieces[2] = Utils.zInToMmStr(pieces[2]);
                    route.add(new CommandPosition(new Position(null, pieces[0], pieces[1], pieces[2], pitch, yaw, roll), routeProperties.getRouteFriendlyName(), route.size() + 1));
                }
                else if (pieces.length == 6)//x,y,z,pitch,yaw,roll
                {
                    String pitch = pieces[3];
                    String yaw = pieces[4];
                    String roll = pieces[5];

                    for (KVPair porint : PITCH_ORIENTATION)
                    {
                        if (pitch.equals(porint.key))
                        {
                            pitch = porint.value;
                        }
                    }
                    for (KVPair yorint : YAW_ORIENTATION)
                    {
                        if (yaw.equals(yorint.key))
                        {
                            yaw = yorint.value;
                        }
                    }
                    for (KVPair worint : ROLL_ORIENTATION)
                    {
                        if (roll.equals(worint.key))
                        {
                            roll = worint.value;
                        }
                    }
                    pieces[0] = Utils.xyInToMmStr(pieces[0]);
                    pieces[1] = Utils.xyInToMmStr(pieces[1]);
                    route.add(new CommandPosition(new Position(null, pieces[0], pieces[1], pieces[2], pitch, yaw, roll), routeProperties.getRouteFriendlyName(), route.size() + 1));
                    pieces[2] = Utils.zInToMmStr(pieces[2]);
                }
                else//error in format of info
                {
                    System.err.println("Format of route line " + lineCount + " wrong. The line: \"" + line + "\"");
                    //ignore line
                }
            }
            lineCount++;
        }
    }

    /**
     * Takes in the array of Metadata pieces and parses out info.
     *
     * @param array of pieces
     * @return properties found.
     */
    private RouteProperties parseForMetadata(String[] array)
    {
        RouteProperties props = new RouteProperties();
        if (array.length < 3)
        {
            System.err.println("Not enough metadata for this route");
            return null;
        }
        else if (array.length > 3)
        {
            System.err.println("Too much metadata for this route");
            return null;
        }
        //exactly 3 pieces, from "X" to "Y" with "Z" effect

        String from = array[0];
        String to = array[1];
        String effect = array[2];

        props.setFrom(getCabinetType(from));
        props.setTo(getCabinetType(to));
        props.setEffect(getRouteEffectType(effect));

        return props;
    }

    /**
     * Converts a string to a RouteEffectType object
     *
     * @param s String to convert
     * @return Converted String value
     */
    private RouteEffectType getRouteEffectType(String s)
    {
        try
        {
            return RouteEffectType.valueOf(s.trim().toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    /**
     * Converts the given string to a CabinetType
     *
     * @param s String to convert
     * @return Converted String value
     */
    private CabinetType getCabinetType(String s)
    {
        try
        {
            return CabinetType.valueOf(s.trim().toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return CabinetType.UNKNOWN;
        }
    }

    private RouteType getRouteType(String prefix)
    {
        try
        {
            return RouteType.valueOf(prefix.trim().toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
}
