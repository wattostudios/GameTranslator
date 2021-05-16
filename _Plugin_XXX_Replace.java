////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
//                                                                                            //
// This program is free software; you can redistribute it and/or modify it under the terms of //
// the GNU General Public License published by the Free Software Foundation; either version 2 //
// of the License, or (at your option) any later versions. This program is distributed in the //
// hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranties //
// of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License //
// at http://www.gnu.org for more details. For updates and information about this program, go //
// to the WATTO Studios website at http://www.watto.org or email watto@watto.org . Thanks! :) //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

import org.watto.Language;
import org.watto.Settings;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_XXX extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_XXX() {

    super("XXX","XXX");

    //           read write replace
    setProperties(true,false,true);
    setAllowsUnicode(false);

    setGames("");
    setExtensions(""); // MUST BE LOWER CASE
    setPlatforms("PC");

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMatchRating(FileManipulator fm) {
    try {

      int rating = 0;

      if (fm.getExtension().equals(extensions[0])){
        rating += 25;
        }

      // 8 - Unknown (8)
      if (fm.readLongL() == 8){
        rating += 5;
        }

      // 2 - null
      if (fm.readShortL() == 0){
        rating += 5;
        }

      fm.skip(33);

      // 1 - null
      if (fm.read() == 0){
        rating += 5;
        }

      // 4 - numFiles
      if (check.numFiles(fm.readIntL())){
        rating += 5;
        }

      return rating;

      }
    catch (Throwable t){
      return 0;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource[] read(File path) {
    try {

      // RESETTING THE GLOBAL VARIABLES
      setDefaultProperties(true);

      FileManipulator fm = new FileManipulator(path,"r");
      long arcSize = fm.getLength();

      // 8 - Unknown (8)
      // 2 - null
      fm.skip(10);

      // 34 - Filename (null)
      filename = fm.readNullString(34);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // skip over the offset information
      fm.skip(numFiles*4);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // 2 - ID
        int id = fm.readShortL();

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readNullString();

        //original
        resources[i] = new Resource(original);
        WSProgressDialog.setValue(i);
        }

      fm.close();

      return resources;

      }
    catch (Throwable t){
      logError(t);
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(Resource[] resources, File path) {
    try {

      FileManipulator fm = new FileManipulator(path,"rw");
      FileManipulator src = new FileManipulator(new File(Settings.getString("CurrentArchive")),"r");

      int numFiles = resources.length;
      WSProgressDialog.setMaximum(numFiles);


      // 4 - Unknown (-34,18,4,149)
      // 4 - null
      // 4 - Number Of Texts [*2]
      // 4 - Header Length (28)
      // 4 - Unknown (4444)
      fm.write(src.read(20));

      // 4 - Number Of Groups
      int numGroups = src.readIntL();
      fm.writeIntL(numGroups);

      // 4 - Group Start Offset
      src.skip(4);
      fm.writeIntL(28 + numFiles*8);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();

        // X - String Data
        fm.writeString(translated);

        // 1 - null String Data Terminator
        fm.writeByte(0);

        WSProgressDialog.setValue(i);
        }


      src.close();
      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[0];
    }
  }



  }
