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
public interface CommandInterface
{

    
    public String toString();
    
    public boolean isRouteDefine();
    
    public CommandType getObjectType();
    
}

