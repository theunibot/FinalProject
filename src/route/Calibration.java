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

package route;
import enums.CabinetType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import utils.FileUtils;
import utils.Result;
import utils.Utils;


/**
 * Position calibration services, to enable minor adjustments of the arm
 * on a position by position basis, persisted to storage.
 */
public class Calibration {
	// define the singleton instance of this class
	private static Calibration calibration = new Calibration();
	// define the map of a position hash to an adjustment value.  The hash is an MD5 of
	// the original (non-adjusted) position (position.hash()) and the resulting
	// Position is the delta adjustment to that position.
	private HashMap<String, Position> adjustments = new HashMap<String, Position>();
	
	private final static String ADJUSTMENT_FILE_NAME = "Adjustments.txt";

	/**
		Ensure this is a singleton interface
	*/
	private Calibration() {
		load();
	}
	
	/**
	 * return the singleton of this class
	 * 
	 * @return Calibration singleton
	 */
	public static Calibration getInstance() {
		return calibration;
	}
	
	/**
	 * Locate an adjustment from the calibration tables
	 * 
	 * @param pos Original non-adjusted position to locate
	 * @return Delta adjustment position
	 */
	public Position get(Position pos) {
		Position adj = adjustments.get(pos.hash());
		// if it doesn't exist, add it
		if (adj == null) {
			adj = new Position(pos.getName());
			adjustments.put(pos.hash(), adj);
		}
		return adj;
	}
	
	/**
	 * Adjust a position based on the calibration adjustment tables
	 * 
	 * @param pos Original non-adjusted position
	 * @return Position with adjustments made
	 */
	public Position adjust(Position pos) {
		Position adj = adjustments.get(pos.hash());
		if (adj == null)
			return pos;
		return pos.offsetPositions(adj);
	}
	
	/**
	 * Private method to add a position adjustment to the hashmap
	 * 
	 * @param hash Hash of the original position
	 * @param adjPos Position to add
	 */
	private void add(String hash, Position adjPos) {
		adjustments.put(hash, adjPos);		
	}

	/**
	 * Loads the adjustment file into memory 
	 * 
	 * File syntax is (one position per line)
	 * hash-of-original-position name-of-position x y z y p r
	 *
	 * @return Result with success/fail information
	 */
	private static Result load() {

		// load the file into memory
		ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME, "");
		if (lines != null) {
			System.out.println("Read " + lines.size() + " line(s) from " + FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME);
			// process all the lines
			for (String line : lines) {
				// break up the line into multiple pieces split on spaces
				String[] splitLinePieces = line.trim().split(" ");
				// make sure there are 7 items
				if (splitLinePieces.length != 7)
					return new Result(ADJUSTMENT_FILE_NAME + " invalid syntax: " + line);

				// break it apart
				String hash = splitLinePieces[0];
				String name = splitLinePieces[1];
				// determine positions to use
				double x = Double.parseDouble(splitLinePieces[2]);
				double y = Double.parseDouble(splitLinePieces[3]);
				double z = Double.parseDouble(splitLinePieces[4]);
				double pitch = Double.parseDouble(splitLinePieces[5]);
				double yaw = Double.parseDouble(splitLinePieces[6]);
				double roll = Double.parseDouble(splitLinePieces[7]);

				// create the position and add to the Calibration tables
				Position adj = new Position(name, x, y, z, pitch, yaw, roll);
				calibration.add(hash, adj);
			}
		}
		// success!
		return new Result();
	}

	/**
	 * saves the Adjustments to a disk file
	 *
	 * @return Result with success/fail information
	 */
	public Result save() {
		StringBuilder builder = new StringBuilder();
		
		// scan through all adjustments...
		for (Entry<String, Position> adj : adjustments.entrySet()) {
			String hash = adj.getKey();
			Position adjPos = adj.getValue();

			// add the point
			builder.append(hash);
			builder.append(" ");
			builder.append(adjPos.getName());
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getX()));
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getY()));
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getZ()));
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getPitch()));
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getYaw()));
			builder.append(" ");
			builder.append(Utils.formatDouble(adjPos.getRoll()));
			builder.append("\n");
		}

		// write it to the file
		if (!FileUtils.createFile(FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME, builder.toString()))
			return new Result("Unable to create adjustment file " + FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME);

		return new Result();
	}
}
