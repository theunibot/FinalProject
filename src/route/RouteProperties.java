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
import java.util.List;

/**
 *
 */
public class RouteProperties
{

    private CabinetType from = null;
    private CabinetType to = null;
    private RouteEffectType effect = null;

    private static RouteHolder rh = RouteHolder.getInstance();

    private String routeFriendlyName = null;
    private String routeIDName = null;
    private static int routeIDVar = 0;
    private int routeID = -1;
    

    public RouteProperties()
    {
    }

    public RouteProperties(CabinetType from, CabinetType to, RouteEffectType effect)
    {
        this.from = from;
        this.to = to;
        this.effect = effect;
        this.routeFriendlyName = genRouteFriendlyName();
        routeID = routeIDVar++;//gets the next id from the static var
        this.routeIDName = "R" + routeID;//gens a route ID
    }

    public String getRouteFriendlyName()
    {
        return routeFriendlyName;
    }
    
    public String getRouteIDName()
    {
        return routeIDName;
    }

    private String genRouteFriendlyName()
    {
        String to = "", from = "", effect = "";
        to = this.to.toString();
        from = this.from.toString();
        effect = this.effect.toString();
        List<Route> sameRoutes = rh.getRoutes(this);
        return from + "_" + to + "_" + effect + ((int) (sameRoutes.size() + 1));
    }

    public CabinetType getTo()
    {
        return to;
    }

    public CabinetType getFrom()
    {
        return from;
    }

    public RouteEffectType getEffect()
    {
        return effect;
    }

    public void setTo(CabinetType to)
    {
        this.to = to;
    }

    public void setFrom(CabinetType from)
    {
        this.from = from;
    }

    public void setEffect(RouteEffectType effect)
    {
        this.effect = effect;
    }

}
