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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import utils.Result;

/**
 *
 * @author Chris
 */
public class R12InterfaceSerial implements R12Interface {
    private SerialPort serialPort;

    /**
     * Initialize communications channel.  Can start background thread if needed.  Passed a map
     * of keys/value pairs from the configuration INI file.
     * 
     * @return Result object indicating success/failure 
     */
    public Result init(Map<String, String> parameters) {
        // display possible port names, just to be helpful...
        System.out.println("\nSearching for ports...");
        String[] ports = SerialPortList.getPortNames();
        for (int index = 0; index < ports.length; ++index)
            System.out.println("Found port: " + ports[index]);
        System.out.println("Port search complete\n");
        
        String serialPortName = parameters.get("device");
        if (serialPortName == null)
            return new Result("Missing serial port device name in INI file under device=<name>");
        
        serialPort = new SerialPort(serialPortName);
        try {
            serialPort.openPort();
            serialPort.purgePort(SerialPort.PURGE_RXABORT + SerialPort.PURGE_RXCLEAR + SerialPort.PURGE_TXABORT + SerialPort.PURGE_TXCLEAR);
            serialPort.setParams(SerialPort.BAUDRATE_19200, 8, 1, 0);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (SerialPortException ex) {
            return new Result("Serial port startup failed: " + ex.getMessage());
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
            serialPort.closePort();
        } catch (SerialPortException ex) {
            System.err.println("Serial port close failed: " + ex.getMessage());
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
        
        while (true) {
            byte[] bytes;
            // get the next byte from the stream
            try {
                bytes = serialPort.readBytes(1);
            } catch (SerialPortException ex) {
                System.err.println("Serial port read failed: " + ex.getMessage());
                return null;
            }
            // add it to the string
            buffer.append((char) bytes[0]);
            // are we done?
            if ((char) bytes[0] == delimiter)
                break;
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
            serialPort.writeString(buffer);
        } catch (SerialPortException ex) {
            return new Result("Serial port write failed: " + ex.getMessage());
        }
        return new Result();
    }
}
