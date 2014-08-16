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
import robotoperations.ArmOperations;
import utils.FileUtils;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class PositionLookup
{

    private final String FILE_NAME = "PositionLookupTable.txt";
    private HashMap<CabinetType, HashMap<Integer, Position>> positions;

    private boolean USE_LINE_NUMS = true;

    private String FILE_CONTENTS = ""
            + "//\n"
            + "// This file defines all known positions in front of each shelf\n"
            + "//\n"
            + "// Each cabinet is defined with a '#CABINET_NAME' line, followed\n"
            + "// by a series of shelf positions.  A shelf position starts with\n"
            + "// the shelf id, following by three or six numbers.  The first three\n"
            + "// numbers identify the X Y and Z coordinates.  The following three\n"
            + "// numbers identify the PITCH YAW and ROLL.  If these last three are\n"
            + "// not specified, they will default to the last used value for the cabinet,\n"
            + "// or 0 0 0 if no prior value used.\n"
            + "//\n";

    private static PositionLookup plt = null;

    /**
     * Implement a singleton interface for this object
     *
     * @return instance of this object
     */
    public static PositionLookup getInstance() {
        if (plt == null)
        {
            plt = new PositionLookup();
        }
        return plt;
    }

    /**
     * Private constructor limits instantiation to singleton getInstance
     */
    private PositionLookup() {
    }

    /**
     * Load the disk-based table into memory for all positions
     * 
     * @return Result with success/failure info
     */
    public Result init() {
        positions = new HashMap<CabinetType, HashMap<Integer, Position>>();

        Result result = parseFile();
        if (!result.success())
            return result;

        if (main.Main.DEBUG)
            System.out.println("Position Lookup Table Initialized.");

        // and program the positions into the controller
        return new Result(); 
    }

    /**
     * Program the array of positions into the robot
     */
    public Result programPositions(String name) {
        ArmOperations ao = ArmOperations.getInstance();

        // reload from the file
        Result result = init();
        if (!result.success())
            return result;
        
        // scan through all cabinets...
        for (Entry<CabinetType, HashMap<Integer, Position>> cabEntry : positions.entrySet()) {
            CabinetType cabinet = cabEntry.getKey();
            HashMap<Integer, Position> posHash = cabEntry.getValue();

            // now scan all positions and program them up
            for (Entry<Integer, Position> posEntry : posHash.entrySet()) {
                Position pos = posEntry.getValue();
                // is this name the one desired to be programmed?
                if ( (name == null) || (pos.getName().toLowerCase().startsWith(name))) {
                    // program the point
                    result = ao.learnPoint(pos);
                    if (!result.success())
                        return result;
                }
            }
        }
        return new Result();
    }

    /**
     * Converts the given cabinet and shelf to a Position coord.
     *
     * @param cabinet Cabinet that contains the shelf
     * @param shelf Shelf within the cabinet
     * @return Position with the coordinates for the point in front of that
     * shelf/cabinet
     */
    public Position shelfToPosition(CabinetType cabinet, int shelf) {
        // first, find the cabinet
        HashMap<Integer, Position> cabinetPositions = positions.get(cabinet);
        if (cabinetPositions == null) {
            System.err.println("Unable to locate cabinet " + cabinet.toString() + " in position table");
            return null;
        }
        // now see if we can find the shelf
        Position pos = cabinetPositions.get(shelf);
        if (pos == null) {
            System.err.println("Unable to locate shelf " + shelf + " in cabinet " + cabinet.toString() + " in position table");
            return null;
        }
        return pos;
    }

    /**
     * Helper method to return the HOME position in cartesian coordinates
     *
     * @return Position with the HOME position
     */
    public static Position homePosition() {
        return PositionLookup.getInstance().shelfToPosition(CabinetType.HOME, 0);
    }

    //<editor-fold defaultstate="collapsed" desc="Parse File Stuff">
    private Result parseFile()
    {
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + FILE_NAME, FILE_CONTENTS);
        if (lines != null)
        {
            System.out.println("Read " + lines.size() + " line(s) from position file.");

            Position prevPosition = new Position("None", "0", "0", "0", "0", "0", "0");

            double xOffset = 0;
            double yOffset = 0;
            double zOffset = 0;
            double pitchOffset = 0;
            double yawOffset = 0;
            double rollOffset = 0;

            CabinetType ct = null;
            for (String line : lines)
            {
                // if it starts with "#", we want to process the header (name of the cabinet)
                if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))
                {
                    // remove the "#" prefix
                    line = line.replace(FileUtils.COMMAND_FILE_METADATA_PREFIX, "");
                    String[] chunks = line.split(" ");
                    if (chunks.length == 7)
                    {

                        // take the remaining name as see if we can convert to a cabinet type
                        try
                        {
                            ct = CabinetType.valueOf(chunks[0].trim().toUpperCase());
                        }
                        catch (IllegalArgumentException e)
                        {
                            return new Result("PositionLookupTable file has unknown CabinetType of " + line.trim().toUpperCase());
                        }

                        // reset our last known position
                        prevPosition = new Position("None", "0", "0", "0", "0", "0", "0");

                        //update our offsets
                        xOffset = Double.parseDouble(chunks[1]);
                        yOffset = Double.parseDouble(chunks[2]);
                        zOffset = Double.parseDouble(chunks[3]);
                        pitchOffset = Double.parseDouble(chunks[4]);
                        yawOffset = Double.parseDouble(chunks[5]);
                        rollOffset = Double.parseDouble(chunks[6]);
                        
                        
                    }
                    else
                    {
                        return new Result("Position Lookup Line " + line + " missing data");
                    }
                }
                else
                {
                    // decode the position (Shelf X Y Z Pitch Yaw Roll, or just Shelf X Y Z)

                    // break up the line into multiple pieces split on spaces
                    String[] splitLinePieces = line.trim().split(" ");

                    // get the shelf id
                    int shelf;
                    try
                    {
                        shelf = Integer.valueOf(splitLinePieces[0]);
                    }
                    catch (IllegalArgumentException e)
                    {
                        return new Result("Invalid shelf ID " + splitLinePieces[0] + " in PositionLookupTable");
                    }

                    // build up a name of this position
                    String name;
                    if ((ct == CabinetType.D1) || (ct == CabinetType.D2))
                    {
                        name = ct.toString() + "_" + shelf;
                    }
                    else
                    {
                        name = ct.toString() + shelf;
                    }

                    // is this three (Shelf, X,Y,Z) or does it include yaw,pitch,roll?
                    Position pos;
                    
                    // determine positions to use
                    String px = Utils.xyInToMmStr(String.valueOf(Double.parseDouble(splitLinePieces[1]) + xOffset));
                    String py = Utils.xyInToMmStr(String.valueOf(Double.parseDouble(splitLinePieces[2]) + yOffset));
                    String pz;
                    if (shelf < 90)
                        pz = Utils.zInToMmStr(String.valueOf(Double.parseDouble(splitLinePieces[3]) + zOffset));
                    else
                        pz = Utils.xyInToMmStr(String.valueOf(Double.parseDouble(splitLinePieces[3]) + zOffset));
                        

                    if (splitLinePieces.length == 4)
                    {
                        pos = new Position(name, px, py, pz, 
                                String.valueOf(prevPosition.getPitch() + pitchOffset), 
                                String.valueOf(prevPosition.getYaw() + yawOffset), 
                                String.valueOf(prevPosition.getRoll() + rollOffset));
                    }
                    else if (splitLinePieces.length == 7)
                    {
                        pos = new Position(name, px, py, pz, 
                                String.valueOf(Double.parseDouble(splitLinePieces[4]) + pitchOffset), 
                                String.valueOf(Double.parseDouble(splitLinePieces[5]) + yawOffset), 
                                String.valueOf(Double.parseDouble(splitLinePieces[6]) + rollOffset));
                    }
                    else
                    {
                        return new Result("PositionLookupTable invalid syntax: " + line);
                    }

                    // save this away for later reference to get default values
                    prevPosition = pos;

                    // make sure we know the cabinet type...
                    if (ct == null)
                    {
                        return new Result("Position found before CabinetType defined in PositionLookupTable (line " + line + ")");
                    }

                    // and tuck it into the hashmap.  
                    // do we know the cabinet?
                    if (positions.get(ct) == null)
                    // create the cabinet
                    {
                        positions.put(ct, new HashMap<Integer, Position>());
                    }
                    // make sure we don't already know this id
                    if (positions.get(ct).get(shelf) != null)
                    {
                        return new Result("Duplicate shelf in cabinet " + ct.toString() + " shelf " + shelf + " in PositionLookupTable");
                    }
                    // now add the position
                    positions.get(ct).put(shelf, pos);
                }
            }
        }
        // success!
        return new Result();
    }

    //</editor-fold>
}
