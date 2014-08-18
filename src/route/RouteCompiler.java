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
    public static final String ROUTE_FILE_BASENAME = FileUtils.getFilesFolderString() + "Routes.txt";
//    pathToFile = FileUtils.getFilesFolderString() + prefix + ROUTE_FILE_BASENAME + ".txt";
    public static final String ROUTE_DEFINE_PREFIX = "route ";

    private final String ROUTE_CLONE_FWD = "F";
    private final String ROUTE_CLONE_REV = "R";

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
        rh.clearRoutes();
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(ROUTE_FILE_BASENAME, ROUTE_COMPILER_FILE_CONTENTS);
        System.out.println("Read " + lines.size() + " line(s) from route compiler file.");
        Result parseResult = parseLines(lines);
        if (!parseResult.success())
            return parseResult;

        return new Result();
    }
    
    /**
     * Programs all routes into the controller
     * 
     * @param name optional name of container to program (only routes on that container, or from_to, are programmed); null for all
     * @return Result with success/fail info
     */
    public Result programRoutes(String name) {
        rh = RouteHolder.getInstance();
        ao = ArmOperations.getInstance();

        Result result = init();
        if (!result.success())
            return result;
        
        for (Route route : rh.getAllRoutes()) {
            String fromCabinet = route.getRouteProperties().getFrom().toString();
            String toCabinet = route.getRouteProperties().getTo().toString();
            // is this the route we want to program?

            if ( (name == null) || 
                (name.equalsIgnoreCase(toCabinet)) ||
                (name.equalsIgnoreCase(fromCabinet)) ||
                (name.equalsIgnoreCase(fromCabinet + "_" + toCabinet)) ) {
                result = ao.learnRoute(route);
                if (!result.success())
                    return result;
            }
        }
        return new Result();
    }

    private Result parseLines(ArrayList<String> lines)
    {
        RouteProperties routeProperties = null;
        Route route = null;       //set route type, D1, D2, etc

        int lineCount = 1;//used in error tracking
        for (String line : lines)
        {
            if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))//new Route
            {
                String[] chunks = line.replaceFirst(FileUtils.COMMAND_FILE_METADATA_PREFIX, "").split(" ");
                if (chunks.length == 3)
                {
                    RouteProperties props = null;

                    String from = chunks[0];
                    String to = chunks[1];
                    String effect = chunks[2];
                    routeProperties = new RouteProperties(getCabinetType(from), getCabinetType(to), getRouteEffectType(effect));
                    route = new Route(routeProperties);
                    rh.addRoute(route);
                }
                else if (chunks.length == 7)//clone command
                {
                    Route routeToClone = null;
                    if ((routeToClone = rh.getRoute(CabinetType.valueOf(chunks[0]), CabinetType.valueOf(chunks[1]), RouteEffectType.valueOf(chunks[2]))) != null)
                    {
                        RouteProperties cloneProps = new RouteProperties(CabinetType.valueOf(chunks[4]), CabinetType.valueOf(chunks[5]), RouteEffectType.valueOf(chunks[6]));
                        Route clone = new Route(cloneProps);
                        if (chunks[3].equals(ROUTE_CLONE_FWD))
                        {
                            //System.out.println("From " + chunks[0] + " to " + chunks[1] + " effect " + chunks[2]);
                            for (int i = 0; i < routeToClone.size(); i++)
                                clone.add(new RoutePosition(routeToClone.get(i), clone.getRouteProperties().getRouteIDName()));
                        }
                        else if (chunks[3].equals(ROUTE_CLONE_REV))
                        {
                            for (int i = routeToClone.size() - 1; i >= 0; i--)
                                clone.add(new RoutePosition(routeToClone.get(i), clone.getRouteProperties().getRouteIDName(), routeToClone.size() - i));
                        }
                        else
                            return new Result("Format of the command of line " + lineCount + " wrong. The line: \"" + line + "\"");
                        rh.addRoute(clone);
                    }
                    else
                        return new Result("Route not found");
                }
                else
                    return new Result("Format of the metadata command of line " + lineCount + " wrong. The line: \"" + line + "\"");

            } else if (line.startsWith(FileUtils.COMMAND_FILE_REFERENCE_POINT)) {
                // this is a reference to a point
                String[] chunks = line.replaceFirst(FileUtils.COMMAND_FILE_REFERENCE_POINT, "").split(" ");
                if (chunks.length == 2) {
                    PositionLookup pl = PositionLookup.getInstance();
                    CabinetType cabinet = CabinetType.valueOf(chunks[0]);
                    if (cabinet == null)
                        return new Result("Unknown cabinet at line " + lineCount + ": " + line);
                    int shelf = Integer.valueOf(chunks[1]);
                    Position reference = pl.shelfToPosition(cabinet, shelf);
                    if (reference == null)
                        return new Result("Unable to locate cabinet/shelf at line " + lineCount + ": " + line);
                    // add the reference position
                    route.add(new RoutePosition(reference, route.getRouteProperties().getRouteIDName(), route.size() + 1));
                } else
                  return new Result("Format of line " + lineCount + " is wrong: " + line); 
            }
            else if (route != null)//not defining a new command, so a cartesian position command
            {

                String[] pieces = line.split(" ");//splits the line to pieces
                if (pieces.length == 3)//x,y,z only
                {
                    double pitch = 0.0, yaw = 0.0, roll = 0.0;

                    //grab the pitch,yaw,roll from the last cartesian command
                    if (route.getLastObject() != null)
                    {
                        RoutePosition orcc = route.getLastObject();
                        pitch = orcc.getPosition().getPitch();
                        yaw = orcc.getPosition().getYaw();
                        roll = orcc.getPosition().getRoll();
                    }
                    double x = Utils.inToMm(Double.valueOf(pieces[0]));
                    double y = Utils.inToMm(Double.valueOf(pieces[1]));
                    double z = Utils.zInToMm(Double.valueOf(pieces[2]));
                    route.add(new RoutePosition(new Position(null, x, y, z, pitch, yaw, roll), route.getRouteProperties().getRouteIDName(), route.size() + 1));
                }
                else if (pieces.length == 6)//x,y,z,pitch,yaw,roll
                {
                    double pitch = Double.valueOf(pieces[3]);
                    double yaw = Double.valueOf(pieces[4]);
                    double roll = Double.valueOf(pieces[5]);

                    double x = Utils.inToMm(Double.valueOf(pieces[0]));
                    double y = Utils.inToMm(Double.valueOf(pieces[1]));
                    double z = Utils.zInToMm(Double.valueOf(pieces[2]));
                    route.add(new RoutePosition(new Position(null, x, y, z, pitch, yaw, roll), route.getRouteProperties().getRouteIDName(), route.size() + 1));
                }
                else//error in format of info
                    return new Result("Format of the command of line " + lineCount + " wrong. The line: \"" + line + "\"");
            }
            else
                return new Result("Route definition failed");
            lineCount++;
        }
        return new Result();
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
