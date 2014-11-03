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
public class CommandRoute extends CommandInterface {
	private CabinetType fromCabinet;
	private CabinetType toCabinet;
	private RouteEffectType effect;
	private Position fromPosition;
	private Position toPosition;

	public CommandRoute(CabinetType fromCabinet, Position fromPosition, CabinetType toCabinet, Position toPosition, RouteEffectType effect) {
		this.fromCabinet = fromCabinet;
		this.toCabinet = toCabinet;
		this.effect = effect;
		this.fromPosition = fromPosition;
		this.toPosition = toPosition;
	}

	public Result execute(CommandArguments args) {
		// find the route
		RouteHolder rh = RouteHolder.getInstance();

		Route route = rh.getRoute(fromCabinet, toCabinet, effect);
		if (route == null)
			return new Result("Unable to locate route from " + fromCabinet.toString() + " to " + toCabinet.toString() + " for effect " + effect.toString());
		ArmOperations ao = ArmOperations.getInstance();
		Result result = ao.runRoute(route, fromPosition, toPosition);
		if (!result.success())
			return result;

		// update args to reflect our new position
		args.cabinet = toCabinet;
		args.coordinates = toPosition;

		return new Result();
	}

	public String details() {
		return "Route(" + fromCabinet + ", " + fromPosition.getName() + ", " + toCabinet + ", " + toPosition.getName() + ", " + effect + ")";
	}

	/**
	 * This command should be allowed to run, even if we have outstanding robot
	 * errors
	 *
	 * @return true to indicate we should run even during errors
	 */
	public boolean ignoreErrors() {
		return true;
	}
}
