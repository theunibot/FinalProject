//
// This file is part of theunibot.
//
// theunibot is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// theunibot is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with theunibot.  If not, see <http://www.gnu.org/licenses/>.
//
// Copyright (c) 2014 Unidesk Corporation
// 
package robotoperations;
//

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import utils.FileUtils;
import utils.Result;
//
///**
// *
// */

public class R12OperationsUSB
{

    //Command Objects
    private SerialPort serialPort;
    private static R12OperationsUSB r12Operations = null;

    //Connect vars
    private String serialPortName = "/dev/ttyUSB0";

    //INI vars
    private final String INI_FILENAME = "R12CommSetupUSB.ini";
    private final String INI_FILE_SECTION_KEY = "vars";
    private final String INI_FILE_SERIAL_PORT_KEY = "serialportkey";

    private final String INI_CONTENTS = ""
            + ";ini file for the setup of the R12 TCP Connection.\n"
            + "[vars]\n"
            + ";To check the USB serial port name, enter \"ls /dev/ttyUSB*\"\n"
            + INI_FILE_SERIAL_PORT_KEY + "=" + serialPortName;

    private boolean simulated = false;  // do not change this here - change in ArmOperations

    /**
     * Constructor made private since this is a singleton interface
     */
    private R12OperationsUSB()
    {
    }

    /**
     * initializes object's dependencies
     *
     * @return Result with success/fail info
     */
    public Result init(boolean simulated)
    {
        this.simulated = simulated;
        if (simulated)
        {
            return new Result();
        }
        else
        {
            loadInfoFromFile();
            serialPort = new SerialPort(serialPortName);
            try
            {
                serialPort.openPort();//Open port
                serialPort.purgePort(SerialPort.PURGE_RXABORT + SerialPort.PURGE_RXCLEAR + SerialPort.PURGE_TXABORT + SerialPort.PURGE_TXCLEAR);
                serialPort.setParams(SerialPort.BAUDRATE_19200, 8, 1, 0);//Set params
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            }
            catch (SerialPortException ex)
            {
                return new Result("Serial port startup failed.");
            }
            return new Result();
        }
    }

    /**
     * Returns a wrapper object holding data from response.
     *
     * @param command command sent, used to filter out of response.
     * @return ResponseObject wrapper object for command sent
     */
    public ResponseObject getResponse(String command)
    {
        if (simulated)
        {
            return new ResponseObject(ArmOperations.RESPONSE_OK, true);
        }
        else
        {
            String responseStr = readNoEcho(command);

            //clean up string
            responseStr = responseStr.replace("\n>", "");//filters the ">" and the new line. Saves all other new lines
            responseStr = responseStr.replace(">", "");//removes any missed ">"
            responseStr = responseStr.trim();
            boolean succesful = false;
            if (responseStr.endsWith(ArmOperations.RESPONSE_OK))
            {
                succesful = true;
            }
            return new ResponseObject(responseStr, succesful);

        }
    }

    private void loadInfoFromFile()
    {

        String pathToFile = FileUtils.getFilesFolderString() + INI_FILENAME;
        /*=====Parsing File===*/

        Map<String, String> map = FileUtils.readINIFileOrGenerate(
                pathToFile,
                INI_FILE_SECTION_KEY,
                new String[]
                {
                    INI_FILE_SERIAL_PORT_KEY
                },
                INI_CONTENTS);
        String serialPortTemp = map.get(INI_FILE_SERIAL_PORT_KEY);
        if (serialPortTemp != null)
        {
            serialPortName = serialPortTemp;

        }
        System.out.println("Serialport: " + serialPortName);
    }

    /**
     * Returns the response without the echo
     *
     * @param command command to filter out
     * @return response without the command
     */
    private String readNoEcho(String command)
    {
        return read().replaceFirst(command, "").trim();
    }

    /**
     * Reads using the R12Interface, responds with a usable string.
     *
     * @return String including echo of command
     */
    private String read()
    {
        byte[] bytes = null;
        byte[] finalByteArray = new byte[65535];
        int byteIterator = 0;
        try
        {
            //spin wait until data is found
            while ((bytes = serialPort.readBytes()) == null);

            //read while there still is data and data isn't '>'
            readData:
            while (true)
            {
                if (bytes != null)
                {
                    for (int i = 0; i < bytes.length; i++)
                    {
                        finalByteArray[i + byteIterator] = bytes[i];
                    }
                    byteIterator += bytes.length;

                    System.out.println(new String(finalByteArray, 0, byteIterator).replace("\r", "\\r").replace("\n", "\\n"));
                    //ends with "\n>"
                    if (byteIterator >= 2 && finalByteArray[(byteIterator - 1)] == (char) ('>') && finalByteArray[(byteIterator - 2)] == (char) ('\n'))
                    {
                        break readData;
                    }
                }
                bytes = serialPort.readBytes();
            }
        }
        catch (SerialPortException ex)
        {
            Logger.getLogger(R12OperationsUSB.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

        String s = new String(finalByteArray, 0, byteIterator);
//        s = s.replace("\r", "\\r");
//        s = s.replace("\n", "\\n");
        return new String(s);

    }

    /**
     * Writes to the R12 the command. Automatically includes the needed return.
     *
     * @param s command to send, no return needed
     */
    public void write(String s)
    {
        System.out.println("      > " + s);
        if (!simulated)
        {
            try
            {
                serialPort.writeString(s + "\r");
            }
            catch (SerialPortException ex)
            {
                Logger.getLogger(R12OperationsUSB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static R12OperationsUSB getInstance()
    {
        if (r12Operations == null)
        {
            r12Operations = new R12OperationsUSB();
        }
        return r12Operations;
    }
}
