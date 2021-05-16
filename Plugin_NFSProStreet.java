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
import org.watto.Settings;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_NFSProStreet extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_NFSProStreet() {

    super("NFSProStreet","NFSProStreet");

    //           read write replace
    setProperties(true,false,true);
    setAllowsUnicode(false);

    setGames("Need For Speed: Pro Street");
    setExtensions("bin"); // MUST BE LOWER CASE
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

      // 4 - Unknown (233472)
      if (fm.readIntL() == 233472){
        rating += 50;
        }

      // 4 - Archive Length [+8]
      if (fm.readIntL()+8 == fm.length()){
        rating += 5;
        }

      // 4 - numFiles
      if (check.numFiles(fm.readIntL())){
        rating += 5;
        }

      // 4 - HeaderLength (28)
      if (fm.readIntL() == 28){
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


      // 4 - Unknown (233472)
      // 4 - Archive Length [+8]
      fm.skip(8);

      // 4 - Number Of Texts
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      // 4 - Directory Header Length (28)
      // 4 - Directory Length (including all these header fields)
      // 16 - Group Name (null terminated)
      fm.skip(24);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      int relOffset = 28 + 8 + numFiles*8;

      // read the resources
      int[] offsets = new int[numFiles];
      for (int i=0;i<numFiles;i++){

        // 4 - Hash
        fm.skip(4);

        // 4 - Text Offset (relative to the start of the text data)
        int offset = fm.readIntL() + relOffset;
        check.offset(offset,arcSize);
        offsets[i] = offset;
        }


      for (int i=0;i<numFiles;i++){
        fm.seek(offsets[i]);

        // X - Text
        // 1 - null Text Terminator
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
      FileManipulator src = new FileManipulator(new File(Settings.getString("CurrentArchive")),"r");

      int numFiles = resources.length;
      WSProgressDialog.setMaximum(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));


      int arcSize = 28 + numFiles*8;
      for (int i=0;i<numFiles;i++){
        arcSize += resources[i].getTranslatedLength()+1;
        }


      // 4 - Unknown (233472)
      fm.write(src.read(4));

      // 4 - Archive Length [+8]
      src.skip(4);
      fm.writeIntL(arcSize);

      // 4 - Number Of Texts
      // 4 - Directory Header Length (28)
      // 4 - Directory Length (including all these header fields)
      // 16 - Group Name (null terminated)
      fm.write(src.read(28));


      // Directory
      int offset = 0;
      for (int i=0;i<numFiles;i++){
        // 4 - Hash
        fm.write(src.read(4));

        // 4 - Text Offset (relative to the start of the text data)
        src.skip(4);
        fm.writeIntL(offset);

        offset += resources[i].getTranslatedLength()+1;
        }



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();

        // X - String Data
        fm.writeString(translated);

        // 1 - null String Data Terminator
        fm.writeByte(0);

        WSProgressDialog.setValue(i);
        }


      src.close();
      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }



  }
