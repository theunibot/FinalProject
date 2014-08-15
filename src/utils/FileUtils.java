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
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Ini;

/**
 *
 */
public class FileUtils
{

    public static final String COMMAND_FILE_COMMENT = "//";
    public static final String COMMAND_FILE_METADATA_PREFIX = "#";
    public static final String COMMAND_FILE_REFERENCE_POINT = ">";
    
    /**
     * Reads an INI file and parses out each value, placing it in the Map. Generates a file if none is found.
     * @param pathToFile path the the INI file to read
     * @param sectionKey section under which the keys are kept
     * @param keys keys of which the values are desired
     * @param genContent content to write to a generated content
     * @return Map of key,value. Never null.
     */
    public static Map<String, String> readINIFileOrGenerate(String pathToFile, String sectionKey, String[] keys, String genContent)
    {
        Map<String, String> map = readINIFile(pathToFile, sectionKey, keys);
        if (map == null)
        {
            FileUtils.createFile(pathToFile, genContent);
            map = new HashMap<>();
        }
        return map;        
    }

    /**
     * Reads an INI file and parses out each value, placing it in the Map
     *
     * @param pathToFile path the the INI file to read
     * @param sectionKey section under which the keys are kept
     * @param keys keys of which the values are desired
     * @return Map of key,value. May be null.
     */
    public static Map<String, String> readINIFile(String pathToFile, String sectionKey, String[] keys)
    {
        Map<String, String> map = null;
        File file = new File(pathToFile);
        if (file.exists())
        {
            map = new HashMap<>();
            try
            {
                System.out.println("INI File found");
                Ini ini = new Ini(file);
                for (String key : keys)
                {
                    String value = ini.get(sectionKey, key);
                    map.put(key, value);
                }

            }
            catch (IOException ex)
            {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }

    /**
     * Reads the file, but then drops all lines beginning with a comment. If
     * file is not found, one is generated with the content given.
     *
     * @param filePath Path to the file to read
     * @return ArrayList<String> of all non-comment lines of the file or an
     * empty ArrayList if ArrayList from read is null or file is generated.
     */
    public static ArrayList<String> readCommandFileOrGenEmpty(String filePath, String contents)
    {
        if (FileUtils.fileExists(filePath))
        {

            //if command file exists, read all the commands and write them out to the ArrayList
            ArrayList<String> arrayList = readCommandFile(filePath);
            if (arrayList != null)
            {
                return arrayList;
            }
            else
            {
                System.err.println("File returned null! No commands found!.");
                return new ArrayList<>();
            }
        }
        else
        {
            System.out.println("Generating New File");
            FileUtils.createFile(filePath, contents);
            return new ArrayList<>();
        }
    }

    /**
     * Reads the file, but then drops all lines beginning with a comment.
     *
     * @param filePath Path to the file to read
     * @return ArrayList<String> of all non-comment lines of the file
     */
    public static ArrayList<String> readCommandFile(String filePath)
    {
        ArrayList<String> arrayList = readFile(filePath);

        //loop that removes all elements in the ArrayList 
        //that start with the comment
        int i = 0;
        while (i < arrayList.size())
        {
            String s = arrayList.get(i);
            if ( (s.startsWith(COMMAND_FILE_COMMENT)) || (s.trim().length() == 0) )
            {
                arrayList.remove(i);
            }
            else
            {
                i++;
            }
        }
        return arrayList;
    }

    /**
     * Reads a file and returns its raw output, sans linebreaks
     *
     * @param filePath String of the path to the file
     * @return ArrayList<String> containing each line in order
     */
    public static ArrayList<String> readFile(String filePath)
    {
        BufferedReader br = null;

        ArrayList<String> fileLines = new ArrayList<String>();
        File f = new File(filePath);
        if (!f.exists())
        {
            System.err.println("File not found!");
            return null;
        }
        try
        {
            String sCurrentLine = null;
            br = new BufferedReader(new FileReader(filePath));
            while ((sCurrentLine = br.readLine()) != null)
            {
                //adds the line and trims it to remove excess whitespace
                fileLines.add(sCurrentLine.trim());
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("File Not Found Exception!");
            return null;
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("File Read Failed Exception!");
            return null;
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileLines;
    }

    /**
     * Creates a file at the given location and writes the contents string to
     * the file.
     *
     * @param fileString String of the path to the file to be created.
     * @param contents String of the content to be written to the file.
     * @return boolean value of success
     */
    public static boolean createFile(String fileString, String contents)
    {
        createFile(fileString);
        FileWriter fw = null;
        try
        {

            File file = new File(fileString);
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contents);
            bw.close();
            return true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;//something bad happened
    }

    /**
     * Creates a file at the given location
     *
     * @param fileString String of the path to the file to be created.
     * @return boolean value of success
     */
    public static boolean createFile(String fileString)
    {
        File f = new File(fileString);

        try
        {
            if (!f.exists() && f.getParentFile().mkdirs())//if the file doesn't already exist and all parent dirs made successfully
            {
                return f.createNewFile();//returns if the creation of the file was successful.
            }
            else
            {
                return false;//file exists, cannot make or failed to make parent dirs
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;//something bad happens, return fail
        }
    }

    public static boolean fileExists(String fileString)
    {

        return new File(fileString).exists();
    }

    public static String getFilesFolderString()
    {
        return System.getProperty("user.dir") + File.separator + "robotFiles" + File.separator;
    }

}
