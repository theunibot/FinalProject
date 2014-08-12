/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

import enums.CabinetType;

/**
 *
 * @author kyle
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
