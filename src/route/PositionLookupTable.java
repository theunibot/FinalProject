/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package route;

import enums.CabinetType;
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
     * Converts the given cabinet and shelf to a Cartesian coord.
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
        else if ( (su == CabinetType.CPL) || (su == CabinetType.CPR) || (su == CabinetType.CPM) )
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
    public static Cartesian homeCartesian() {
        return PositionLookupTable.getInstance().shelfToCartesian(CabinetType.HOME, 0);
    }

    //<editor-fold defaultstate="collapsed" desc="Parse File Stuff">
    private void parseFile()
    {
        ArrayList<String> lines = FileUtils.readCommandFileOrGenEmpty(FileUtils.getFilesFolderString() + FILE_NAME, FILE_CONTENTS);
        if (lines != null)
        {
            CabinetType su = null;
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

                        if (Utils.stringToEnumShelfType(piece) == CabinetType.D1)
                        {
                            su = CabinetType.D1;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.D2)
                        {
                            su = CabinetType.D2;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPL)
                        {
                            su = CabinetType.CPL;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPM)
                        {
                            su = CabinetType.CPM;
                        }
                        else if (Utils.stringToEnumShelfType(piece) == CabinetType.CPR)
                        {
                            su = CabinetType.CPR;
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
                            if ( (su == CabinetType.CPL) || (su == CabinetType.CPM) || (su == CabinetType.CPR) )
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
                            else if (su == CabinetType.D1)
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
                            else if (su == CabinetType.D2)
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
                            if ( (su == CabinetType.CPL) || (su == CabinetType.CPM) || (su == CabinetType.CPR) )
                            {
                                cpPos.add(new Cartesian(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5]));
                            }
                            else if (su == CabinetType.D1)
                            {
                                d1Pos.add(new Cartesian(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5]));
                            }
                            else if (su == CabinetType.D2)
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