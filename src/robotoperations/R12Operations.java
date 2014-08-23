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
import utils.FileUtils;
import utils.Result;

/**
 *
 */
public class R12Operations
{
    //Command Objects
    private R12Interface r12i = null;
    private static R12Operations r12Operations = null;

    //Connect vars
    private String address = "192.168.1.1";
    private int port = 1111;

    //INI vars
    private final String INI_FILENAME = "R12CommSetup.ini";
    private final String INI_FILE_SECTION_KEY = "vars";
    private final String INI_FILE_PORT_KEY = "portkey";
    private final String INI_FILE_ADDRESS_KEY = "addresskey";

    private final String INI_CONTENTS = ";ini file for the setup of the R12 TCP Connection.\n"
            + "[vars]\n"
            + INI_FILE_ADDRESS_KEY + "=" + address + "\n"
            + INI_FILE_PORT_KEY + "=" + port;

    private boolean simulated = false;  // do not change this here - change in ArmOperations
    
    /**
     * Constructor made private since this is a singleton interface
     */
    private R12Operations() {
    }
    /**
     * initializes object's dependencies
     *
     * @return Result with success/fail info
     */
    public Result init(boolean simulated){
        this.simulated = simulated;
        if(simulated){
            return new Result();
        }
        else{
            r12i = R12Interface.getInstance();
            loadInfoFromFile();
            return r12i.init(address, port);
        }
    }
    
    /**
     * Returns a wrapper object holding data from response.
     *
     * @param command command sent, used to filter out of response.
     * @return ResponseObject wrapper object for command sent
     */
    public ResponseObject getResponse(String command){    
        if(simulated){
            return new ResponseObject(ArmOperations.RESPONSE_OK, true);
        }
        else{
            String responseStr = readNoEcho(command);
System.out.println(responseStr);
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
                    INI_FILE_PORT_KEY, INI_FILE_ADDRESS_KEY
                },
                INI_CONTENTS);
        String portTemp = map.get(INI_FILE_PORT_KEY);
        String addressTemp = map.get(INI_FILE_ADDRESS_KEY);
        if (portTemp != null)
        {
            port = Integer.parseInt(portTemp);

        }
        if (addressTemp != null)
        {
            address = addressTemp;

        }
        System.out.println("Address: " + address + " Port: " + port);
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
        byte[] buffer = new byte[65536];
        int offsetIterator = 0;//length of the buffer. Actual last pos is this - 1
        int response = 0;
        do
        {
            response = r12i.read(buffer, offsetIterator);
            if (response < 0)
            {
                System.err.println("Error, response was " + response);
            }
            offsetIterator += response;
        }
        while (buffer[offsetIterator - 1] != '>');
        String s = new String(buffer, 0, offsetIterator);
//        System.out.println("Read Buffered: " + s);
        return s;
    }

    /**
     * Writes to the R12 the command. Automatically includes the needed return.
     *
     * @param s command to send, no return needed
     */
    public void write(String s){
        System.out.println("      > " + s.replaceAll("\r", "\n        > "));
        if (!simulated)
            r12i.write(s + "\r");
    }

    public static R12Operations getInstance()
    {
        if (r12Operations == null)
        {
            r12Operations = new R12Operations();
        }
        return r12Operations;
    }
}
