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
public class CommandCartesian implements CommandInterface
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
    
    @Override
    public boolean isRouteDefine()
    {
        return false;
    }
    
    @Override
    public String toString()
    {        
        return "LEARN\r"
                + "DECIMAL " + coord.getRollStr() + " " + coord.getYawStr() + " " + coord.getPitchStr() + " " + coord.getZ() + " " + coord.getY() + " " + coord.getX() + " " + routeName + " " + line + " DLD\r";
    }

    @Override
    public CommandType getObjectType()
    {
        return CommandType.CARTESIAN;
    }

}
