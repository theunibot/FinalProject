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
import robotoperations.ArmOperations;
import route.Position;
import route.PositionLookup;
import route.Route;
import route.RouteHolder;
import utils.Result;

/**
 *
 */
public class CommandCalibratePosition extends CommandInterface {
	CabinetType cabinet;
	int shelf;
	String plunge;
	int depth;
	int speed;

	public CommandCalibratePosition(CabinetType cabinet, int shelf, String plunge, int depth, int speed) {
		this.cabinet = cabinet;
		this.shelf = shelf;
		this.plunge = plunge;
		this.depth = depth;
		this.speed = speed;
	}

	public Result execute(CommandArguments args) {
//        Result result = movePosition(args, cabinet, shelf, RouteEffectType.EFFICIENT);
//        if (!result.success())
//            return result;

		ArmOperations ao = ArmOperations.getInstance();
		return ao.calibratePoint(cabinet, shelf, plunge, depth, speed);
	}

	public String details() {
		return "CalibratePosition(" + cabinet.toString() + ", " + shelf + ", " + plunge + ", " + depth + ", " + speed + ")";
	}

	/**
	 * This command should be allowed to run, even if we have outstanding robot
	 * errors
	 *
	 * @return true to indicate we should run even during errors
	 */
	public boolean ignoreErrors() {
		return false;
	}

}
