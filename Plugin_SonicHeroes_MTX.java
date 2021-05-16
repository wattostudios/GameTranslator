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
import org.watto.manipulator.DataConverter;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_SonicHeroes_MTX extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SonicHeroes_MTX() {

    super("SonicHeroes_MTX","SonicHeroes_MTX");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    // disabled - can't quite figure out how it knows whether a byte is unicode or not
    setEnabled(false);

    setGames("Sonic Heroes");
    setExtensions("mtx"); // MUST BE LOWER CASE
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

      // 4 - Archive Length
      if (fm.readIntL() == fm.length()){
        rating += 5;
        }

      // 4 - Unknown (8)
      if (fm.readIntL() == 8){
        rating += 5;
        }

      // 4 - Unknown (12)
      if (fm.readIntL() == 12){
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

      // 4 - Archive Length
      // 4 - Unknown (8)
      // 4 - Unknown (12)
      fm.skip(12);


      // 4 - Number Of Text Strings
      int numFiles = (fm.readIntL()-12)/4;
      check.numFiles(numFiles);


      fm.seek(0); // this optimises the buffer reading
      fm.skip(12);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      int[] offsets = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Text Offset
        int offset = fm.readIntL();
        check.offset(offset,arcSize);
        offsets[i] = offset;
        }


      // read the resources
      for (int i=0;i<numFiles;i++){
        fm.seek(offsets[i]);

        String original = new String(new byte[0],"UTF-16LE");

        // X - Text
        int byteVal = fm.readByteU();
        int byteVal2 = fm.readByteU();
        while (byteVal != 0){
          if (byteVal == 131){
            // unicode
            byteVal &= 126;

            original += DataConverter.toChar(new byte[]{(byte)byteVal2,(byte)byteVal});

            byteVal = fm.readByteU();
            byteVal2 = fm.readByteU();
            }
          else {
            // ASCII
            original += (char)byteVal;
            byteVal = byteVal2;
            byteVal2 = fm.readByteU();
            }

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


      int arcSize = 12 + numFiles*4;
      for (int i=0;i<numFiles;i++){
        arcSize += resources[i].getTranslatedLength()*2 + 2; // *2 unicode, +2 terminator
        }


      // 4 - Archive Length
      fm.writeIntL(arcSize);

      // 4 - Unknown (8)
      fm.writeIntL(8);

      // 4 - Unknown (12)
      fm.writeIntL(12);



      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the offsets
      int offset = 12 + numFiles*4;
      for (int i=0;i<numFiles;i++){
        // 4 - Text Offset
        fm.writeIntL(offset);

        offset += resources[i].getTranslatedLength()*2 + 2; // *2 unicode, +2 terminator
        }



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();


        // X - String (unicode)
        byte[] bytes = DataConverter.toByteArray(DataConverter.toCharArray(translated));

        for (int b=0;b<bytes.length;b+=2){
          if ((bytes[b]&128)==0){
            bytes[b+1] = bytes[b];
            bytes[b] = (byte)128;
            }
          }

        fm.write(bytes);


        // 1 - Terminator
        fm.writeByte(0);
        fm.writeByte(204);

        WSProgressDialog.setValue(i);
        }

      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


  }
