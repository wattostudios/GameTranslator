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
public class Plugin_XXX extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_XXX() {

    super("XXX","XXX");

    //           read write replace
    setProperties(true,false,false);
    setAllowsUnicode(false);

    setGames("");
    setExtensions(""); // MUST BE LOWER CASE
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

      // 8 - Unknown (8)
      if (fm.readLongL() == 8){
        rating += 5;
        }

      // 2 - null
      if (fm.readShortL() == 0){
        rating += 5;
        }

      fm.skip(33);

      // 1 - null
      if (fm.read() == 0){
        rating += 5;
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

      // 8 - Unknown (8)
      // 2 - null
      fm.skip(10);

      // 34 - Filename (null)
      filename = fm.readNullString(34);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);



      Resource_Property[] properties = new Resource_Property[5];
      properties[0] = new Resource_Property(langName,"Language Name");
      properties[1] = new Resource_Property(langVersion,"Version");
      properties[2] = new Resource_Property(langAuthorName,"Author Name");
      properties[3] = new Resource_Property(langAuthorEmail,"Email Address");
      properties[4] = new Resource_Property(langAuthorWebsite,"Website");
      setProperties(properties);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // skip over the offset information
      fm.skip(numFiles*4);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // 2 - ID
        int id = fm.readShortL();

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
      int numFiles = resources.length;

      WSProgressDialog.setMaximum(numFiles);


      // 8 - Unknown (8)
      fm.writeLongL(8);

      // 2 - null
      fm.writeShortL((short)0);

      // 34 - Filename (null)
      if (filename.equals("")){
        filename = path.getName();
        }
      fm.writeNullString(filename,34);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the offsets
      int offset = 48 + numFiles*4;
      for (int i=0;i<numFiles;i++){
        // 4 - String Offset
        fm.writeIntL(offset);

        offset += resources[i].getTranslatedLength() + 3; // +1 for the null terminator, +2 for the ID
        }



      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Name")){
          nameNode.setContent(translated);
          }
        else if (code.equals("Version")){
          versionNode.setContent(translated);
          }
        else if (code.equals("Author Name")){
          authorNode.addChild(new XMLNode("name",translated));
          }
        else if (code.equals("Email Address")){
          authorNode.addChild(new XMLNode("email",translated));
          }
        else if (code.equals("Website")){
          authorNode.addChild(new XMLNode("website",translated));
          }
        }



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // 2 - String ID
        fm.writeShortL((short)id);

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



/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[0];
    }
  }


  }
