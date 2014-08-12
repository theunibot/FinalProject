/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package route;

/**
 *
 * @author kyle
 */
public class RouteProperties
{

    private RouteCabinetPosition from = null;
    private RouteCabinetPosition to = null;    
    private RouteEffectType effect = null;
    
    private String routeName;

    public RouteProperties(String routeName)
    {
        this.routeName = routeName;
    }

    public RouteProperties(String routeName, RouteCabinetPosition from, RouteCabinetPosition to)
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

    public RouteCabinetPosition getTo()
    {
        return to;
    }

    public RouteCabinetPosition getFrom()
    {
        return from;
    }

    public RouteEffectType getEffect()
    {
        return effect;
    }

    public void setTo(RouteCabinetPosition to)
    {
        this.to = to;
    }

    public void setFrom(RouteCabinetPosition from)
    {
        this.from = from;
    }

    public void setEffect(RouteEffectType effect)
    {
        this.effect = effect;
    }
    
    
}
