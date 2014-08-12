/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import enums.RouteType;
import robotoperations.ArmOperations;
import utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import utils.Utils;
import utils.Result;

/**
 *
 * @author kyle
 */
public class RouteCompiler
{

    private static RouteCompiler routeCompiler = null;
    private RouteHolder rh = RouteHolder.getInstance();

    //String consts
    public static final String ROUTE_FILE_BASENAME = FileUtils.getFilesFolderString() + "allRoutes.txt";
//    pathToFile = FileUtils.getFilesFolderString() + prefix + ROUTE_FILE_BASENAME + ".txt";
    public static final String ROUTE_DEFINE_PREFIX = "route ";
    public static final String ROUTE_LEFT = "left";
    public static final String ROUTE_MIDDLE = "middle";
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
            + "//To Be Implemented: To declare a route to be available for use the syntax \"include: <name of route>\" to ensure\n"
            + "//the route is added to a usable list for the program"
            + "\n"
            + "\n" + ROUTE_LEFT + ""
            + "\n"
            + "\n" + ROUTE_MIDDLE + ""
            + "\n"
            + "\n" + ROUTE_RIGHT;

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
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(ROUTE_FILE_BASENAME, ROUTE_COMPILER_FILE_CONTENTS);
        System.out.println("Read " + lines.size() + " line(s) from route compiler file.");
        ArrayList<Route> routes = parseLines(lines);

        for (Route route : routes)
        {
            //adds routes to the route holder
            rh.addRoute(route);

            ArmOperations ao = ArmOperations.getInstance();
            Result result = ao.learnRoute(route);
            if (!result.success())
                return result;
        }

        return new Result();
    }

    private ArrayList<Route> parseLines(ArrayList<String> lines)
    {
        RouteProperties routeProperties = new RouteProperties(null);
        ArrayList<Route> listOfRoutes = new ArrayList<Route>();
        Route route = null;        //set route type, D1, D2, etc
//        routeProperties.setRouteType(getRouteType(prefix));
        int lineCount = 1;//used in error tracking
        for (String line : lines)
        {
            if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))//new Route
            {
                String[] chunks = line.replaceFirst(FileUtils.COMMAND_FILE_METADATA_PREFIX, "").split(" ");
                routeProperties = parseForMetadata(chunks);
                routeProperties.setRouteName(nameRoute(routeProperties));
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
                    if (route.getLastObject() != null)
                    {
                        CommandCartesian orcc = route.getLastObject();
                        pitch = String.valueOf(orcc.getCartesian().getPitch());
                        yaw = String.valueOf(orcc.getCartesian().getYaw());
                        roll = String.valueOf(orcc.getCartesian().getRoll());
                    }
                    pieces[0] = Utils.xyInToMmStr(pieces[0]);
                    pieces[1] = Utils.xyInToMmStr(pieces[1]);
                    pieces[2] = Utils.xyInToMmStr(pieces[2]);
                    route.add(new CommandCartesian(new Cartesian(pieces[0], pieces[1], pieces[2], pitch, yaw, roll), routeProperties.getRouteName(), route.size() + 1));
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
                    route.add(new CommandCartesian(new Cartesian(pieces[0], pieces[1], pieces[2], pitch, yaw, roll), routeProperties.getRouteName(), route.size() + 1));
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

    private String nameRoute(RouteProperties rp)
    {
        String to = "", from = "", effect = "";
        to = getRouteCabinetPositionString(rp.getTo());
        from = getRouteCabinetPositionString(rp.getFrom());
        effect = getRouteEffectTypeString(rp.getEffect());
        List<Route> sameRoutes = rh.getRoutes(rp);
        return from + "_" + to + "_" + effect + ((int)(sameRoutes.size() + 1));

    }

    /**
     * Takes in the array of Metadata pieces and parses out info.
     *
     * @param array of pieces
     * @return properties found.
     */
    private RouteProperties parseForMetadata(String[] array)
    {
        RouteProperties props = new RouteProperties("");
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

        props.setFrom(getRouteCabinetPosition(from));
        props.setTo(getRouteCabinetPosition(to));
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
        if (s.equalsIgnoreCase("efficient"))
        {
            return RouteEffectType.EFFICIENT;
        }
        else if (s.equalsIgnoreCase("fancy"))
        {
            return RouteEffectType.FANCY;
        }
        else if (s.equalsIgnoreCase("pick"))
        {
            return RouteEffectType.PICK;
        }
        else if (s.equalsIgnoreCase("place"))
        {
            return RouteEffectType.PLACE;
        }
        else
        {
            return null;
        }
    }

    private String getRouteEffectTypeString(RouteEffectType ret)
    {
        if (ret == RouteEffectType.EFFICIENT)
        {
            return "EFFICIENT";
        }
        else if (ret == RouteEffectType.FANCY)
        {
            return "FANCY";
        }
        else if (ret == RouteEffectType.PICK)
        {
            return "PICK";
        }
        else if (ret == RouteEffectType.PLACE)
        {
            return "PLACE";
        }
        else
        {
            return null;
        }
    }

    /**
     * Converts the given string to a RouteCabinetPosition
     *
     * @param s String to convert
     * @return Converted String value
     */
    private RouteCabinetPosition getRouteCabinetPosition(String s)
    {
        if (s.equalsIgnoreCase("d1"))
        {
            return RouteCabinetPosition.D1;
        }
        else if (s.equalsIgnoreCase("d2"))
        {
            return RouteCabinetPosition.D2;
        }
        else if (s.equalsIgnoreCase("home"))
        {
            return RouteCabinetPosition.HOME;
        }
        else if (s.equalsIgnoreCase("cpl"))
        {
            return RouteCabinetPosition.CPL;
        }
        else if (s.equalsIgnoreCase("cpr"))
        {
            return RouteCabinetPosition.CPR;
        }
        else if (s.equalsIgnoreCase("cpc"))
        {
            return RouteCabinetPosition.CPC;
        }
        else
        {
            return null;
        }
    }

    private String getRouteCabinetPositionString(RouteCabinetPosition rcp)
    {
        if (rcp == RouteCabinetPosition.CPC)
        {
            return "CPC";
        }
        else if (rcp == RouteCabinetPosition.CPL)
        {
            return "CPL";
        }
        else if (rcp == RouteCabinetPosition.CPR)
        {
            return "CPR";
        }
        else if (rcp == RouteCabinetPosition.D1)
        {
            return "D1";
        }
        else if (rcp == RouteCabinetPosition.D2)
        {
            return "D2";
        }
        else if (rcp == RouteCabinetPosition.HOME)
        {
            return "HOME";
        }
        else
        {
            return null;
        }
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
