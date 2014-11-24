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
package commands;

import enums.*;
import inventory.Inventory;
import utils.*;
import route.DynamicRoute;

/**
 * Command that recompiles the route cache
 */
public class CommandRecompile extends CommandInterface {
	private boolean invalidateCache;

	/**
	 * Constructor initializes the parameters for the recompile command
	 *
	 * @param invalidateCache set to true if the cache should be invalidated; false will compile any still pending completion
	 */
	public CommandRecompile(boolean invalidateCache) {
		this.invalidateCache = invalidateCache;
	}

	/**
	 * Execute command to recompile the dynamic route cache
	 *
	 * @param args Standard command args to track the arm location
	 *
	 * @return Result with success/fail info
	 */
	public Result execute(CommandArguments args) {
		DynamicRoute dr = new DynamicRoute();
		
		if (invalidateCache)
			dr.invalidate();
		
		// compile them all...
		while (true) {
			Result result = dr.recompile();
			if (result == null)
				return new Result();
			if (!result.success())
				return result;
		}
	}

	/**
	 * Return details about this command
	 *
	 * @return Human readable string that describes this command
	 */
	public String details() {
		return "Recompile(" + (invalidateCache ? "true" : "false") + ")";
	}
}
