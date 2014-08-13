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

/**
 *
 */
public class RouteProperties
{

    private CabinetType from = null;
    private CabinetType to = null;    
    private RouteEffectType effect = null;
    
    private String routeName;

    public RouteProperties(String routeName)
    {
        this.routeName = routeName;
    }

    public RouteProperties(String routeName, CabinetType from, CabinetType to)
    {
        this.routeName = routeName;        
        this.from = from;
        this.to = to;
    }
       
    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
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
