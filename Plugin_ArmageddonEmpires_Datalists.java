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
public class Plugin_ArmageddonEmpires_Datalists extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ArmageddonEmpires_Datalists() {

    super("ArmageddonEmpires_Datalists","ArmageddonEmpires_Datalists");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Armageddon Empires");
    setExtensions("txt");
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

      if (fm.getPath().indexOf("Datalists") > 0){
        rating += 25;
        }

      // first line
      if (fm.readString(14).equals("NumElements: [")){
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


      // First line - numFiles
      String firstLine = fm.readLine();
      int numFiles = Integer.parseInt(firstLine.substring(14,firstLine.length()-1));


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      for (int i=0;i<numFiles;i++){

        // Value
        String original = fm.readLine();
        if (original == null || original.length() <= 0 || original.equals("EOF")){
          continue;
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


      // first line - NumElements: [6]
      fm.writeString("NumElements: [" + numFiles + "]");
      fm.writeByte(13);
      fm.writeByte(10);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // Value
        fm.writeString(translated);
        fm.writeByte(13);
        fm.writeByte(10);

        WSProgressDialog.setValue(i);
        }


      // EOF
      fm.writeString("EOF");

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


  }
