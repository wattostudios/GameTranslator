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
public class Plugin_AgathaChristieATTWN extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_AgathaChristieATTWN() {

    super("AgathaChristieATTWN","AgathaChristieATTWN");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Agatha Christie: And Then There Were None");
    setExtensions("act");
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

      // 2 - numFiles
      if (check.numFiles((short)fm.readShortL())){
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

      // 2 - Number Of Text Strings
      int numFiles = fm.readShortL();
      check.numFiles(numFiles);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      for (int i=0;i<numFiles;i++){
        // 2 - Text Length
        short length = (short)(fm.readShortL() - 1);
        check.length(length,arcSize);

        // X - Text String (XORed with byte 48, terminated with byte 10)
        byte[] originalBytes = fm.read(length);
        fm.skip(1); // terminator

        // do the XOR
        byte byte48 = (byte)48;
        for (int x=0;x<length;x++){
          originalBytes[x] ^= byte48;
          }

        String original = new String(originalBytes);

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


      // 2 - Number Of Text Strings
      fm.writeShortL((short)(numFiles));


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // write the lengths
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();

        // 2 - Text Length
        fm.writeShortL((short)(translated.length() + 1)); //+1 terminator

        byte[] translatedBytes = translated.getBytes();
        int length = translatedBytes.length;

        // do the XOR
        byte byte48 = (byte)48;
        for (int x=0;x<length;x++){
          translatedBytes[x] ^= byte48;
          }


        // X - Text String (XORed with byte 48, terminated with byte 10)
        fm.write(translatedBytes);
        fm.writeByte((byte)58); // terminator

        WSProgressDialog.setValue(i);
        }

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }



  }
