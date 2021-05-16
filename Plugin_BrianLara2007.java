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
public class Plugin_BrianLara2007 extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_BrianLara2007() {

    super("BrianLara2007","BrianLara2007");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Brian Lara International Cricket 2007");
    setExtensions("dat");
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

      // 16 - Header (GameTextCreator + null)
      if (fm.readString(16).equals("GameTextCreator" + (byte)0)){
        rating += 50;
        }

      // 4 - Number Of Texts
      if (check.numFiles(fm.readIntL())){
        rating += 5;
        }


      // 4 - null
      if (fm.readLongL() == 0){
        rating += 5;
        }
      // 4 - null
      if (fm.readLongL() == 0){
        rating += 5;
        }
      // 4 - null
      if (fm.readLongL() == 0){
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


      // 16 - Header (GameTextCreator + null)
      fm.skip(16);

      // 4 - Number Of Texts
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // skip over the nulls
      fm.skip(numFiles*4);

      // read the resources
      for (int i=0;i<numFiles;i++){

        // X - Text String
        // 1 - Text String Terminator
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
      int numFiles = resources.length;

      WSProgressDialog.setMaximum(numFiles);


      // 16 - Header (GameTextCreator + null)
      fm.writeString("GameTextCreator");
      fm.writeByte(0);

      // 4 - Number Of Texts
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the nulls
      for (int i=0;i<numFiles;i++){
        // 4 - null
        fm.writeIntL(0);
        }


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // X - Text String
        fm.writeString(translated);

        // 1 - Text String Terminator
        fm.writeByte(0);

        WSProgressDialog.setValue(i);
        }

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }



  }
