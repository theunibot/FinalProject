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
    private Double x;
    private Double y;
    private Double z;
    private Double pitch;
    private Double yaw;
    private Double roll;
    private String name = null;

    public Position(String name) {
        this.x = 0.;
        this.y = 0.;
        this.z = 0.;
        this.pitch = 0.;
        this.yaw = 0.;
        this.roll = 0.;
        this.name = name;
    }

    public Position(String name, String x, String y, String z, String pitch, String yaw, String roll)
    {
        this.x = Double.valueOf(x);
        this.y = Double.valueOf(y);
        this.z = Double.valueOf(z);
        this.pitch = Double.valueOf(pitch);
        this.yaw = Double.valueOf(yaw);
        this.roll = Double.valueOf(roll);
        this.name = name;
    }   
    
    public Position(String name, Double x, Double y, Double z, Double pitch, Double yaw, Double roll)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.name = name;
    }   
    
    public Position(String name, Position sourcePosition) {
        this.x = sourcePosition.x;
        this.y = sourcePosition.y;
        this.z = sourcePosition.z;
        this.pitch = sourcePosition.pitch;
        this.yaw = sourcePosition.yaw;
        this.roll = sourcePosition.roll;
        this.name = name;
    }

    public boolean equals(Object other) {
        if (other == null)
            return false;
        
        if (this.getClass() != other.getClass())
            return false;
        
        Position op = (Position) other;
        if ( (this.x != op.x) ||
            (this.y != op.y) ||
            (this.z != op.z) ||
            (this.pitch != op.pitch) ||
            (this.yaw != op.yaw) ||
            (this.roll != op.roll) ||
            (this.name.equals(op.name)) )
            return false;
        
        return true;
    }
    
    /**
     * Offset this position by the relative amounts contained in the provided offsetPosition
     * 
     * @param offsetPosition relative offsets to adjust by
     * @return This object, now adjusted
     */
    public Position offsetPositions(Position offsetPosition) {
        this.x += offsetPosition.getX();
        this.y += offsetPosition.getY();
        this.z += offsetPosition.getZ();
        this.pitch += offsetPosition.getPitch();
        this.yaw += offsetPosition.getYaw();
        this.roll += offsetPosition.getRoll();

        return this;
    }
    
    public String getRoboforth()
    {
        return getXStr() + " X ! " + getYStr() + " Y ! " + getZStr() + " Z ! " + getPitchStr() + " PITCH ! " + getYawStr() + " YAW ! " + getRollStr() + " ROLL ! POINT " + getName();
    }
    
    public Double getX() {
        return x;
    }

    public String getXStr()
    {
        return Utils.formatDouble(x);
    }

    public void setX(String x) {
        this.x = Double.valueOf(x);
    }
    
    public void setX(Double x) {
        this.x = x;
    }
    
    public Double getY() {
        return y;
    }
    
    public String getYStr()
    {
        return Utils.formatDouble(y);
    }
    
    public void setY(String y) {
        this.y = Double.valueOf(y);
    }
    
    public void setY(Double y) {
        this.y = y;
    }
    
    public Double getZ() {
        return z;
    }

    public String getZStr()
    {
        return Utils.formatDouble(z);
    }

    public void setZ(String z) {
        this.z = Double.valueOf(z);
    }
    
    public void setZ(Double z) {
        this.z = z;
    }
    
    public Double getPitch() {
        return pitch;
    }
    
    public String getPitchStr()
    {
        return Utils.formatDouble(pitch);
    }
    
    public void setPitch(String pitch) {
        this.pitch = Double.valueOf(pitch);
    }
    
    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }
    
    public Double getYaw() {
        return yaw;
    }

    public String getYawStr()
    {
        return Utils.formatDouble(yaw);
    }
    
    public void setYaw(String yaw) {
        this.yaw = Double.valueOf(yaw);
    }
    
    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }
    
    public Double getRoll() {
        return roll;
    }

    public String getRollStr()
    {
        return Utils.formatDouble(roll);
    }
    
    public void setRoll(String roll) {
        this.roll = Double.valueOf(roll);
    }
    
    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public String getName()
    {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name + " (" + getXStr() + ", " + getYStr() + ", " + getZStr() + "; " + getPitchStr() + ", " + getYawStr() + ", " + getRollStr() + ")";
    }
    
}
