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
public class PositionLookupTable
{
    private static PositionLookupTable plt = null;
    
    public static PositionLookupTable getInstance()
    {
        if(plt == null)plt = new PositionLookupTable();
        return plt;
    }

    private PositionLookupTable()
    {
    }
    
    
}