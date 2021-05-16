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
public class Plugin_SpaceForceRogueUniverse_Mail extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SpaceForceRogueUniverse_Mail() {

    super("SpaceForceRogueUniverse_Mail","SpaceForceRogueUniverse_Mail");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("SpaceForce: Rogue Universe");
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

      if (fm.getFile().getName().equals("mail_database.txt")){
        rating += 25;
        }

      if (fm.readString(8).equals("addmail:")){
        rating += 25;
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
        String line = fm.readLine();
        if (line == null){
          continue;
          }

        line = line.trim();
        if (line.length() <= 0){
          continue;
          }

        if (!line.equals("addmail:")){
          continue;
          }

        // {
        fm.readLine();

        // id: 0
        int id = Integer.parseInt(fm.readLine().trim().substring(4));

        // type: 0
        int type = Integer.parseInt(fm.readLine().trim().substring(6));

        // from: Ivan Dell
        String from = fm.readLine().trim().substring(6);

        // subject: Welcome
        String original = fm.readLine().trim().substring(9);

        // body: Greetings Anderson. For a second there I thought that fighting off those pirates was going to be out of your league but you proved me wrong. You've got potential. Hopefully it will soon be unleashed and everyone will see what I was talking about. If only your father was alive to see it. Well, I wish you luck, and remember, I'm always here for you no matter what. Make me proud, Anderson. Farewell.
        String body = fm.readLine().trim().substring(6);

        // }
        fm.readLine();


        //original,id
        resources[realNumFiles] = new Resource_SpaceForceRogueUniverse_Mail(original,id,type,from,body);
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
        int id = 0;
        int type = 0;
        String from = "";
        String body = "";

        if (text instanceof Resource_SpaceForceRogueUniverse_Mail){
          Resource_SpaceForceRogueUniverse_Mail resource = (Resource_SpaceForceRogueUniverse_Mail)text;
          id = resource.getID();
          type = resource.getType();
          from = resource.getFrom();
          body = resource.getBody();
          }

        // addmail:
        fm.writeString("addmail:");
        fm.writeByte(13);
        fm.writeByte(10);

        // {
        fm.writeString("   {");
        fm.writeByte(13);
        fm.writeByte(10);

        // id: 0
        fm.writeString("   id: " + id);
        fm.writeByte(13);
        fm.writeByte(10);

        // type: 0
        fm.writeString("   type: " + type);
        fm.writeByte(13);
        fm.writeByte(10);

        // from: Ivan Dell
        fm.writeString("   from: " + from);
        fm.writeByte(13);
        fm.writeByte(10);

        // subject: Welcome
        fm.writeString("   subject: " + translated);
        fm.writeByte(13);
        fm.writeByte(10);

        // body: Greetings Anderson. For a second there I thought that fighting off those pirates was going to be out of your league but you proved me wrong. You've got potential. Hopefully it will soon be unleashed and everyone will see what I was talking about. If only your father was alive to see it. Well, I wish you luck, and remember, I'm always here for you no matter what. Make me proud, Anderson. Farewell.
        fm.writeString("   body: " + body);
        fm.writeByte(13);
        fm.writeByte(10);

        // }
        fm.writeString("   }");
        fm.writeByte(13);
        fm.writeByte(10);

        // blank line
        fm.writeByte(13);
        fm.writeByte(10);

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
  return new Resource_SpaceForceRogueUniverse_Mail();
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
    columns[numColumns]   = new WSTableColumn("From",'F',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Body",'B',String.class,true,true);
    columns[numColumns+2] = new WSTableColumn("ID",  'I',Integer.class,true,true);
    columns[numColumns+3] = new WSTableColumn("Type",'t',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SpaceForceRogueUniverse_Mail){
      Resource_SpaceForceRogueUniverse_Mail resource = (Resource_SpaceForceRogueUniverse_Mail)text;

      if (code == 'I'){
        return new Integer(resource.getID());
        }
      else if (code == 'F'){
        return resource.getFrom();
        }
      else if (code == 'B'){
        return resource.getBody();
        }
      else if (code == 't'){
        return new Integer(resource.getType());
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
      if (text instanceof Resource_SpaceForceRogueUniverse_Mail){
        Resource_SpaceForceRogueUniverse_Mail resource = (Resource_SpaceForceRogueUniverse_Mail)text;

        if (code == 'I'){
          resource.setID(((Integer)value).intValue());
          return;
          }
        else if (code == 'F'){
          resource.setFrom((String)value);
          return;
          }
        else if (code == 'B'){
          resource.setBody((String)value);
          return;
          }
        else if (code == 't'){
          resource.setType(((Integer)value).intValue());
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
