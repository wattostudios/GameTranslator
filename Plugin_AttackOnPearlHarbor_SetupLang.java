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
public class Plugin_AttackOnPearlHarbor_SetupLang extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_AttackOnPearlHarbor_SetupLang() {

    super("AttackOnPearlHarbor_SetupLang","AttackOnPearlHarbor_SetupLang");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Attack On Pearl Harbor");
    setExtensions("ini");
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

      if (fm.getFile().getName().equalsIgnoreCase("setuplang.ini")){
        rating += 25;
        }
      else {
        rating = 0;
        }

      if (fm.readByteU() == 255 && fm.readByteU() == 254){
        rating += 5;
        }

      if (fm.readUnicodeString(5).equals("*****")){
        rating += 5;
        }

      if (fm.readByte() == 13 && fm.readByte() == 0 && fm.readByte() == 13 && fm.readByte() == 0){
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

      // 2 - Unicode Header (255,254)
      // 10 - Header (*****) (UNICODE)
      // 4 - New Line (13,0,10,0)
      fm.skip(16);


      int numFiles = Archive.getMaxFiles();


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;

      while (fm.getFilePointer() < arcSize){
        String line = fm.readUnicodeLine();

        //original
        resources[realNumFiles] = new Resource(line);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;
        }

      resources = resizeResources(resources,realNumFiles);

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



      // 2 - Unicode Header (255,254)
      fm.writeByte(255);
      fm.writeByte(254);

      // 10 - Header (*****) (UNICODE)
      fm.writeUnicodeString("*****");

      // 4 - New Line (13,0,10,0)
      fm.writeByte(13);
      fm.writeByte(0);
      fm.writeByte(10);
      fm.writeByte(0);



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();

        // X - Text String (UNICODE)
        fm.writeUnicodeString(translated);

        // 4 - New Line (13,0,10,0)
        fm.writeByte(13);
        fm.writeByte(0);
        fm.writeByte(10);
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
