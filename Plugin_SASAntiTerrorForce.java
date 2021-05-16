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
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_SASAntiTerrorForce extends ArchivePlugin {

  String filename = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SASAntiTerrorForce() {

    super("SASAntiTerrorForce","SASAntiTerrorForce");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("SAS Anti-Terror Force");
    setExtensions("");
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

      // 4 - Header (LANG)
      if (fm.readString(4).equals("LANG")){
        rating += 50;
        }

      // 4 - Unknown (2)
      if (fm.readIntL() == 4){
        rating += 5;
        }

      // 4 - Number Of Language Strings
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

      // 4 - Header (LANG)
      // 4 - Unknown (2)
      fm.skip(8);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // skip over the offset information
      fm.skip(numFiles*4);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // X - String Data
        // 1 - null String Data Terminator
        String original = fm.readNullString();

        // 0-1 - null Padding to a multiple of 2 bytes
        if ((original.length() + 1)%2 == 1){
          fm.skip(1);
          }

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
      int numFiles = resources.length;

      WSProgressDialog.setMaximum(numFiles);


      // 4 - Header (LANG)
      fm.writeString("LANG");

      // 4 - Unknown (2)
      fm.writeIntL(2);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the offsets
      int offset = 0;
      for (int i=0;i<numFiles;i++){
        // 4 - String Offset (relative to the start of the string data)
        fm.writeIntL(offset);

        int lineLength = resources[i].getTranslatedLength() + 1; // +1 for the null terminator
        offset += lineLength;

        // padding to a multiple of 2 bytes
        if (lineLength%2 == 1){
          offset += 1;
          }
        }


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // X - String Data
        fm.writeString(translated);

        // 1 - null String Data Terminator
        fm.writeByte(0);

        // 0-1 - null Padding to a multiple of 2 bytes
        if ((translated.length() + 1)%2 == 1){
          fm.writeByte(0);
          }

        WSProgressDialog.setValue(i);
        }

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


  }
