/*
    This file is part of theunibot.

    theunibot is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    theunibot is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package route;

import utils.Utils;

/**
 * Define a cartesian coordinate
 *
 */
public class Cartesian
{

    private int x;
    private int y;
    private int z;
    private int pitch;
    private int yaw;
    private int roll;
    private String pitchStr;
    private String yawStr;
    private String rollStr;
    private String name = null;

    public Cartesian(String x, String y, String z, String pitch, String yaw, String roll)
    {
        //turn 300.0 to 3000
        x = x.replace(".", "");
        y = y.replace(".", "");
        z = z.replace(".", "");
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.z = Integer.parseInt(z);
        this.pitchStr = pitch;
        this.yawStr = yaw;
        this.rollStr = roll;        
    }   

    public String getRoboforth()
    {
        return getX() + " X ! " + getY() + " Y ! " + getZ() + " Z ! " + getPitch() + " PITCH ! " + getYaw() + " YAW ! " + getRoll() + " ROLL ! POINT " + getName();
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public int getPitch()
    {
        return pitch;
    }

    public int getYaw()
    {
        return yaw;
    }

    public int getRoll()
    {
        return roll;
    }

    public String getPitchStr()
    {
        return pitchStr;
    }

    public String getYawStr()
    {
        return yawStr;
    }

    public String getRollStr()
    {
        return rollStr;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    
    
}
