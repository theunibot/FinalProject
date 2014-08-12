/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import java.util.ArrayList;

/**
 *
 * @author kyle
 */
public class Route
{

    ArrayList<CommandCartesian> commands = new ArrayList<>();

    private RouteProperties routeProperties = new RouteProperties(null);

    public Route(RouteProperties routeProperties)
    {
        setRouteProperties(routeProperties);
    }
    
    public void setRouteProperties(RouteProperties routeProperties)
    {
        this.routeProperties.setEffect(routeProperties.getEffect());
        this.routeProperties.setFrom(routeProperties.getFrom());
        this.routeProperties.setTo(routeProperties.getTo());
        this.routeProperties.setRouteName(routeProperties.getRouteName());
    }

    public int size()
    {
        return commands.size();
    }

    public void add(CommandCartesian o)
    {
        commands.add(o);
    }

    public CommandCartesian getLastObject()
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
        for (CommandCartesian o : commands)
        {
            commandStrings.add("LEARN");
            commandStrings.add(o.toString());
        }
        return commandStrings;
    }

}
