/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import enums.RouteSide;
import enums.RouteType;

/**
 *
 * @author kyle
 */
public class RouteProperties
{

    private RouteCabinetPosition from = null;
    private RouteCabinetPosition to = null;
    
    
    private String routeName;
    private RouteType routeType = null;
    private RouteSide routeSide = null;

    public RouteProperties(String routeName)
    {
        this.routeName = routeName;
    }

    public RouteProperties(String routeName, RouteType routeType, RouteSide routeSide)
    {
        this.routeName = routeName;
        this.routeType = routeType;
        this.routeSide = routeSide;
    }
       
    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }    
    
    public RouteSide getRouteSide()
    {
        return routeSide;
    }

    public RouteType getRouteType()
    {
        return routeType;
    }

    public void setRouteSide(RouteSide routeSide)
    {
        this.routeSide = routeSide;
    }

    public void setRouteType(RouteType routeType)
    {
        this.routeType = routeType;
    }
}
