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
package robotoperations;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import utils.Result;

/**
 *
 * @author Chris
 */
public class R12InterfaceIP implements R12Interface {
    private Socket socket = null;
    private InputStream inputStream = null;
    private BufferedReader inFromServer = null;
    private DataOutputStream outToServer = null;

    /**
     * Initialize communications channel.  Can start background thread if needed.  Passed a map
     * of keys/value pairs from the configuration INI file.
     * 
     * @return Result object indicating success/failure 
     */
    public Result init(Map<String, String> parameters) {
        String address = parameters.get("address");
        if (address == null)
            return new Result("Missing IP 'address' of R12 serial interface in INI file");
       
        String portStr = parameters.get("port");
        if (portStr == null)
            return new Result("Missing IP 'port' of R12 serial interface in INI file");
        int port = Integer.parseInt(portStr);

        System.out.println("Opening " + address + " port " + port);

        try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getByName(address), port), 2000);

                inputStream = socket.getInputStream();
                inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                outToServer = new DataOutputStream(socket.getOutputStream());
                System.out.println("Socket Opened Succesfully");
        } catch (IOException ex) {
                return new Result("Unable to communicate with " + address + " port " + port);
        }

        return new Result();    
    }
    
    /**
     * De-initialize communications channel.  Shuts down background thread(s) if needed.  Must be thread safe
     * so this can be called from a different thread that the communications thread in order to initiate a
     * successful shutdown (and unblock read/write as needed)
     */
    public void deinit() {
        try {
            inFromServer.close();
            outToServer.close();
            socket.close();
        } catch (IOException ex) {
            System.err.println("Quit Function failed: " + ex.getMessage());
        }
    }
    
    /**
     * Read data from the communications channel.  Reads bytes until the specified delimiter byte
     * is located.  Blocking interface, but must unblock if deinit is called.
     * 
     * @param delimiter character to look for to terminate the reading action
     * @return String with all bytes
     */
    public String read(char delimiter) {
        StringBuilder buffer = new StringBuilder();
        
        if (inputStream == null) {
            System.err.println("Read called yet no inputStream is open");
            return null;
        }
        
        while (true) {
            try {
                // get the next byte from the stream
                int readValue = inputStream.read();
                if (readValue == -1) {
                    System.err.println("Read called and returned -1; unblocking and returning failure");
                    return null;
                }
                // add it to the string
                buffer.append((char) readValue);
                // are we done?
                if ((char) readValue == delimiter)
                    break;
            } catch (IOException ex) {
                System.err.println("Read failed with exception: " + ex.getMessage());
                return null;
            }
        }
        
        return buffer.toString();
    }
    
    /**
     * Write data to the communications channel.  Writes the specified string buffer to the communications
     * channel.  Blocks until the write is complete.
     * 
     * @param buffer Buffer to send to the channel
     * @return Result with success/fail info
     */
    public Result write(String buffer) {

        try {
            outToServer.writeBytes(buffer);
        } catch (IOException ex) {
            return new Result("Failed to write buffer due to exception: " + ex.getMessage());
        }
        return new Result();
    }
}
