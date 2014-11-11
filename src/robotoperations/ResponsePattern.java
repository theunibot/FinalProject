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

/**
 * R12 response pattern manager - to look for specific patterns in the response and capture their values
 */
public class ResponsePattern {

	public void define(String name, String regex) {
		
	}
	
	public String lookup(String name) {
		return null;
	}

	/**
	 * process a response string according to the established patterns
	 * 
	 * @param responseMessage message from the R12 robot
	 */
	public void process(String responseMessage) {
		System.out.println("****** ResponsePattern: " + responseMessage + " END****");
	}
}
