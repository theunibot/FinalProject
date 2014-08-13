/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import enums.CabinetType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class RouteHolder
{

    private static RouteHolder rh = null;

    private List<Route> routes = Collections.synchronizedList(new ArrayList<Route>());

    public static RouteHolder getInstance()
    {
        if (rh == null)
        {
            rh = new RouteHolder();
        }
        return rh;
    }

    private RouteHolder()
    {
    }

    public void addRoute(Route r)
    {
        synchronized (routes)
        {
            routes.add(r);
        }
    }
    
    public Route getRoute(CabinetType fromCabinet, CabinetType toCabinet, String effect) {
        return null;
    }

    public List<Route> getRoutes(RouteProperties rp)
    {
        List<Route> routesToGive = new ArrayList<>();
        for (Route r : routes)
        {
            RouteProperties props = r.getRouteProperties();
            if(props.getEffect() == rp.getEffect()
                    && props.getFrom() == rp.getFrom()
                    && props.getTo() == rp.getTo())
            {
                routesToGive.add(r);
            }
        }
        return routesToGive;
    }

    /**
     * Gets a random Route matching the specified params. Error thrown if command not found.
     * @param from
     * @param to
     * @param effect
     * @return 
     */
    public Route getRoute(CabinetType from, CabinetType to, RouteEffectType effect)            
    {
        List<Route> routesToGive = new ArrayList<>();
        for (Route r : routes)
        {
            RouteProperties props = r.getRouteProperties();
            if(props.getEffect() == effect
                    && props.getFrom() == from
                    && props.getTo() == to)
            {
                routesToGive.add(r);
            }
        }
        if(routesToGive.size() > 0)
        {
            return routesToGive.get(Utils.getRandInt(routesToGive.size()));
        }
        
        System.err.println("ERROR, Route from " + from + " to " + to + " with effect " + effect + " not found!");
        return null;
    }
}
