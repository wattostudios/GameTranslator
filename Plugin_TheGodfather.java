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
public class Plugin_TheGodfather extends ArchivePlugin {

  String filename = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_TheGodfather() {

    super("TheGodfather","The Godfather");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("The Godfather");
    setExtensions("str");
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

      // 4 - Header (coTS)
      if (fm.readString(4).equals("coTS")){
        rating += 50;
        }

      // 4 - Version? (6)
      if (fm.readIntL() == 6){
        rating += 5;
        }

      // 4 - Unknown
      fm.skip(4);

      // 4 - null
      if (fm.readIntL() == 0){
        rating += 5;
        }
      // 4 - Unknown (20)
      if (fm.readIntL() == 20){
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


      // 4 - Header (coTS)
      // 4 - Version? (6)
      // 4 - Unknown
      // 4 - null
      // 4 - Unknown (20)
      // 8 - CRC?
      // 4 - Archive Length [+3012]
      // 4 - Archive Length [+2048]
      // 4 - Archive Length [+2048]
      // 2008 - null Padding to offset 2048

      // 4 - Unknown
      // 4 - Archive Length [+3024]
      // 4 - Unknown
      check.offset(2060,arcSize);
      fm.seek(2060);

      // 4 - File Offset (relative to the end of this field) (128)
      int fileOffset = (int)(fm.readIntL() + fm.getFilePointer());
      check.offset(fileOffset,arcSize);

      // 4 - Unknown (16/20)
      int filenameLength = 16 + fm.readIntL();
      check.filenameLength(filenameLength);

      // 32/36 - Filename (null-terminated, filled with junk)
      String filename = fm.readNullString(filenameLength);

      // 4 - Extension Length (including null) (4)
      fm.skip(4);

      // 4 - File Extension (LH2 + null)
      String extension = fm.readNullString(4);

      // 4 - File Path Length (including nulls/padding to a multiple of 4 bytes)
      fm.skip(4);

      // X - File Path (including x:\ etc.)
      String filePath = fm.readNullString();

      // 1 - null File Path Terminator
      // 0-3 - Junk Padding to a multiple of 4 bytes
      // 4 - Unknown (4)
      // 4 - Unknown
      // 4 - null
      // 4 - File Length (including everything in File Data except the padding)
      // 8 - null
      fm.seek(fileOffset);

      // 4 - Header (LCH2)
      // 4 - File Length (including everything in File Data except the padding)
      // 4 - Unknown (1)
      // 4 - null
      fm.skip(16);

      // 4 - Number Of Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      // 4 - Unknown (1)
      // 8 - null
      fm.skip(12);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // add the archive properties
      Resource_Property[] properties = new Resource_Property[3];
      properties[0] = new Resource_Property(filename,"Filename");
      properties[1] = new Resource_Property(extension,"Extension");
      properties[2] = new Resource_Property(filePath,"File Path");
      setProperties(properties);


      // Read the hashes
      int[] hashes = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Hash?
        hashes[i] = fm.readIntL();
        }


      // Read the offsets
      int[] offsets = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Offset (relative to the start of the file data)
        int offset = fm.readIntL() + fileOffset;
        check.offset(offset,arcSize);
        offsets[i] = offset;
        }


