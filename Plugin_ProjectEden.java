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
public class Plugin_ProjectEden extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ProjectEden() {

    super("ProjectEden","ProjectEden");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Project Eden");
    setExtensions("edt"); // MUST BE LOWER CASE
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

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // 4 - Text Length
        int textLength = fm.readIntL();
        check.length(textLength,arcSize);

        // X - Text
        String original = fm.readString(textLength);

        // 4 - Padding (-1)
        fm.skip(4);

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


      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int length = translated.length();

        // 4 - Text Length
        fm.writeIntL(length);

        // X - Text
        fm.writeString(translated);

        // 4 - Padding (-1)
        fm.writeIntL(-1);

        WSProgressDialog.setValue(i);
        }

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


  }
