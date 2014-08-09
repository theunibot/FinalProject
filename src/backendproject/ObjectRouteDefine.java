/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendproject;

/**
 *
 * @author kyle
 */
public class ObjectRouteDefine implements ObjectRouteInterface
{

    private String routeName;
    

    public ObjectRouteDefine(String routeName)
    {
        this.routeName = routeName.trim();    
    }

    @Override
    public String toString()
    {
        return "ROUTE " + this.routeName + "\r";
    }

    @Override
    public boolean isRouteDefine()
    {
        return true;
    }

    @Override
    public ObjectRouteType getObjectType()
    {
        return ObjectRouteType.DEFINE;
    }

}
