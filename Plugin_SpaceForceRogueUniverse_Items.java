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
public class Plugin_SpaceForceRogueUniverse_Items extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SpaceForceRogueUniverse_Items() {

    super("SpaceForceRogueUniverse_Items","SpaceForceRogueUniverse_Items");

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

      if (fm.getFile().getName().indexOf("items") == 0){
        rating += 25;
        }

      if (fm.readString(8).equals("additem:")){
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

        if (!line.equals("additem:")){
          continue;
          }

        // {
        fm.readLine();


        // name: Soul Serum
        String original = fm.readLine().trim().substring(6);

        // description: Drives the habitual user insane.
        String description = fm.readLine().trim().substring(13);

        // patch: 0
        int patch = Integer.parseInt(fm.readLine().trim().substring(7));

        // group: 0
        int group = Integer.parseInt(fm.readLine().trim().substring(7));

        // unit: 1
        int unit = Integer.parseInt(fm.readLine().trim().substring(6));

        // price: 2.000000
        double price = Double.parseDouble(fm.readLine().trim().substring(7));

        // weight: 0.100000
        double weight = Double.parseDouble(fm.readLine().trim().substring(8));

        // space: 0.100000
        double space = Double.parseDouble(fm.readLine().trim().substring(7));

        // power: 0
        int power = Integer.parseInt(fm.readLine().trim().substring(7));

        // Data1: -1
        int data1 = Integer.parseInt(fm.readLine().trim().substring(7));

        // Data2: -1
        int data2 = Integer.parseInt(fm.readLine().trim().substring(7));

        // }
        fm.readLine();


        resources[realNumFiles] = new Resource_SpaceForceRogueUniverse_Items(original,description,patch,group,unit,price,weight,space,power,data1,data2);
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
        int patch = 0;
        int group = 0;
        int unit = 0;
        double price = 0;
        double weight = 0;
        double space = 0;
        int power = 0;
        int data1 = -1;
        int data2 = -1;

        if (text instanceof Resource_SpaceForceRogueUniverse_Items){
          Resource_SpaceForceRogueUniverse_Items resource = (Resource_SpaceForceRogueUniverse_Items)text;
          description = resource.getDescription();
          patch = resource.getPatch();
          group = resource.getGroup();
          unit = resource.getUnit();
          price = resource.getPrice();
          weight = resource.getWeight();
          space = resource.getSpace();
          power = resource.getPower();
          data1 = resource.getData1();
          data2 = resource.getData2();
          }

        // additem:
        fm.writeString("additem:");
        fm.writeByte(13);
        fm.writeByte(10);

        // {
        fm.writeString("   {");
        fm.writeByte(13);
        fm.writeByte(10);

        // name: Soul Serum
        fm.writeString("   name: " + translated);
        fm.writeByte(13);
        fm.writeByte(10);

        // description: Drives the habitual user insane.
        fm.writeString("   description: " + description);
        fm.writeByte(13);
        fm.writeByte(10);

        // patch: 0
        fm.writeString("   patch: " + patch);
        fm.writeByte(13);
        fm.writeByte(10);

        // group: 0
        fm.writeString("   group: " + group);
        fm.writeByte(13);
        fm.writeByte(10);

        // unit: 1
        fm.writeString("   unit: " + unit);
        fm.writeByte(13);
        fm.writeByte(10);

        // price: 2.000000
        fm.writeString("   price: " + price);
        fm.writeByte(13);
        fm.writeByte(10);

        // weight: 0.100000
        fm.writeString("   weight: " + weight);
        fm.writeByte(13);
        fm.writeByte(10);

        // space: 0.100000
        fm.writeString("   space: " + space);
        fm.writeByte(13);
        fm.writeByte(10);

        // power: 0
        fm.writeString("   power: " + power);
        fm.writeByte(13);
        fm.writeByte(10);

        // Data1: -1
        fm.writeString("   Data1: " + data1);
        fm.writeByte(13);
        fm.writeByte(10);

        // Data2: -1
        fm.writeString("   Data2: " + data2);
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
  return new Resource_SpaceForceRogueUniverse_Items();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+10];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns]   = new WSTableColumn("Description",'D',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Patch",      'P',Integer.class,true,true);
    columns[numColumns+2] = new WSTableColumn("Group",      'G',Integer.class,true,true);
    columns[numColumns+3] = new WSTableColumn("Unit",       'U',Integer.class,true,true);
    columns[numColumns+4] = new WSTableColumn("Price",      'p',Double.class,true,true);
    columns[numColumns+5] = new WSTableColumn("Weight",     'W',Double.class,true,true);
    columns[numColumns+6] = new WSTableColumn("Space",      'S',Double.class,true,true);
    columns[numColumns+7] = new WSTableColumn("Power",      'w',Integer.class,true,true);
    columns[numColumns+8] = new WSTableColumn("Data 1",     '1',Integer.class,true,true);
    columns[numColumns+9] = new WSTableColumn("Data 2",     '2',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SpaceForceRogueUniverse_Items){
      Resource_SpaceForceRogueUniverse_Items resource = (Resource_SpaceForceRogueUniverse_Items)text;

      if (code == 'D'){
        return resource.getDescription();
        }
      else if (code == 'P'){
        return new Integer(resource.getPatch());
        }
      else if (code == 'G'){
        return new Integer(resource.getGroup());
        }
      else if (code == 'U'){
        return new Integer(resource.getUnit());
        }
      else if (code == 'p'){
        return new Double(resource.getPrice());
        }
      else if (code == 'W'){
        return new Double(resource.getWeight());
        }
      else if (code == 'S'){
        return new Double(resource.getSpace());
        }
      else if (code == 'w'){
        return new Integer(resource.getPower());
        }
      else if (code == '1'){
        return new Integer(resource.getData1());
        }
      else if (code == '2'){
        return new Integer(resource.getData2());
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
      if (text instanceof Resource_SpaceForceRogueUniverse_Items){
        Resource_SpaceForceRogueUniverse_Items resource = (Resource_SpaceForceRogueUniverse_Items)text;

        if (code == 'D'){
          resource.setDescription((String)value);
          return;
          }
        else if (code == 'P'){
          resource.setPatch(((Integer)value).intValue());
          return;
          }
        else if (code == 'G'){
          resource.setGroup(((Integer)value).intValue());
          return;
          }
        else if (code == 'U'){
          resource.setUnit(((Integer)value).intValue());
          return;
          }
        else if (code == 'p'){
          resource.setPrice(((Double)value).doubleValue());
          return;
          }
        else if (code == 'W'){
          resource.setWeight(((Double)value).doubleValue());
          return;
          }
        else if (code == 'S'){
          resource.setSpace(((Double)value).doubleValue());
          return;
          }
        else if (code == 'w'){
          resource.setPower(((Integer)value).intValue());
          return;
          }
        else if (code == '1'){
          resource.setData1(((Integer)value).intValue());
          return;
          }
        else if (code == '2'){
          resource.setData2(((Integer)value).intValue());
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
