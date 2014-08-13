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
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package robotoperations;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Result;

/**
 *
 */
public class R12Interface
{

    private final boolean USE_CONNECT_TIMEOUT = true;
    private final int INITIAL_CONNECT_TIMEOUT = 5000;//milis
    
    private Socket socket = null;
    private InputStream inputStream = null;
    private BufferedReader inFromServer = null;
    private DataOutputStream outToServer = null;

    private static R12Interface r12Interface = null;

    private volatile boolean isRunning = true;

    /**
     * Constructor made private, since this is a singleton interface
     */
    private R12Interface() {    
    }
    
    /**
     * Init the R12 interface. Sets up the socket, return success or failure.
     * Pulls its values from an INI file. If one does not exist, one is
     * generated automatically.
     *
     * @return boolean - true success, false failure
     */
    public Result init(String address, int port)
    {
        
        System.out.println("Opening Socket...");

        try
        {
            socket = new Socket();
            if (USE_CONNECT_TIMEOUT)
            {
                socket.connect(new InetSocketAddress(InetAddress.getByName(address), port), INITIAL_CONNECT_TIMEOUT);
            }
            else
            {
                socket.connect(new InetSocketAddress(InetAddress.getByName(address), port));
            }

            inputStream = socket.getInputStream();
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            outToServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("Socket Opened Succesfully");
        }
        catch (IOException ex)
        {
            Logger.getLogger(R12Interface.class.getName()).log(Level.SEVERE, null, ex);
            return new Result("Setup of socket to address " + address + " on port " + port + " failed.");
        }

        return new Result();
    }

    /**
     * Stops the interface gracefully
     */
    public void kill()
    {
        this.isRunning = false;
    }

    public static R12Interface getInstance()
    {
        if (r12Interface == null)
        {
            r12Interface = new R12Interface();
        }
        return r12Interface;
    }

    

    /**
     * Reads the info sent back from the TCP connection. <b>This IS
     * blocking.</b>
     *
     * @return <b>If successful:</b> the response String<br/>
     * <b>If failure:</b> null
     */
    public int read(byte[] buffer, int offset)
    {
        int readVal = -2;
        if (inputStream != null)
        {
            try
            {
//                System.out.println(offset);
                readVal = inputStream.read(buffer, offset, buffer.length - offset);
            }
            catch (IOException ex)
            {
                Logger.getLogger(R12Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return readVal;
    }

    /**
     * Writes out the TCP connection. <b>This is NOT blocking.</b>
     *
     * @param s String to write out the TCP connection.
     * @return boolean - if write was successful
     */
    public boolean write(String s)
    {

        try
        {
            outToServer.writeBytes(s);
//            outToServer.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(R12Interface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Breaks down the connection and quits, properly closing all objects.
     */
    public void quit()
    {
        try
        {
            inFromServer.close();
            outToServer.close();
            socket.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(R12Interface.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Quit Function failed");
        }
    }
}
