/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package route;

import enums.EnumRouteInfo;

/**
 *
 * @author kyle
 */
public class ObjectRoute
{
    String name;
    EnumRouteInfo routeInfo;

    public ObjectRoute(String name, EnumRouteInfo routeInfo)
    {
        this.name = name;
        this.routeInfo = routeInfo;
    }      
}
