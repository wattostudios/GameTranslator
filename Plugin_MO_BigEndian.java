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
public class Plugin_MO_BigEndian extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_MO_BigEndian() {

    super("MO_BigEndian","MO_BigEndian");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("GNU Language Packs");
    setExtensions("mo"); // MUST BE LOWER CASE
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

      // 4 - Header (149,4,18,222 for big endian order)
      int header1 = fm.readByteU();
      int header2 = fm.readByteU();
      int header3 = fm.readByteU();
      int header4 = fm.readByteU();
      if (header1 == 149 && header2 == 4 && header3 == 18 && header4 == 222){
        rating += 50;
        }

      // 4 - Version (0)
      if (fm.readIntB() == 0){
        rating += 5;
        }

      // 4 - Number Of Strings
      if (check.numFiles(fm.readIntB())){
        rating += 5;
        }

      long arcSize = fm.getLength();

      // 4 - Original Strings Directory Offset
      if (check.offset(fm.readIntB(),arcSize)){
        rating += 5;
        }

      // 4 - Translated Strings Directory Offset
      if (check.offset(fm.readIntB(),arcSize)){
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


      // 4 - Header (149,4,18,222 for big endian order)
      // 4 - Version (0)
      fm.skip(8);

      // 4 - Number Of Strings
      int numFiles = fm.readIntB();
      check.numFiles(numFiles);

      // 4 - Original Strings Directory Offset
      int originalDirOffset = fm.readIntB();
      check.offset(originalDirOffset,arcSize);

      // 4 - Translated Strings Directory Offset
      int translatedDirOffset = fm.readIntB();
      check.offset(translatedDirOffset,arcSize);

      // 4 - Number Of Hash Entries
      // 4 - Hash Directory Offset
      fm.skip(8);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      fm.seek(originalDirOffset);

      // read the original offsets
      int[] originalOffsets = new int[numFiles];
      int[] originalLengths = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Original String Length (not including null)
        int originalLength = fm.readIntB();
        check.length(originalLength,arcSize);
        originalLengths[i] = originalLength;

        // 4 - Original String Offset
        int originalOffset = fm.readIntB();
        check.offset(originalOffset,arcSize);
        originalOffsets[i] = originalOffset;
        }


      int[] translatedOffsets = null;
      int[] translatedLengths = null;

      if (translatedDirOffset != 0){
        fm.seek(translatedDirOffset);

        // read the translated offsets
        translatedOffsets = new int[numFiles];
        translatedLengths = new int[numFiles];
        for (int i=0;i<numFiles;i++){
          // 4 - translated String Length (not including null)
          int translatedLength = fm.readIntB();
          check.length(translatedLength,arcSize);
          translatedLengths[i] = translatedLength;

          // 4 - translated String Offset
          int translatedOffset = fm.readIntB();
          check.offset(translatedOffset,arcSize);
          translatedOffsets[i] = translatedOffset;
          }
        }



      // read the original texts
      for (int i=0;i<numFiles;i++){
        fm.seek(originalOffsets[i]);

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readString(originalLengths[i]);
        fm.skip(1); // skip 1 to optimise the reading, as this will move the file pointer
                    // to the same place as the next text offset, which means that the fm.seek
                    // doesn't need to move and re-read the buffer for each text!

        //original
        resources[i] = new Resource(original);

        WSProgressDialog.setValue(i);
        }


      // read the translated texts
      if (translatedDirOffset != 0){
        for (int i=0;i<numFiles;i++){
          fm.seek(translatedOffsets[i]);

          // X - Text
          // 1 - null Text Terminator
          String translated = fm.readString(translatedLengths[i]);
          fm.skip(1); // skip 1 to optimise the reading, as this will move the file pointer
                      // to the same place as the next text offset, which means that the fm.seek
                      // doesn't need to move and re-read the buffer for each text!

          //translated
          resources[i].setTranslated(translated);

          WSProgressDialog.setValue(i);
          }
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


      // 4 - Header (149,4,18,222 for big endian order)
      fm.writeByte((byte)149);
      fm.writeByte((byte)4);
      fm.writeByte((byte)18);
      fm.writeByte((byte)222);

      // 4 - Version (0)
      fm.writeIntB(0);

      // 4 - Number Of Strings
      fm.writeIntB(numFiles);

      // 4 - Original Strings Directory Offset
      fm.writeIntB(28);

      // 4 - Translated Strings Directory Offset
      fm.writeIntB(28 + (numFiles*8));

      // 4 - Number Of Hash Entries
      fm.writeIntB(0);

      // 4 - Hash Directory Offset
      fm.writeIntB(0);



      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      int offset = 28 + (numFiles*16);


      // write the originals directory
      for (int i=0;i<numFiles;i++){
        int length = resources[i].getOriginal().length();

        // 4 - Original String Length (not including null)
        fm.writeIntB(length);

        // 4 - Original String Offset
        fm.writeIntB(offset);

        offset += length + 1; // +1 null terminator
        }


      // write the translations directory
      for (int i=0;i<numFiles;i++){
        int length = resources[i].getTranslatedLength();

        // 4 - Translated String Length (not including null)
        fm.writeIntB(length);

        // 4 - Translated String Offset
        fm.writeIntB(offset);

        offset += length + 1; // +1 null terminator
        }




      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the original strings
      for (int i=0;i<numFiles;i++){
        String original = resources[i].getOriginal();

        // X - String Data
        fm.writeString(original);

        // 1 - null String Data Terminator
        fm.writeByte(0);

        WSProgressDialog.setValue(i);
        }


      // Write the translated strings
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();

        // X - String Data
        fm.writeString(translated);

        // 1 - null String Data Terminator
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

