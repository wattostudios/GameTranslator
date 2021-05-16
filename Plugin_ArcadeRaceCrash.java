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
import org.watto.component.WSPluginException;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_ArcadeRaceCrash extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ArcadeRaceCrash() {

    super("ArcadeRaceCrash","ArcadeRaceCrash");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Arcade Race: Crash");
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

      // see if there is the matching ArcadeRaceCrash_size.txt file
      File currentFile = fm.getFile();
      String path = currentFile.getAbsolutePath();
      int dotPos = path.lastIndexOf(".txt");

      if (dotPos <= 0){
        return 0;
        }

      path = path.substring(0,dotPos) + "_size.txt";
      currentFile = new File(path);

      if (currentFile.exists()){
        rating += 25;
        }
      else {
        return 0;
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


      // First, get the matching ArcadeRaceCrash_size.txt file
      String sizePath = path.getAbsolutePath();
      int dotPos = sizePath.lastIndexOf(".txt");
      if (dotPos <= 0){
        throw new WSPluginException("Missing Size File");
        }
      sizePath = sizePath.substring(0,dotPos) + "_size.txt";
      File sizeFile = new File(sizePath);
      if (!sizeFile.exists()){
        throw new WSPluginException("Missing Size File");
        }


      FileManipulator fm = new FileManipulator(path,"r");
      long arcSize = fm.getLength();


      // 4 - Number Of Text Strings
      int numFiles = Archive.getMaxFiles();


      // read the resources
      String[] texts = new String[numFiles];

      int realNumFiles = 0;

      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();
        texts[realNumFiles] = line;
        realNumFiles++;
        }

      fm.close();



      numFiles = realNumFiles;

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      // Now open the ArcadeRaceCrash_size.txt file and read the properties
      fm = new FileManipulator(sizeFile,"r");

      for (int i=0;i<numFiles;i++){
        String line = fm.readLine();
        if (line == null || line.length() < 12){
          fm.close();
          throw new WSPluginException("The size file has less entries than the language file.");
          }

        // 12 chars - Size Value 1 (padded with spaces) (float number)
        float size1 = Float.parseFloat(line.substring(0,12));

        // X chars - Size Value 2 (float number)
        float size2 = Float.parseFloat(line.substring(12));

        //original,size1,size2
        resources[i] = new Resource_ArcadeRaceCrash(texts[i],size1,size2);
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


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles-1;i++){
        Resource text = resources[i];
        String translated = text.getTranslated();

        // X - String Data
        fm.writeString(translated);

        // 2 bytes - New Line
        fm.writeByte(13);
        fm.writeByte(10);
        }
      // the last string should not have a newline terminator after it.
      fm.writeString(resources[numFiles-1].getTranslated());


      fm.close();




      // Now write the XXX_size.txt file
      String sizePath = path.getAbsolutePath();
      int dotPos = sizePath.lastIndexOf(".");
      if (dotPos <= 0){
        return;
        }
      String newSizePath = sizePath.substring(0,dotPos) + "_size" + sizePath.substring(dotPos);
      File sizeFile = new File(newSizePath);

      fm = new FileManipulator(sizeFile,"rw");


      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String size1 = "1";
        String size2 = "1";

        if (text instanceof Resource_ArcadeRaceCrash){
          Resource_ArcadeRaceCrash resource = (Resource_ArcadeRaceCrash)text;
          size1 = "" + resource.getSize1();
          size2 = "" + resource.getSize2();
          }


        // 12 chars - Size Value 1 (padded with spaces) (float number)
        if (size1.length() > 11){ // forces a space character between Size1 and Size2
          size1 = size1.substring(0,11);
          }
        while (size1.length() < 12){
          size1 = size1 + " ";
          }

        fm.writeString(size1);

        // X chars - Size Value 2 (float number)
        fm.writeString(size2);

        // 2 bytes - New Line (not for the last line)
        if (i != numFiles-1){
          fm.writeByte(13);
          fm.writeByte(10);
          }

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
Gets a blank resource of this type, for use when adding resources
**********************************************************************************************
**/
public Resource getBlankResource(){
  return new Resource_ArcadeRaceCrash();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+2];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns]   = new WSTableColumn("Size 1",'F',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Size 2",'G',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_ArcadeRaceCrash){
      Resource_ArcadeRaceCrash resource = (Resource_ArcadeRaceCrash)text;

      if (code == 'F'){
        return ""+resource.getSize1();
        }
      else if (code == 'G'){
        return ""+resource.getSize2();
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    try {
      if (text instanceof Resource_ArcadeRaceCrash){
        Resource_ArcadeRaceCrash resource = (Resource_ArcadeRaceCrash)text;

        if (code == 'F'){
          resource.setSize1(Float.parseFloat((String)value));
          return;
          }
        else if (code == 'G'){
          resource.setSize2(Float.parseFloat((String)value));
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
