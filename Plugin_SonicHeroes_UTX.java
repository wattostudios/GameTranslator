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
public class Plugin_SonicHeroes_UTX extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SonicHeroes_UTX() {

    super("SonicHeroes_UTX","SonicHeroes_UTX");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Sonic Heroes");
    setExtensions("utx"); // MUST BE LOWER CASE
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

      // 4 - Number Of Groups
      if (check.numFiles(fm.readIntL())){
        rating += 5;
        }

      // 4 - Number Of Texts in Group 1
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


      // 4 - Number Of Groups
      int numGroups = fm.readIntL();
      check.numFiles(numGroups);


      int numFiles = 0;

      int[] numTexts = new int[numGroups];
      for (int i=0;i<numGroups;i++){
        // 4 - Number Of Texts In This Group
        int numText = fm.readIntL();
        check.numFiles(numText);
        numTexts[i] = numText;

        numFiles += numText;
        }



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      int[] offsets = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Text Offset
        int offset = fm.readIntL();
        check.offset(offset,arcSize);
        offsets[i] = offset;
        }


      int group = 0;
      for (int i=0;i<numFiles;i++){
        if (numTexts[group] == 0){
          group++;
          }

        fm.seek(offsets[i]);

        // X - Text (Unicode)
        // 2 - null Text Terminator
        String original = fm.readNullUnicodeString();

        //original,code
        resources[i] = new Resource_SonicHeroes_UTX(original,group);
        WSProgressDialog.setValue(i);

        numTexts[group]--;
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



      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));


      // sort by groups
      java.util.Arrays.sort(resources);

      int numGroups = ((Resource_SonicHeroes_UTX)resources[numFiles-1]).getGroup()+1;


      // determine number of files in each group
      int[] groupCount = new int[numGroups];
      int currentGroup = 0;
      for (int i=0;i<numFiles;i++){
        if (resources[i] instanceof Resource_SonicHeroes_UTX){
          int groupNum = ((Resource_SonicHeroes_UTX)resources[i]).getGroup();
          if (groupNum == currentGroup){
            groupCount[currentGroup]++;
            }
          else {
            currentGroup++;
            groupCount[currentGroup]++;
            }
          }
        else {
          currentGroup++;
          groupCount[currentGroup]++;
          }
        }



      // 4 - Number Of Groups
      fm.writeIntL(numGroups);


      for (int i=0;i<numGroups;i++){
        // 4 - Number Of Texts In This Group
        fm.writeIntL(groupCount[i]);
        }



      // write the offsets
      int offset = 4 + numGroups*4 + numFiles*4;
      for (int i=0;i<numFiles;i++){
        // 4 - Text Offset
        fm.writeIntL(offset);

        offset += (resources[i].getTranslatedLength()*2)+2; // *2 for unicode, +2 for null terminator
        }





      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // X - Text (unicode)
        fm.writeUnicodeString(translated);

        // 2 - null Unicode Text Terminator
        fm.writeByte(0);
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
Gets a blank resource of this type, for use when adding resources
**********************************************************************************************
**/
public Resource getBlankResource(){
  return new Resource_SonicHeroes_UTX();
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
    columns[numColumns] = new WSTableColumn("Group",'G',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SonicHeroes_UTX){
      Resource_SonicHeroes_UTX resource = (Resource_SonicHeroes_UTX)text;

      if (code == 'G'){
        return new Integer(resource.getGroup());
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
      if (text instanceof Resource_SonicHeroes_UTX){
        Resource_SonicHeroes_UTX resource = (Resource_SonicHeroes_UTX)text;

        if (code == 'G'){
          resource.setGroup(((Integer)value).intValue());
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
