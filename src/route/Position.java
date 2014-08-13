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
 * Define a position in space as a cartesian coordinate
 *
 */
public class Position
{

    private String x;
    private String y;
    private String z;
    private String pitch;
    private String yaw;
    private String roll;
    private String name = null;

    public Position(String name, String x, String y, String z, String pitch, String yaw, String roll)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.name = name;
    }   

    public String getRoboforth()
    {
        return getX() + " X ! " + getY() + " Y ! " + getZ() + " Z ! " + getPitch() + " PITCH ! " + getYaw() + " YAW ! " + getRoll() + " ROLL ! POINT " + getName();
    }

    public String getX()
    {
        return x;
    }

    public String getY()
    {
        return y;
    }

    public String getZ()
    {
        return z;
    }

    public String getPitch()
    {
        return pitch;
    }

    public String getYaw()
    {
        return yaw;
    }

    public String getRoll()
    {
        return roll;
    }

    public String getName()
    {
        return name;
    }
    
    
}
