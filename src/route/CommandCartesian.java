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
public class CommandCartesian 
{
    private Cartesian coord;
    private String routeName;
    private int line;

    public CommandCartesian(Cartesian coord, String routeName, int line)
    {
        this.coord = coord;
        this.routeName = routeName;
        this.line = line;     
    }
    
    public Cartesian getCartesian() {
        return this.coord;
    }
    
    public boolean isRouteDefine()
    {
        return false;
    }
    
    public String toString()
    {        
        return "DECIMAL " + coord.getRollStr() + " " + coord.getYawStr() + " " + coord.getPitchStr() + " " + coord.getZ() + " " + coord.getY() + " " + coord.getX() + " " + routeName + " " + line + " LINE DLD\r";
    }
}
