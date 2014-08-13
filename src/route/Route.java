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

import java.util.ArrayList;

/**
 *
 */
public class Route
{

    ArrayList<CommandPosition> commands = new ArrayList<>();

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

    public void add(CommandPosition o)
    {
        commands.add(o);
    }

    public CommandPosition getLastObject()
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
        for (CommandPosition o : commands)
        {
            commandStrings.add("LEARN");
            commandStrings.add(o.toString());
        }
        return commandStrings;
    }

}
