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
import utils.Result;

/**
 * Standard interface for communications with the R12 robot arm, to be implemented
 * for specific interfaces (such as IP or serial).  Implements only the base communications,
 * and contains no specific knowledge of the R12 itself.
 */
public interface R12Interface {
    /**
     * Initialize communications channel.  Can start background thread if needed.  Passed a map
     * of keys/value pairs from the configuration INI file.
     * 
     * @return Result object indicating success/failure
     */
    public Result init(Map<String, String> parameters);
    
    /**
     * De-initialize communications channel.  Shuts down background thread(s) if needed.  Must be thread safe
     * so this can be called from a different thread that the communications thread in order to initiate a
     * successful shutdown (and unblock read/write as needed)
     */
    public void deinit();
    
    /**
     * Read data from the communications channel.  Reads bytes until the specified delimiter byte
     * is located.  Blocking interface, but must unblock if deinit is called.
     * 
     * @param delimiter character to look for to terminate the reading action
     * @return String with all bytes
     */
    public String read(char delimiter);
    
    /**
     * Write data to the communications channel.  Writes the specified string buffer to the communications
     * channel.  Blocks until the write is complete.
     * 
     * @param buffer Buffer to send to the channel
     * @return Result with success/fail info
     */
    public Result write(String buffer);
}
