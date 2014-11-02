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

import commandqueue.CommandQueues;
import enums.*;
import utils.Result;

/**
 * Command to show a sign
 */
public class CommandShowSign extends CommandInterface {

	int layer;
	RouteEffectType effect;
	boolean signRunning = false;

	/**
	 * Constructor that sets up the command parameters
	 *
	 * @param layer  Layer shelf to show
	 * @param effect effect to use
	 */
	public CommandShowSign(int layer, RouteEffectType effect) {
		this.layer = layer;
		this.effect = effect;
	}

	/**
	 * Executes the robot commands for showing the sign
	 *
	 * @param args Robot positional information
	 *
	 * @return Result with success/fail information
	 */
	public Result execute(CommandArguments args) {
		CabinetType cpCabinet = utils.Utils.shelfToCPCabinet(this.layer);

        // first, if sign is continuous -- make sure it is on queue 0 (priority).  If not, it isn't allowed because
		// other queue actions could override and we wouldn't know where the sign is...
		if ((effect == RouteEffectType.CONTINUOUS) && (getQueueIndex() != 0))
			effect = RouteEffectType.SIGN;

		// are we doing a continuous sign?
		if (effect == RouteEffectType.CONTINUOUS)
			// is this our first entry?
			if (!signRunning) {
				signRunning = true;
				// bring up the initial sign
				Result result = this.moveLayer(args, cpCabinet, layer, cpCabinet, -1, RouteEffectType.CONTINUOUS_START);
				if (!result.success())
					return result;
				return new Result(CommandCompletion.INCOMPLETE);
			} else {
				// should we put the sign away?
				CommandQueues cmdq = CommandQueues.getInstance();
				if (cmdq.queueDepth() == 0) {
					// nope, keep running the sign
					Result result = this.moveLayer(args, cpCabinet, -1, cpCabinet, -1, RouteEffectType.CONTINUOUS);
					if (!result.success())
						return result;
					return new Result(CommandCompletion.INCOMPLETE);
				} else {
					// yep, put it away
					Result result = this.moveLayer(args, cpCabinet, -1, cpCabinet, layer, RouteEffectType.CONTINUOUS_END);
					return result;
				}
			}
		else {
			// show the sign
			Result result = this.moveLayer(args, cpCabinet, layer, cpCabinet, layer, effect);
			return result;
		}
	}

	/**
	 * Return details about this command
	 *
	 * @return Human readable string that describes this command
	 */
	public String details() {
		return "ShowSign(" + layer + ", " + effect.toString() + ")";
	}
}
