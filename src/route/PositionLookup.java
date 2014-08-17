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

    private final String POSITION_FILE_NAME = "Positions.txt";
    private final String ADJUSTMENT_FILE_NAME = "Adjustments.txt";
    private HashMap<CabinetType, HashMap<Integer, Position>> positions;
    private HashMap<CabinetType, HashMap<Integer, Position>> adjustments;

    private boolean USE_LINE_NUMS = true;

    private String POSITION_FILE_CONTENTS = ""
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
    
    private String ADJUSTMENT_FILE_CONTENTS = ""
            + "//\n"
            + "// This file defines all adjustments for all known positions of each shelf\n"
            + "//\n"
            + "// This file defines minor adjustments, in millimeters, to the positions\n"
            + "// contained in the Positions.txt file.  It can be edited by hand, or is also\n"
            + "// updated by commands from within the software.  This provides for tuning and\n"
            + "// calibration of the surface without having to adust the primary coordinate\n"
            + "// file.  Additionally, adjustments can be made on-the-fly while the software\n"
            + "// is operational.  The syntax is basically the same as the Positions.txt file,\n"
            + "// except that each line defines the relative offset, rather than the absolute\n"
            + "// position.\n"
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
        adjustments = new HashMap<CabinetType, HashMap<Integer, Position>>();
        
        Result result = loadPositionFile(POSITION_FILE_NAME, POSITION_FILE_CONTENTS, positions, true);
        if (!result.success())
            return result;
        
        result = loadPositionFile(ADJUSTMENT_FILE_NAME, ADJUSTMENT_FILE_CONTENTS, adjustments, false);
        if (!result.success())
            return result;

        if (main.Main.DEBUG)
            System.out.println("Positions (and adjustments) loaded");

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
                    // locate the point again, because this will implement any calibration adjustments
                    Position adjPos = shelfToPosition(cabinet, posEntry.getKey());
                    if (adjPos != null)
                        pos = adjPos;

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
        
        // clone the position, so changes are local instance and not affecting the original database
        pos = new Position(pos.getName(), pos);
        
        // see if we have an adjustment position for this
        HashMap<Integer, Position> adjustCabinetPositions = adjustments.get(cabinet);
        if (adjustCabinetPositions != null) {
            Position adjustPos = adjustCabinetPositions.get(shelf);
            if (adjustPos != null) {
                // apply the adjustments
                System.out.println("Adjusting position by " + adjustPos.toString());
                pos.offsetPositions(adjustPos);
            }
        }
        
        System.out.println("Resulting position is " + pos.toString());
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
    
    /**
     * Locate (and create if needed) an adjustment position
     * 
     * @param cabinet Cabinet to adjust
     * @param shelf Shelf within cabinet to adjust
     * @return Position from the adjustment table
     */
    public Position shelfToAdjustmentPosition(CabinetType cabinet, int shelf) {
        // create cabinet if it doesn't already exist
        if (adjustments.get(cabinet) == null)
            adjustments.put(cabinet, new HashMap<Integer, Position>());

        // do we already have an adjustment?
        Position adjPos = adjustments.get(cabinet).get(shelf);
        if (adjPos != null)
            return adjPos;
        else {
            // locate the original position so we can get the name
            Position origPos = shelfToPosition(cabinet, shelf);
            if (origPos == null) {
                System.err.println("shelfToAdjustmentPosition: Unable to locate position for " + cabinet.toString() + " shelf " + shelf);
                return null;
            }
            Position newPos = new Position(origPos.getName());
            adjustments.get(cabinet).put(shelf, newPos);
            return newPos;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Parse File Stuff">
    
    /**
     * Loads the position file into memory as a collection of positions
     * 
     * @return Result with success/fail information 
     */
    private Result loadPositionFile(String fileName, String defaultHeader, HashMap<CabinetType, HashMap<Integer, Position>> hashMap,
            boolean adjustZOffset)
    {
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + fileName, defaultHeader);
        if (lines != null)
        {
            System.out.println("Read " + lines.size() + " line(s) from " + fileName);

            Position prevPosition = new Position("None");

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
                            return new Result(fileName + " file has unknown CabinetType of " + line.trim().toUpperCase());
                        }

                        // reset our last known position
                        prevPosition = new Position("None");

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
                        return new Result(fileName + " Lookup Line " + line + " missing data");
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
                        return new Result("Invalid shelf ID " + splitLinePieces[0] + " in " + fileName);
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
                    double px = Utils.inToMm(Double.parseDouble(splitLinePieces[1]) + xOffset);
                    double py = Utils.inToMm(Double.parseDouble(splitLinePieces[2]) + yOffset);
                    double pz;
                    if ( (shelf < 90) && (adjustZOffset) )
                        pz = Utils.zInToMm(Double.parseDouble(splitLinePieces[3]) + zOffset);
                    else
                        pz = Utils.inToMm(Double.parseDouble(splitLinePieces[3]) + zOffset);
                        

                    if (splitLinePieces.length == 4)
                    {
                        pos = new Position(name, px, py, pz, 
                                prevPosition.getPitch() + pitchOffset, 
                                prevPosition.getYaw() + yawOffset, 
                                prevPosition.getRoll() + rollOffset);
                    }
                    else if (splitLinePieces.length == 7)
                    {
                        pos = new Position(name, px, py, pz, 
                                Double.parseDouble(splitLinePieces[4]) + pitchOffset, 
                                Double.parseDouble(splitLinePieces[5]) + yawOffset, 
                                Double.parseDouble(splitLinePieces[6]) + rollOffset);
                    }
                    else
                    {
                        return new Result(fileName + " invalid syntax: " + line);
                    }

                    // save this away for later reference to get default values
                    prevPosition = pos;

                    // make sure we know the cabinet type...
                    if (ct == null)
                    {
                        return new Result("Position found before CabinetType defined in " + fileName + " (line " + line + ")");
                    }

                    // and tuck it into the hashmap.  
                    // do we know the cabinet?
                    if (hashMap.get(ct) == null)
                    // create the cabinet
                    {
                        hashMap.put(ct, new HashMap<Integer, Position>());
                    }
                    // make sure we don't already know this id
                    if (hashMap.get(ct).get(shelf) != null)
                    {
                        return new Result("Duplicate shelf in cabinet " + ct.toString() + " shelf " + shelf + " in " + fileName);
                    }
                    // now add the position
                    hashMap.get(ct).put(shelf, pos);
                }
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
    public Result saveAdjustmentFile() {
        StringBuilder builder = new StringBuilder();
        
        // place the header into the builder
        builder.append(ADJUSTMENT_FILE_CONTENTS);
        
        // scan through all cabinets...
        for (Entry<CabinetType, HashMap<Integer, Position>> cabEntry : adjustments.entrySet()) {
            CabinetType cabinet = cabEntry.getKey();
            HashMap<Integer, Position> posHash = cabEntry.getValue();

            // add the header for the cabinet
            builder.append("\n");
            builder.append(FileUtils.COMMAND_FILE_METADATA_PREFIX);
            builder.append(cabinet.toString());
            builder.append(" 0.0 0.0 0.0 0.0 0.0 0.0");
            builder.append("\n");
            
            // now scan all positions
            for (Entry<Integer, Position> posEntry : posHash.entrySet()) {
                Position pos = posEntry.getValue();

                // add the point
                builder.append(posEntry.getKey());
                builder.append(" ");
                builder.append(Utils.mmToIn(pos.getX()));
                builder.append(" ");
                builder.append(Utils.mmToIn(pos.getY()));
                builder.append(" ");
                builder.append(Utils.mmToIn(pos.getZ()));
                builder.append(" ");
                builder.append(pos.getPitchStr());
                builder.append(" ");
                builder.append(pos.getYawStr());
                builder.append(" ");
                builder.append(pos.getRollStr());
                builder.append("\n");
            }
        }
        
        // write it to the file
        if (!FileUtils.createFile(FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME, builder.toString()))
            return new Result("Unable to create adjustment file " + FileUtils.getFilesFolderString() + ADJUSTMENT_FILE_NAME);
        
        return new Result();
    }    
    //</editor-fold>
}
