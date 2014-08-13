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
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (c) 2014 Unidesk Corporation
 */
package route;

import enums.CabinetType;
import java.util.ArrayList;
import utils.FileUtils;
import utils.Result;
import utils.Utils;

/**
 *
 */
public class PositionLookupTable
{

    private final String FILE_NAME = "PositionLookupTable.txt";
    private ArrayList<Cartesian> d1Pos = null;
    private ArrayList<Cartesian> d2Pos = null;
    private ArrayList<Cartesian> cpPos = null;

    private boolean USE_LINE_NUMS = true;

    private String FILE_CONTENTS = ""
            + "//This is the position lookup table file."
            + "\n//Inside this file, the XYZ position of each shelf on the CachePoint"
            + "\n//and Desktops are defined."
            + "\n//"
            + "\n//Use \"//\" at the beginning of any comment. Each comment must be"
            + "\n//on its own line."
            + "\n//"
            + "\n//Format:"
            + "\n//X Y Z"
            + "\n//X Y Z P Y R"
            + "\n//"
            + "\n#D1"
            + "\n#D2"
            + "\n#CP";

    private static PositionLookupTable plt = null;

    public static PositionLookupTable getInstance()
    {
        if (plt == null)
        {
            plt = new PositionLookupTable();
        }
        return plt;
    }

    private PositionLookupTable()
    {

    }

    public Result init()
    {
        d1Pos = new ArrayList<>();
        d2Pos = new ArrayList<>();
        cpPos = new ArrayList<>();
        parseFile();
        if (main.Main.DEBUG)
        {
            System.out.println("Position Lookup Table Initialized.");
        }
        //TODO: Program the robot with the commands
        return new Result();
    }

    /**
     * Converts the given cabinet and shelf to a Cartesian coord.
     *
     * @param su
     * @param shelf
     * @return
     */
    public Cartesian shelfToCartesian(CabinetType su, int shelf)
    {
        int index = Utils.shelfToIndex(shelf);
        if (su == CabinetType.D1)
        {
            if (index > 0 && index < d1Pos.size())
            {
                return d1Pos.get(index);
            }
            else
            {
                System.err.println("Shelf " + index + " not in D1");
                return null;
            }
        }
        else if (su == CabinetType.D2)
        {

            if (index > 0 && index < d2Pos.size())
            {
                return d2Pos.get(index);
            }
            else
            {
                System.err.println("Shelf " + index + " not in D2");
                return null;
            }
        }
        else if ((su == CabinetType.CPL) || (su == CabinetType.CPR) || (su == CabinetType.CPM))
        {
            if (index > 0 && index < cpPos.size())
            {
                return cpPos.get(index);
            }
            else
            {
                System.err.println("Shelf " + index + " not in CP");
                return null;
            }
        }
        else
        {
            System.err.println("Shelf Unit unknown");
            return null;
        }
    }

    /**
     * Helper method to return the HOME position in cartesian coordinates
     *
     * @return Cartesian with the HOME position
     */
    public static Cartesian homeCartesian()
    {
        return PositionLookupTable.getInstance().shelfToCartesian(CabinetType.HOME, 0);
    }

    //<editor-fold defaultstate="collapsed" desc="Parse File Stuff">
    private void parseFile()
    {
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + FILE_NAME, FILE_CONTENTS);
        if (lines != null)
        {
            CabinetType ct = null;
            for (String line : lines)
            {
                String[] splitLinePieces = line.split(" ");
                if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))//metadata
                {
                    for (String piece : splitLinePieces)
                    {
                        //remove any comment piece
                        if (piece.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))
                        {
                            piece = piece.replace(FileUtils.COMMAND_FILE_METADATA_PREFIX, "");
                        }

                        if (Utils.stringToEnumShelfType(piece) == CabinetType.D1)
                        {
                            ct = CabinetType.D1;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.D2)
                        {
                            ct = CabinetType.D2;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPL)
                        {
                            ct = CabinetType.CPL;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPM)
                        {
                            ct = CabinetType.CPM;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPR)
                        {
                            ct = CabinetType.CPR;
                        }
                    }
                }
                else//type is position X,Y,Z,Pitch,Yaw,Roll
                {
                    if (splitLinePieces.length == 3)//line num, XYZ
                    {
                        if (ct != null)
                        {
                            Cartesian prevCart;
                            Cartesian nextCart;
                            if ((ct == CabinetType.CPL) || (ct == CabinetType.CPM) || (ct == CabinetType.CPR))
                            {

                                if ((prevCart = getLastPoint(cpPos)) != null)
                                {
                                    nextCart = new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr());
                                }
                                else
                                {
                                    nextCart = new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], "0", "0", "0");

                                }
                                nextCart.setName(ct.toString() + cpPos.size());
                                cpPos.add(nextCart);
                            }
                            else if (ct == CabinetType.D1)
                            {
                                if ((prevCart = getLastPoint(d1Pos)) != null)
                                {
                                    nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr()));
                                }
                                else
                                {
                                    nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], "0", "0", "0"));
                                }
                                
                                nextCart.setName(ct.toString() + d1Pos.size());
                                d1Pos.add(nextCart);
                            }
                            else if (ct == CabinetType.D2)
                            {
                                if ((prevCart = getLastPoint(d2Pos)) != null)
                                {
                                    nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr()));
                                }
                                else
                                {
                                    nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], "0", "0", "0"));
                                }
                                nextCart.setName(ct.toString() + d2Pos.size());
                                d2Pos.add(nextCart);
                            }
                            else
                            {
                                System.err.println("Shelving Unit Unknown. Error.");
                            }
                        }
                        else//su == null
                        {
                            System.err.println("Shelf Unit not defined.");
                        }

                    }
                    else if (splitLinePieces.length == 6)//line num, XYZPYR
                    {
                        if (ct != null)
                        {
                            Cartesian nextCart;
                            if ((ct == CabinetType.CPL) || (ct == CabinetType.CPM) || (ct == CabinetType.CPR))
                            {
                                nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], splitLinePieces[3], splitLinePieces[4], splitLinePieces[5]));
                                nextCart.setName(ct.toString() + cpPos.size());
                                cpPos.add(nextCart);
                            }
                            else if (ct == CabinetType.D1)
                            {
                                nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], splitLinePieces[3], splitLinePieces[4], splitLinePieces[5]));
                                nextCart.setName(ct.toString() + d1Pos.size());
                                d1Pos.add(nextCart);
                            }
                            else if (ct == CabinetType.D2)
                            {
                                nextCart = (new Cartesian(splitLinePieces[0], splitLinePieces[1], splitLinePieces[2], splitLinePieces[3], splitLinePieces[4], splitLinePieces[5]));
                                nextCart.setName(ct.toString() + d2Pos.size());
                                d2Pos.add(nextCart);
                            }
                            else
                            {
                                System.err.println(ct.toString() + " is not recognized.");
                            }
                            
                        }
                        else
                        {
                            System.err.println("Shelf Unit not defined.");
                        }
                    }
                    else//error
                    {
                        System.err.println("Line malformed: " + line);
                    }
                }
            }
        }
        else
        {

        }
    }

    /**
     * Returns the last point in the selected ArrayList.
     *
     * @param c
     * @return
     */
    private Cartesian getLastPoint(ArrayList<Cartesian> c)
    {
        if (c.size() > 0)
        {
            return c.get(c.size() - 1);
        }
        return null;
    }
    //</editor-fold>

}
