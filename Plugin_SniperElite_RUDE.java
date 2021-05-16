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
public class Plugin_SniperElite_RUDE extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SniperElite_RUDE() {

    super("SniperElite_RUDE","SniperElite_RUDE");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Sniper Elite");
    setExtensions("asr");
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

      // Header
      if (fm.readString(12).equals("Asura   RUDE")){
        rating += 50;
        }

      long arcSize = fm.getLength();

      // Archive Size
      if (fm.readIntL()+24 == arcSize){
        rating += 5;
        }

      // Version
      if (fm.readIntL() == 1){
        rating += 5;
        }

      fm.skip(4);

      // Number Of Text Strings
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

      // 8 - Header 1 (Asura   )
      // 4 - Header 2 (RUDE)
      // 4 - Archive Length [+24]
      // 8 - Version (1)
      fm.skip(24);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      Resource[] resources = new Resource[numFiles];

      WSProgressDialog.setMaximum(numFiles);

      // Loop through directory
      for(int i=0;i<numFiles;i++){
        // X - Text String
        // 1 - null Text String Terminator
        String original = fm.readNullString();

        long length = original.length();

        // 0-3 - null Padding to a multiple of 4 bytes
        long padding = 4 - ((length+1)%4);
        if (padding < 4){
          fm.skip(padding);
          }

        //path,id,name,offset,length,decompLength,exporter
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


      int archiveSize = 20;
      for (int i=0;i<numFiles;i++){
        int length = resources[i].getTranslatedLength();
        int padding = 4 - (length%4);
        if (padding == 4){
          padding = 0;
          }
        archiveSize += length + padding;
        }



      // 8 - Header 1 (Asura   )
      // 4 - Header 2 (RUDE)
      fm.writeString("Asura   RUDE");

      // 4 - Archive Length [+24]
      fm.writeIntL((int)archiveSize);

      // 8 - Version (1)
      fm.writeLongL(1);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // X - Text String
        // 1 - null Text String Terminator
        fm.writeString(translated);
        fm.writeByte(0);

        // 0-3 - null Padding to a multiple of 4 bytes
        long padding = 4 - ((translated.length()+1)%4);
        if (padding != 4){
          for (int p=0;p<padding;p++){
            fm.writeByte(0);
            }
          }

        WSProgressDialog.setValue(i);
        }


      // 16 - null
      for (int i=0;i<16;i++){
        fm.writeByte(0);
        }


      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


  }
