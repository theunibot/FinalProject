/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import route.CommandInterface;
import java.util.ArrayList;

/**
 *
 * @author kyle
 */
public class Route
{

    ArrayList<CommandInterface> commands = new ArrayList<>();

    private RouteProperties routeProperties = new RouteProperties(null);

    public Route(RouteProperties routeProperties)
    {
        this.routeProperties.setRouteSide(routeProperties.getRouteSide());
        this.routeProperties.setRouteType(routeProperties.getRouteType());
        this.routeProperties.setRouteName(routeProperties.getRouteName());
    }

    public int size()
    {
        return commands.size();
    }

    public void add(CommandInterface o)
    {
        commands.add(o);
    }

    public CommandInterface getFirstObject()
    {
        if(commands.size() > 0)
        {
            return commands.get(commands.size() - 1);
        }
        return null;
    }

    public RouteProperties getRouteProperties()
    {
        return routeProperties;
    }

    public ArrayList<String> getRoboforthCommands()
    {
        ArrayList<String> commandStrings;
        commandStrings = new ArrayList<>();
        commandStrings.add("ROUTE " + this.routeProperties.getRouteName());
        commandStrings.add(commands.size() + " RESERVE");
        for (CommandInterface o : commands)
        {
            commandStrings.add(o.toString());
        }
        return commandStrings;
    }

}
