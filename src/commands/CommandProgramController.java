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

import robotoperations.ArmOperations;
import route.PositionLookup;
import route.RouteCompiler;
import utils.Result;

/**
 *
 */
/*
public class CommandProgramController extends CommandInterface {
	private String name;

	public CommandProgramController(String name) {
		this.name = name;
		if ((this.name != null) && (this.name.length() == 0))
			this.name = null;
	}

	public Result execute(CommandArguments args) {
		ArmOperations ao = ArmOperations.getInstance();

		// if all, do a full reset of the controller
		if (name == null) {
			Result result = ao.restartController();
			if (!result.success())
				return result;
		}
		Result result = PositionLookup.getInstance().programPositions(name);
		if (!result.success())
			return result;
		result = RouteCompiler.getInstance().programRoutes(name);
		if (!result.success())
			return result;
		// now cause these to be permanent
		return ao.persist();
	}

	public String details() {
		if (name == null)
			return "ProgramController()";
		return "ProgramController(" + name + ")";
	}
*/
	/**
	 * This command should be allowed to run, even if we have outstanding robot
	 * errors
	 *
	 * @return true to indicate we should run even during errors
	 */
/*
	public boolean ignoreErrors() {
		return true;
	}
}
*/