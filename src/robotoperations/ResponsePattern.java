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

import java.util.HashMap;
import java.util.regex.*;

/**
 * R12 response pattern manager - to look for specific patterns in the response and capture their values
 */
public class ResponsePattern {
	private String message = null;				// the message to match against
	HashMap<String, Pattern> patternMap = new HashMap<String, Pattern>();
	
	/**
	 * Define a possible RegEx match for the output string
	 * 
	 * @param name A key name (anything) to describe/lookup this match
	 * @param regex A regular expression to locate within the results
	 */
	public void define(String name, String regex) {
		Pattern pattern = Pattern.compile(regex);
		patternMap.put(name, pattern);
	}
	
	/**
	 * Locate a regular expression match based on a key defined in 'define'
	 * 
	 * @param name key defined in prior call to 'define'
	 * @return Matching string if found, null if none
	 */
	public String lookup(String name) {
		// locate the key for the pattern
		Pattern pattern = patternMap.get(name);
		if (pattern == null)
			return null;
		// run the regex to see if we have a match
		Matcher matcher = pattern.matcher(message);
		if (matcher.find())
			return matcher.group();
		return null;
	}

	/**
	 * process a response string according to the established patterns
	 * 
	 * @param responseMessage message from the R12 robot
	 */
	public void process(String responseMessage) {
		// store away the result for later processing if requested
		this.message = responseMessage;
	}
}
