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
public class Plugin_ArmageddonEmpires_CardKeys extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ArmageddonEmpires_CardKeys() {

    super("ArmageddonEmpires_CardKeys","ArmageddonEmpires_CardKeys");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Armageddon Empires");
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

      if (fm.getPath().indexOf("CardKeys") > 0){
        rating += 25;
        }

      // first line
      if (fm.readString(5).equals("Desc_")){
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


      int numFiles = Archive.getMaxFiles();


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){

        // Desc_recce_2_6: This unit can perform recconaisance
        String description = fm.readLine();
        if (description == null || description.length() <= 0 || description.equals("EOF")){
          continue;
          }

        int dotPos = description.indexOf(":");
        String code = description.substring(5,dotPos);
        description = description.substring(dotPos+2);

        // Name_recce_2_6: Recce S6 R2
        String name = fm.readLine();
        dotPos = name.indexOf(":");
        name = name.substring(dotPos+2);

        // Cost_recce_2_6: [void]
        String cost = fm.readLine();
        dotPos = cost.indexOf(":");
        cost = cost.substring(dotPos+2);


        //name,code,desc,cost
        resources[realNumFiles] = new Resource_ArmageddonEmpires_CardKeys(name,code,description,cost);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;
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



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String description = "";
        String cost = "";
        String code = "";

        if (text instanceof Resource_ArmageddonEmpires_CardKeys){
          Resource_ArmageddonEmpires_CardKeys resource = (Resource_ArmageddonEmpires_CardKeys)text;
          description = resource.getDescription();
          cost = resource.getCost();
          code = resource.getCode();
          }

        // Desc_recce_2_6: This unit can perform recconaisance
        fm.writeString("Desc_" + code + ": " + description);
        fm.writeByte(13);
        fm.writeByte(10);

        // Name_recce_2_6: Recce S6 R2
        fm.writeString("Name_" + code + ": " + translated);
        fm.writeByte(13);
        fm.writeByte(10);

        // Cost_recce_2_6: [void]
        fm.writeString("Cost_" + code + ": " + cost);
        fm.writeByte(13);
        fm.writeByte(10);


        // blank line between entries
        fm.writeByte(13);
        fm.writeByte(10);

        WSProgressDialog.setValue(i);
        }


      // EOF
      fm.writeString("EOF");

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
  return new Resource_ArmageddonEmpires_CardKeys();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+3];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns]   = new WSTableColumn("Code",'C',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Description",'D',String.class,true,true);
    columns[numColumns+2] = new WSTableColumn("Cost",'S',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_ArmageddonEmpires_CardKeys){
      Resource_ArmageddonEmpires_CardKeys resource = (Resource_ArmageddonEmpires_CardKeys)text;

      if (code == 'C'){
        return resource.getCode();
        }
      else if (code == 'D'){
        return resource.getDescription();
        }
      else if (code == 'S'){
        return resource.getCost();
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
      if (text instanceof Resource_ArmageddonEmpires_CardKeys){
        Resource_ArmageddonEmpires_CardKeys resource = (Resource_ArmageddonEmpires_CardKeys)text;

        if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        else if (code == 'D'){
          resource.setDescription((String)value);
          return;
          }
        else if (code == 'S'){
          resource.setCost((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
