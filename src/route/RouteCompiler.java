/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import route.Route;
import robotoperations.ResponseObject;
import robotoperations.R12Operations;
import utils.FileUtils;
import route.CommandType;
import route.CommandCartesian;
import java.util.ArrayList;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class RouteCompiler
{

    private static RouteCompiler routeCompiler = null;
    private R12Operations r12o = R12Operations.getInstance();   
    private RouteHolder rh = RouteHolder.getInstance();

    //String consts
    public static final String ROUTE_PREFIX = "route ";
    public static final String ROUTE_FILE_BASENAME = "routes";
    public static final String ROUTE_LEFT = "left";
    public static final String ROUTE_RIGHT = "right";

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
            + "//To Be Implemented: To declare a route to be avalible for use the syntax \"include: <name of route>\" to ensure\n"
            + "//the route is added to a usable list for the program"
            + "\n"
            + "\n" + ROUTE_LEFT + ""
            + "\n"
            + "\n" + ROUTE_RIGHT;
    private String pathToFile = "";

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
     * @return success
     */
    public boolean init()
    {
        boolean success = true;
        for (String prefix : ROUTE_FILE_PREFIXS)
        {
            pathToFile = FileUtils.getFilesFolderString() + prefix + ROUTE_FILE_BASENAME + ".txt";
            ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(pathToFile, ROUTE_COMPILER_FILE_CONTENTS);
            System.out.println("Read " + lines.size() + " line(s) from route compiler file.");
            ArrayList<Route> routes = parseLines(lines, prefix);

            for (Route route : routes)
            {
                //adds routes to the route holder
                rh.addRoute(route);
                
                //gets and executes each command in the route
                ArrayList<String> commands = route.getRoboforthCommands();
                for (String commandString : commands)
                {
                    System.out.println(commandString);
                    if (!runCommand(commandString))//if command not successul
                    {
                        success = false;
                    }
                }
            }
        }
        return success;
    }

    private boolean runCommand(String commandString)
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

    private ArrayList<Route> parseLines(ArrayList<String> lines, String prefix)
    {
        RouteProperties routeProperties = new RouteProperties(null);
        ArrayList<Route> listOfRoutes = new ArrayList<Route>();
        Route route = null;        //set route type, D1, D2, etc
        routeProperties.setRouteType(getRouteType(prefix));
        int lineCount = 1;//used in error tracking
        for (String line : lines)
        {
            if (line.startsWith("#"))
            {
                String[] chunks = line.split(" ");
                for (String chunk : chunks)
                {
                    if (chunk.equals(ROUTE_LEFT))
                    {
                        routeProperties.setRouteSide(RouteSide.LEFT);
                    }
                    else if (chunk.equals(ROUTE_RIGHT))
                    {
                        routeProperties.setRouteSide(RouteSide.RIGHT);
                    }
                }
            }
            else if (line.startsWith(ROUTE_PREFIX))//new Route
            {
                String routeName = prefix + line.replace(ROUTE_PREFIX, "").trim();
                routeProperties.setRouteName(routeName);//sets the name of the route for use
                route = new Route(routeProperties);
                listOfRoutes.add(route);
            }
            else if (route != null)//not defining a new command, so a cartesian command
            {

                String[] pieces = line.split(" ");//splits the line to pieces
                if (pieces.length == 3)//x,y,z only
                {
                    String pitch = "0", yaw = "0", roll = "0";

                    //grab the pitch,yaw,roll from the last cartesian command
                    if (route.getFirstObject() != null && route.getFirstObject().getObjectType() == CommandType.CARTESIAN)
                    {
                        CommandCartesian orcc = (CommandCartesian) route.getFirstObject();
                        pitch = String.valueOf(orcc.getPitch());
                        yaw = String.valueOf(orcc.getYaw());
                        roll = String.valueOf(orcc.getRoll());
                    }
                    pieces[0] = Utils.xyInToMmStr(pieces[0]);
                    pieces[1] = Utils.xyInToMmStr(pieces[1]);
                    pieces[2] = Utils.xyInToMmStr(pieces[2]);
                    route.add(new CommandCartesian(pieces[0], pieces[1], pieces[2], pitch, yaw, roll, routeProperties.getRouteName(), route.size() + 1));
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
                    pieces[2] = Utils.xyInToMmStr(pieces[2]);
                    route.add(new CommandCartesian(pieces[0], pieces[1], pieces[2], pitch, yaw, roll, routeProperties.getRouteName(), route.size() + 1));
                }
                else//error in format of info
                {
                    System.err.println("Format of line " + lineCount + " wrong. The line: " + line);
                    //ignore line
                }
            }
            lineCount++;
        }

        return listOfRoutes;
    }

    private RouteType getRouteType(String prefix)
    {
        if (prefix.equals(ROUTE_FILE_PREFIXS[0]))//D1
        {
            return RouteType.D1;
        }
        else if (prefix.equals(ROUTE_FILE_PREFIXS[1]))//D2
        {
            return RouteType.D2;
        }
        else if (prefix.equals(ROUTE_FILE_PREFIXS[2]))//S
        {
            return RouteType.S;
        }
        else
        {
            return null;
        }
    }
}
