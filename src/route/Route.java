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
import java.util.List;

/**
 *
 */
public class Route
{

    ArrayList<RoutePosition> commands = new ArrayList<>();

    private RouteProperties routeProperties = null;

    public Route(RouteProperties routeProperties)
    {
        setRouteProperties(routeProperties);
    }

    public void setRouteProperties(RouteProperties rp)
    {
        this.routeProperties = rp;
    }

    public int size()
    {
        return commands.size();
    }

    public void add(RoutePosition o)
    {
        commands.add(o);
    }

    public void set(int index, RoutePosition cp)
    {
        if (index >= 0 && index < this.size())
        {
            commands.set(index, cp);
        }
    }

    public RoutePosition get(int index)
    {
        if (index >= 0 && index < this.size())
        {
            return commands.get(index);
        }
        else
        {
            return null;
        }
    }

    public RoutePosition getLastObject()
    {
        if (commands.size() > 0)
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
        commandStrings.add("ROUTE " + this.routeProperties.getRouteIDName());
        commandStrings.add("ERASE");
        commandStrings.add(commands.size() + " RESERVE");
        for (RoutePosition o : commands)
        {
            commandStrings.add("LEARN");
            commandStrings.add(o.toString());
        }
        return commandStrings;
    }

}

