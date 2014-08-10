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
public class CommandDefine implements CommandInterface
{

    private String routeName;
    

    public CommandDefine(String routeName)
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
    public CommandType getObjectType()
    {
        return CommandType.DEFINE;
    }

}
