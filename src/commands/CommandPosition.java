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
import utils.Result;

/**
 *
 */
public class CommandPosition extends CommandInterface {
	private CabinetType cabinet;
	int shelf;
	RouteEffectType effect;

	public CommandPosition(CabinetType cabinet, int shelf, RouteEffectType effect) {
		this.cabinet = cabinet;
		this.shelf = shelf;
		this.effect = effect;
	}

	public Result execute(CommandArguments args) {
		return movePosition(args, cabinet, shelf, effect);
	}

	public String details() {
		return "Position(" + cabinet.toString() + ", " + shelf + ", " + effect.toString() + ")";
	}
}
