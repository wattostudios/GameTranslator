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
public class Plugin_SniperElite_LTXT extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SniperElite_LTXT() {

    super("SniperElite_LTXT","SniperElite_LTXT");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

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
      if (fm.readString(12).equals("Asura   LTXT")){
        rating += 50;
        }

      long arcSize = fm.getLength();

      // Archive Size
      if (fm.readIntL()+24 == arcSize){
        rating += 5;
        }

      // Version
      if (fm.readIntL() == 0){
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
      // 4 - Header 2 (LTXT)
      // 4 - Archive Length [+24]
      // 8 - Version (0)
      fm.skip(24);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      Resource[] resources = new Resource[numFiles];

      WSProgressDialog.setMaximum(numFiles);

      // Loop through directory
      for(int i=0;i<numFiles;i++){

        // 4 - String Length [*2 for unicode] (including null terminators)
        long length = fm.readIntL() - 1;
        check.length(length,arcSize);

        // X - Text String (unicode text)
        String original = fm.readUnicodeString((int)length);

        // 2 - null Filename Terminator
        fm.skip(2);

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


      int archiveSize = 20 + (numFiles*6);
      for (int i=0;i<numFiles;i++){
        archiveSize += resources[i].getTranslatedLength();
        }



      // 8 - Header 1 (Asura   )
      // 4 - Header 2 (LTXT)
      fm.writeString("Asura   LTXT");

      // 4 - Archive Length [+24]
      fm.writeIntL((int)archiveSize);

      // 8 - Version (0)
      fm.writeLongL(0);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // 4 - String Length [*2 for unicode] (including null terminators)
        fm.writeIntL(translated.length()+1);

        // X - Text String (unicode text)
        // 2 - null Filename Terminator
        fm.writeUnicodeString(translated);
        fm.writeShortL((short)0);

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
