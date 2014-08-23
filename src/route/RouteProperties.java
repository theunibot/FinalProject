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
    private int uniqueId;
    private boolean reverse = false;
    private int routeSpeed;
    private int accelSpeed;

    public RouteProperties(CabinetType from, CabinetType to, RouteEffectType effect, int routeSpeed, int accelSpeed) {
        this.from = from;
        this.to = to;
        this.effect = effect;        
       // determine how many other routes exist with this name, to give it a unique number
        RouteHolder rh = RouteHolder.getInstance();
        this.uniqueId = rh.countSimilarRoutes(getRouteFriendlyShortName());        
        this.routeSpeed = routeSpeed;
        this.accelSpeed = accelSpeed;
    }

    public int getRouteSpeed()
    {
        return routeSpeed;
    }        

    public int getRouteAccel()
    {
        return accelSpeed;
    }        

    public String getRouteFriendlyName() {
        return getRouteFriendlyShortName() + "_" + (this.uniqueId + 1);
    }
    
    public String getRouteFriendlyShortName() {
        return from.toString() + "_" + to.toString() + "_" + effect.toString();
    }
    
    public String getRouteIDName() {
        return 'R' + utils.Utils.hash(getRouteFriendlyName(), 4);
    }

    public CabinetType getTo() {
        return to;
    }

    public CabinetType getFrom() {
        return from;
    }

    public RouteEffectType getEffect() {
        return effect;
    }

    public void setTo(CabinetType to) {
        this.to = to;
    }

    public void setFrom(CabinetType from) {
        this.from = from;
    }

    public void setEffect(RouteEffectType effect) {
        this.effect = effect;
    }
    
    public boolean getReverse() {
        return reverse;
    }
    
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

}
