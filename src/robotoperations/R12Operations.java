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
import static utils.FileUtils.readINIFile;
import utils.Result;
import java.util.regex.Pattern;

/**
 *
 */
public class R12Operations {
	//Command Objects
	private R12Interface r12i = null;
	private static R12Operations r12Operations = null;

	private boolean simulated = false; 

	/**
	 * Constructor made private since this is a singleton interface
	 */
	private R12Operations() {
	}
	
	/**
	 * determines if we are currently simulating r12 ops
	 * 
	 * @return boolean with true if we are currently simulating
	 */
	public boolean simulated() {
		return this.simulated;
	}

	/**
	 * Implement a singleton interface to this class
	 * 
	 * @return R12Operations object singleton 
	 */
	public static R12Operations getInstance() {
		if (r12Operations == null)
			r12Operations = new R12Operations();
		return r12Operations;
	}

    /**
	 * Initialize the R12 system, including loading and connecting to the arm with the appropriate interface
	 *
	 * @return Result with success/fail info
	 */
	public Result init() {
		// load up the INI file with configuration info
		Map<String, String> map = readINIFile(FileUtils.getFilesFolderString() + "R12Setup.ini", 
				"interface", new String[] { "driver", "port", "address", "device", "simulate" });
		if (map == null)
			return new Result("Unable to load INI file " + FileUtils.getFilesFolderString() + "R12Setup.ini");

		// determine if we are simulated
		if ( (map.get("simulate") != null) && 
			(map.get("simulate").toLowerCase().equals("true")) )
			this.simulated = true;
		else
			this.simulated = false;
		
		// if simualted, we are done
		if (this.simulated)
			return new Result();
		
		// now determine which interface to load
		if (map.get("driver") == null)
			return new Result("Missing [interface] Driver=<type> in R12Setup.ini file");

		switch (map.get("driver").toLowerCase()) {
			case "ip":
				r12i = new R12InterfaceIP();
				break;
			case "serial":
				r12i = new R12InterfaceSerial();
				break;
			default:
				return new Result("Uknown driver type in R12Setup.ini: " + map.get("driver"));
		}
		return r12i.init(map);
	}

	/**
	 * Returns a wrapper object holding data from response.
	 *
	 * @param command command sent, used to filter out of response.
	 *
	 * @return ResponseObject wrapper object for command sent
	 */
	public ResponseObject getResponse(String command) {
            if (simulated)
                    return new ResponseObject("OK", true);
            else {
                // adjust protocol based on command being sent
                int responses = 1;
                String success = "OK";
                switch (command.toLowerCase().trim()) {
                    case "roboforth":
                    case "start":
                        responses = 2;
                        break;
                    case "org jump":
                        success = "1994";
                        break;
                }
                String responseStr = "";
                for (int responseLoop = 0; responseLoop < responses; ++responseLoop) {
                    responseStr = readNoEcho(command);
                    System.out.println(responseStr);
                }
                //clean up string
                responseStr = responseStr.replace("\n>", "");		//filters the ">" and the new line. Saves all other new lines
                responseStr = responseStr.replace(">", "");			//removes any missed ">"
                responseStr = responseStr.trim();
                boolean succesful = false;
                if (responseStr.endsWith(success))
                    succesful = true;
                return new ResponseObject(responseStr, succesful);
            }
	}


	/**
	 * Returns the response without the echo
	 *
	 * @param command command to filter out
	 *
	 * @return response without the command
	 */
	private String readNoEcho(String command) {
            return read().replaceFirst(Pattern.quote(command), "").trim();
	}

	/**
	 * Reads using the R12Interface, responds with a usable string.
	 *
	 * @return String including echo of command
	 */
	private String read() {
            return r12i.read('>');
	}

	/**
	 * Writes to the R12 the command. Automatically includes the needed return.
	 *
	 * @param s command to send, no return needed
	 */
	public void write(String s) {
            System.out.println("      > " + s.replaceAll("\r", "\n        > "));
            if (!simulated)
                r12i.write(s + "\r");
	}

}