      // write the strings
      for (int i=0;i<numFiles;i++){
        fm.seek(offsets[i]);

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readNullString();

        //original,id
        resources[i] = new Resource_TheGodfather(original,hashes[i]);
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


      // get the archive properties
      String filename = "";
      String extension = "";
      String filePath = "";
      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Filename")){
          filename = translated;
          }
        else if (code.equals("Extension")){
          extension = translated;
          }
        else if (code.equals("File Path")){
          filePath = translated;
          }
        }




      // calculate the archive length
      int headerLength = 88;


      int pathLength = filePath.length() + 1;
      int paddingSize = 4-(pathLength%4);
      if (paddingSize != 4){
        pathLength += paddingSize;
        }
      headerLength += pathLength;

      int dataLength = 32 + numFiles*8;
      for (int i=4;i<numFiles;i+=2){
        dataLength += resources[i].getTranslatedLength() + 1;
        }

      int paddingLength = 2048 - (dataLength%2048);
      if (paddingLength == 2048){
        paddingLength = 0;
        }

      int archiveLength = headerLength + dataLength + paddingLength;


      // 4 - Header (coTS)
      fm.writeString("coTS");

      // 4 - Version? (6)
      fm.writeIntL(6);

      // 4 - Unknown (769)
      fm.writeIntL(769);

      // 4 - null
      fm.writeIntL(0);

      // 4 - Unknown (20)
      fm.writeIntL(20);

      // 8 - Unknown Constant (1,172,147,83,200,21,172,14)
      fm.write(new byte[]{(byte)1,(byte)172,(byte)147,(byte)83,(byte)200,(byte)21,(byte)172,(byte)14});

      // 4 - Archive Length [+3012]
      fm.writeIntL(archiveLength-964);

      // 4 - Archive Length [+2048]
      fm.writeIntL(archiveLength);

      // 4 - Archive Length [+2048]
      fm.writeIntL(archiveLength);

      // 2008 - null Padding to offset 2048
      for (int i=0;i<2008;i++){
        fm.writeByte(0);
        }


      // 4 - Unknown (1814)
      fm.writeIntL(1814);

      // 4 - Archive Length [+3024]
      fm.writeIntL(archiveLength-976);

      // 4 - Unknown (402849791)
      fm.writeIntL(402849791);

      // 4 - File Offset (relative to the end of this field) (128)
      fm.writeIntL(headerLength-16);

      // 4 - Unknown (16)
      fm.writeIntL(16);

      // 32 - Filename (null-terminated, filled with junk)
      fm.writeNullString(filename,32);

      // 4 - Extension Length (including null) (4)
      fm.writeIntL(4);

      // 4 - File Extension (LH2 + null)
      fm.writeNullString(extension,4);

      // 4 - File Path Length (including nulls/padding to a multiple of 4 bytes)
      fm.writeIntL(pathLength);

      // X - File Path (including x:\ etc.)
      // 1 - null File Path Terminator
      // 0-3 - Junk Padding to a multiple of 4 bytes
      fm.writeNullString(filePath,pathLength);

      // 4 - Unknown (4)
      fm.writeIntL(4);

      // 4 - Unknown (3217014528)
      fm.writeIntL((int)3217014528L);

      // 4 - null
      fm.writeIntL(0);

      // 4 - File Length (including everything in File Data except the padding)
      fm.writeIntL(dataLength);

      // 8 - null
      fm.writeLongL(0);



      // 4 - Header (LCH2)
      fm.writeString("LCH2");

      // 4 - File Length (including everything in File Data except the padding)
      fm.writeIntL(dataLength);

      // 4 - Unknown (1)
      fm.writeIntL(1);

      // 4 - null
      fm.writeIntL(0);

      // 4 - Number Of Strings
      fm.writeIntL(numFiles);

      // 4 - Unknown (1)
      fm.writeIntL(1);

      // 8 - null
      fm.writeLongL(0);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // Write the hashes
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];
        // 4 - Hash?
        if (text instanceof Resource_TheGodfather){
          Resource_TheGodfather resource = (Resource_TheGodfather)text;
          fm.writeIntL(resource.getHash());
          }
        else {
          fm.writeIntL(0);
          }
        }



      // write the offsets
      int offset = 32 + numFiles*8;
      for (int i=0;i<numFiles;i++){
        // 4 - String Offset (relative to the start of the File Data: ie. [+2048+144])
        fm.writeIntL(offset);

        int lineLength = resources[i].getTranslatedLength() + 1; // +1 for the null terminator
        offset += lineLength;
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



      // Write the padding
      for (int i=0;i<paddingLength;i++){
        fm.writeByte(0);
        }


      fm.close();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Gets a blank resource of this type, for use when adding resources
**********************************************************************************************
**/
public Resource getBlankResource(){
  return new Resource_TheGodfather();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[3];
    properties[0] = new Resource_Property("Filename");
    properties[1] = new Resource_Property("Extension");
    properties[2] = new Resource_Property("File Path");
    }
  }


/**
**********************************************************************************************
Gets all the columns
**********************************************************************************************
**/
  public WSTableColumn[] getColumns() {
    WSTableColumn[] baseColumns = super.getColumns();
    int numColumns = baseColumns.length;

    // copy the base columns into a new array
    WSTableColumn[] columns = new WSTableColumn[numColumns+1];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns] = new WSTableColumn("Hash",'H',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_TheGodfather){
      Resource_TheGodfather resource = (Resource_TheGodfather)text;

      if (code == 'H'){
        return new Integer(resource.getHash());
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_TheGodfather){
      Resource_TheGodfather resource = (Resource_TheGodfather)text;

      if (code == 'H'){
        resource.setHash(((Integer)value).intValue());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
