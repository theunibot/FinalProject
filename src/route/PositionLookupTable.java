/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package route;

import enums.ShelfType;
import java.util.ArrayList;
import utils.FileUtils;
import utils.Utils;

/**
 *
 * @author kyle
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
            + "\n//Inside this file, the XYZ position of each slot on the CachePoint"
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
        d1Pos = new ArrayList<>();
        d2Pos = new ArrayList<>();
        cpPos = new ArrayList<>();
        parseFile();
        if (main.Main.DEBUG)
        {
            System.out.println("Position Lookup Table Initialized.");
        }
    }

    /**
     * Converts the given slot and shelf unit to a Cartesian coord.
     * @param su
     * @param slot
     * @return 
     */
    public Cartesian slotToCartesian(ShelfType su, int slot)
    {
        int index = Utils.slotToIndex(slot);
        if (su == ShelfType.D1)
        {
            if (index > 0 && index < d1Pos.size())
            {
                return d1Pos.get(index);
            }
            else
            {
                System.err.println("Slot " + index + " not in D1");
                return null;
            }
        }
        else if (su == ShelfType.D2)
        {

            if (index > 0 && index < d2Pos.size())
            {
                return d2Pos.get(index);
            }
            else
            {
                System.err.println("Slot " + index + " not in D2");
                return null;
            }
        }
        else if (su == ShelfType.CP)
        {
            if (index > 0 && index < cpPos.size())
            {
                return cpPos.get(index);
            }
            else
            {
                System.err.println("Slot " + index + " not in CP");
                return null;
            }
        }
        else
        {
            System.err.println("Shelf Unit unknown");
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Parse File Stuff">
    private void parseFile()
    {
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + FILE_NAME, FILE_CONTENTS);
        if (lines != null)
        {
            ShelfType su = null;
            for (String line : lines)
            {
                String[] pieces = line.split(" ");
                if (line.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))//metadata
                {
                    for (String piece : pieces)
                    {
                        //remove any comment piece
                        if (piece.startsWith(FileUtils.COMMAND_FILE_METADATA_PREFIX))
                        {
                            piece = piece.replace(FileUtils.COMMAND_FILE_METADATA_PREFIX, "");
                        }

                        if (Utils.stringToEnumShelfType(piece) == ShelfType.D1)
                        {
                            su = ShelfType.D1;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == ShelfType.D2)
                        {
                            su = ShelfType.D2;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == ShelfType.CP)
                        {
                            su = ShelfType.CP;
                        }
                    }
                }
                else//type is position X,Y,Z,Pitch,Yaw,Roll
                {
                    if (pieces.length == 3)//line num, XYZ
                    {
                        if (su != null)
                        {
                            Cartesian prevCart;
                            if (su == ShelfType.CP)
                            {
                                if ((prevCart = getLastPoint(cpPos)) != null)
                                {
                                    cpPos.add(new Cartesian(pieces[0], pieces[1], pieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr()));
                                }
                                else
                                {
                                    cpPos.add(new Cartesian(pieces[0], pieces[1], pieces[2], "0", "0", "0"));
                                }
                            }
                            else if (su == ShelfType.D1)
                            {
                                if ((prevCart = getLastPoint(d1Pos)) != null)
                                {
                                    d1Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr()));
                                }
                                else
                                {
                                    d1Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], "0", "0", "0"));
                                }
                            }
                            else if (su == ShelfType.D2)
                            {
                                if ((prevCart = getLastPoint(d2Pos)) != null)
                                {
                                    d2Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], prevCart.getPitchStr(), prevCart.getYawStr(), prevCart.getRollStr()));
                                }
                                else
                                {
                                    d2Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], "0", "0", "0"));
                                }
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
                    else if (pieces.length == 6)//line num, XYZPYR
                    {
                        if (su != null)
                        {
                            if (su == ShelfType.CP)
                            {
                                cpPos.add(new Cartesian(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5]));
                            }
                            else if (su == ShelfType.D1)
                            {
                                d1Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5]));
                            }
                            else if (su == ShelfType.D2)
                            {
                                d2Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5]));
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