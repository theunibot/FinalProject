/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package route;

/**
 * Define a cartesian coordinate
 * 
 */
public class Cartesian {
    private int x;
    private int y;
    private int z;
    private int pitch;
    private int yaw;
    private int roll;
    private String pitchStr;
    private String yawStr;
    private String rollStr;

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
    
    public String getRoboforth(){
        String robo = getX() + " X ! " + getY() + " Y ! " + getZ() + " Z ! " + getPitch() + " PITCH ! " + getYaw() + " YAW ! " + getRoll() + " ROLL !";
        return robo;
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
}
