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
public class Plugin_ArmageddonEmpires_BasicTileData extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ArmageddonEmpires_BasicTileData() {

    super("ArmageddonEmpires_BasicTileData","ArmageddonEmpires_BasicTileData");

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

      if (fm.getFile().getName().equals("BasicTileData.txt")){
        rating += 25;
        }

      // first line
      if (fm.readString(11).equals("tilename: [")){
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

        // tilename: ["The Camps"]
        String tileName = fm.readLine();
        if (tileName == null || tileName.length() <= 0 || tileName.equals("EOF")){
          continue;
          }

        tileName = tileName.substring(12,tileName.length()-2);

        // tile_desc: ["Machine empire guards remain at their posts growing humans until they receive new orders."]
        String description = fm.readLine();
        description = description.substring(13,description.length()-2);

        // tilepic: ["1"]
        String tilePic = fm.readLine();
        int bracketEnd = tilePic.indexOf("\"]");
        tilePic = tilePic.substring(11,bracketEnd);

        // baseID: ["Desert"]
        String baseID = fm.readLine();
        baseID = baseID.substring(10,baseID.length()-2);

        // movept_cost: [1]
        String movementCost = fm.readLine();
        movementCost = movementCost.substring(14,movementCost.length()-1);


        //name,desc,pic,base,cost
        resources[realNumFiles] = new Resource_ArmageddonEmpires_BasicTileData(tileName,description,tilePic,baseID,movementCost);
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
        String tilePic = "";
        String baseID = "";
        String movementCost = "";

        if (text instanceof Resource_ArmageddonEmpires_BasicTileData){
          Resource_ArmageddonEmpires_BasicTileData resource = (Resource_ArmageddonEmpires_BasicTileData)text;
          description = resource.getDescription();
          tilePic = resource.getTilePic();
          baseID = resource.getBaseID();
          movementCost = resource.getMovementCost();
          }

        // tilename: ["The Camps"]
        fm.writeString("tilename: [\"" + translated + "\"]");
        fm.writeByte(13);
        fm.writeByte(10);

        // tile_desc: ["Machine empire guards remain at their posts growing humans until they receive new orders."]
        fm.writeString("tile_desc: [\"" + description + "\"]");
        fm.writeByte(13);
        fm.writeByte(10);

        // tilepic: ["1"]
        fm.writeString("tilepic: [\"" + tilePic + "\"]");
        fm.writeByte(13);
        fm.writeByte(10);

        // baseID: ["Desert"]
        fm.writeString("baseID: [\"" + baseID + "\"]");
        fm.writeByte(13);
        fm.writeByte(10);

        // movept_cost: [1]
        fm.writeString("movept_cost: [" + movementCost + "]");
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
  return new Resource_ArmageddonEmpires_BasicTileData();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+4];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns]   = new WSTableColumn("Description",'D',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Tile Picture",'P',String.class,true,true);
    columns[numColumns+2] = new WSTableColumn("Base ID",'I',String.class,true,true);
    columns[numColumns+3] = new WSTableColumn("Movement Cost",'C',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_ArmageddonEmpires_BasicTileData){
      Resource_ArmageddonEmpires_BasicTileData resource = (Resource_ArmageddonEmpires_BasicTileData)text;

      if (code == 'D'){
        return resource.getDescription();
        }
      else if (code == 'P'){
        return resource.getTilePic();
        }
      else if (code == 'I'){
        return resource.getBaseID();
        }
      else if (code == 'C'){
        return resource.getMovementCost();
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
      if (text instanceof Resource_ArmageddonEmpires_BasicTileData){
        Resource_ArmageddonEmpires_BasicTileData resource = (Resource_ArmageddonEmpires_BasicTileData)text;

        if (code == 'D'){
          resource.setDescription((String)value);
          return;
          }
        else if (code == 'P'){
          resource.setTilePic((String)value);
          return;
          }
        else if (code == 'I'){
          resource.setBaseID((String)value);
          return;
          }
        else if (code == 'C'){
          resource.setMovementCost((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
