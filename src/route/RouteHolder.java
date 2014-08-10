/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Route> getRoutes(RouteProperties rp)
    {
        List<Route> routesToGive = new ArrayList<>();
        for (Route r : routes)
        {
            if (r.getRouteProperties().getRouteSide() == rp.getRouteSide()
                    && r.getRouteProperties().getRouteType() == rp.getRouteType())
            {
                routesToGive.add(r);
            }
        }
        return routesToGive;
    }
}
