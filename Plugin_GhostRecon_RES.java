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
public class Plugin_GhostRecon_RES extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_GhostRecon_RES() {

    super("GhostRecon_RES","GhostRecon_RES");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Ghost Recon");
    setExtensions("res"); // MUST BE LOWER CASE
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

      if (fm.getFile().getName().equalsIgnoreCase("STRINGS.RES")){
        rating += 25;
        }

      // 4 - numFiles
      if (check.numFiles(fm.readIntL())){
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


      // 4 - Number Of Groups
      int numGroups = fm.readIntL();
      check.numFiles(numGroups);


      int numFiles = Archive.getMaxFiles();


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numGroups);



      // read the resources
      int realNumFiles = 0;
      for (int i=0;i<numGroups;i++){
        // 4 - Number Of Strings in Group
        int numInGroup = fm.readIntL();
        check.numFiles(numInGroup);

        for (int j=0;j<numInGroup;j++){
          // 4 - String Length
          int filenameLength = fm.readIntL();

          // X - String
          String original = fm.readString(filenameLength);

          // 2 - null
          fm.skip(2);

          //original,code
          resources[realNumFiles] = new Resource_GhostRecon_RES(original,(i+1));
          realNumFiles++;
          }

        // 4 - null End Of Group Indicator
        fm.skip(4);

        WSProgressDialog.setValue(i);
        }


      fm.close();

      resources = resizeResources(resources,realNumFiles);

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


      // sort by groups
      java.util.Arrays.sort(resources);

      int numGroups = ((Resource_GhostRecon_RES)resources[numFiles-1]).getGroup();


      // determine number of files in each group
      int[] groupCount = new int[numGroups];
      int currentGroup = 1;
      for (int i=0;i<numFiles;i++){
        if (resources[i] instanceof Resource_GhostRecon_RES){
          int groupNum = ((Resource_GhostRecon_RES)resources[i]).getGroup();
          if (groupNum == currentGroup){
            groupCount[currentGroup-1]++;
            }
          else {
            currentGroup++;
            groupCount[currentGroup-1]++;
            }
          }
        else {
          currentGroup++;
          groupCount[currentGroup-1]++;
          }
        }



      // 4 - Number Of Groups
      fm.writeIntL(numGroups);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));


      // 4 - Number Of Strings in Group
      fm.writeIntL(groupCount[0]);


      // Write the strings
      currentGroup = 1;
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int group = 0;

        if (text instanceof Resource_GhostRecon_RES){
          Resource_GhostRecon_RES resource = (Resource_GhostRecon_RES)text;
          group = resource.getGroup();
          }

        if (group != currentGroup){
          currentGroup++;

          // 4 - null End Of Group Indicator
          fm.writeIntL(0);

          // 4 - Number Of Strings in Group
          fm.writeIntL(groupCount[currentGroup-1]);
          }

        // 4 - String Length
        fm.writeIntL(translated.length());

        // X - String
        fm.writeString(translated);

        // 2 - null
        fm.writeShortL((short)0);

        WSProgressDialog.setValue(i);
        }


      // 4 - null End Of Group Indicator
      fm.writeIntL(0);


      // 4 - null End Of Language Pack Indicator
      fm.writeIntL(0);


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
  return new Resource_GhostRecon_RES();
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
    if (text instanceof Resource_GhostRecon_RES){
      Resource_GhostRecon_RES resource = (Resource_GhostRecon_RES)text;

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
      if (text instanceof Resource_GhostRecon_RES){
        Resource_GhostRecon_RES resource = (Resource_GhostRecon_RES)text;

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
