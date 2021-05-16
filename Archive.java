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

import org.watto.*;
import org.watto.component.*;
import org.watto.manipulator.FileBuffer;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
**********************************************************************************************
The Archive class manages details about the loaded archive, such as the <i>resources</i>, the
<i>columns</i> to be shown in the current FileListPanel, and the plugin used to read the
archive. It also contains methods for the management and manipulation of Resources, such as
adding and removing resources.
<br><br>
This class is entirely static. You will need to call the constructor once in order to set up a
few globals and such, but from this point onwards you would simply call the methods directly
such as by "Archive.runMethod()".
**********************************************************************************************
**/
public class Archive{

  /** The file that was read into this archive **/
  static File basePath = null;
  /** The columns to be shown in the current FileListPanel **/
  static WSTableColumn[] columns = new WSTableColumn[0];
  /** The plugin used to read the <i>basePath</i> archive **/
  static ArchivePlugin readPlugin = new AllFilesPlugin();
  /** The plugin that was originally used, before changing to a different format **/
  static ArchivePlugin oldReadPlugin = readPlugin;
  /** The resources stored in this archive **/
  static Resource[] resources = new Resource[0];

  static Icon fileIcon;
  static Icon renamedIcon;
  static Icon unrenamedIcon;
  static Icon addedIcon;
  static Icon unaddedIcon;


/**
**********************************************************************************************
Constructor. Should only be called once.
**********************************************************************************************
**/
  public Archive() {
    columns = getDefaultColumns();
    try {
      fileIcon = new ImageIcon(getClass().getResource("images/WSTable/GenericFile.png"));
      renamedIcon = new ImageIcon(getClass().getResource("images/WSTable/Renamed.png"));
      unrenamedIcon = new ImageIcon(getClass().getResource("images/WSTable/Unrenamed.png"));
      addedIcon = new ImageIcon(getClass().getResource("images/WSTable/Added.png"));
      unaddedIcon = new ImageIcon(getClass().getResource("images/WSTable/Unadded.png"));

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Adds a resource to the archive
@param file the resource to add
**********************************************************************************************
**/
  public static void addResource(Resource file) {
    int numResources = resources.length;
    resizeResources(numResources + 1);
    resources[numResources] = file;
    }


/**
**********************************************************************************************
Adds a number of files to the archive
@param files the files to add
**********************************************************************************************
**/
  public static void addResources(Resource[] files) {

    int numFiles = resources.length;
    int newNumFiles = numFiles + files.length;
    resizeResources(newNumFiles);

    for (int i=numFiles,j=0;i<newNumFiles && j<files.length;i++,j++){
      resources[i] = files[j];
      }

    }


/**
**********************************************************************************************
Adds a number of files to the archive
@param files the files to add
**********************************************************************************************
**/
  public static Resource[] addResources(int numToAdd) {

    Resource[] newResources = new Resource[numToAdd];
    for (int i=0;i<numToAdd;i++){
      newResources[i] = readPlugin.getBlankResource();
      }

    addResources(newResources);

    return newResources;

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Icon getAddedIcon(boolean added){
    if (added){
      return addedIcon;
      }
    else {
      return unaddedIcon;
      }
    }


/**
**********************************************************************************************
Gets the name of the archive
@return the name of the opened archive, or "newArchive" if the archive was started from scratch
**********************************************************************************************
**/
  public static String getArchiveName() {
    if (basePath == null){
      return "newLanguagePack";
      }
    else {
      return basePath.getName();
      }
    }


/**
**********************************************************************************************
Gets the file that was loaded
@return the archive file
**********************************************************************************************
**/
  public static File getBasePath() {
    return basePath;
    }


/**
**********************************************************************************************
Gets the column from position <i>column</i> of the array
@param column the column number
@return the column
**********************************************************************************************
**/
  public static WSTableColumn getColumn(int column) {
    if (column < columns.length){
      return columns[column];
      }
    else {
      return null;
      }
    }


/**
**********************************************************************************************
Gets the column with the <i>columnCode</i>
@param columnCode the code of the column
@return the column
**********************************************************************************************
**/
  public static WSTableColumn getColumn(char columnCode) {
    for (int i=0;i<columns.length;i++){
      if (columns[i].getCharCode() == columnCode){
        return columns[i];
        }
      }
    return null;
    }



/**
**********************************************************************************************
Gets all the columns
@return the columns
**********************************************************************************************
**/
  public static WSTableColumn[] getColumns() {
    if (columns == null){
      columns = getDefaultColumns();
      }
    return columns;
    }


/**
**********************************************************************************************
Gets the default columns for an archive
@return the default columns
**********************************************************************************************
**/
  public static WSTableColumn[] getDefaultColumns() {
    // We can't get it from ArchivePlugin directly because it is an abstract class.
    // So, get it from a dummy ArchivePlugin instead.
    return new AllFilesPlugin().getColumns();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Icon getIcon(String name){
    return fileIcon;
    }


/**
**********************************************************************************************
Gets the maximum number of files, from setting "MaxNumberOfFiles4"
@return the maximum number of files
**********************************************************************************************
**/
  public static int getMaxFiles() {
    return getMaxFiles(4);
    }


/**
**********************************************************************************************
Gets the maximum number of files for a field with <i>size</i> number of bytes
@param size the number of bytes assigned to the NumberOfFiles field of an archive
@return the maximum number of files
**********************************************************************************************
**/
  public static int getMaxFiles(int size) {
    if (size == 2){
      return Settings.getInt("MaxNumberOfFiles2");
      }
    else {
      return Settings.getInt("MaxNumberOfFiles4");
      }
    }


/**
**********************************************************************************************
Gets the number of columns
@return the number of columns
**********************************************************************************************
**/
  public static int getNumColumns() {
    return columns.length;
    }


/**
**********************************************************************************************
Gets the number of resources in the archive
@return the number of files
**********************************************************************************************
**/
  public static int getNumFiles() {
    if (resources == null){
      return 0;
      }
    return resources.length;
    }


/**
**********************************************************************************************
Gets the plugin used to read the archive
@return the plugin
**********************************************************************************************
**/
  public static ArchivePlugin getReadPlugin() {
    return readPlugin;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ArchivePlugin getOldReadPlugin() {
    return oldReadPlugin;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Icon getRenamedIcon(boolean renamed){
    if (renamed){
      return renamedIcon;
      }
    else {
      return unrenamedIcon;
      }
    }


/**
**********************************************************************************************
Gets the resource from index <i>num</i> of the array
@param num the resource number
@return the resource.
**********************************************************************************************
**/
  public static Resource getResource(int num) {
    return resources[num];
    }


/**
**********************************************************************************************
Gets all the resources in the archive
@return the resources
**********************************************************************************************
**/
  public static Resource[] getResources() {
    return resources;
    }


/**
**********************************************************************************************
Gets <i>numOfResources</i>, starting from the <i>startResource</i>
@return the resources
**********************************************************************************************
**/
  public static Resource[] getResources(int startResource, int numOfResources) {
    Resource[] range = new Resource[numOfResources];
    System.arraycopy(resources,startResource,range,0,numOfResources);
    return range;
    }


/**
**********************************************************************************************
Gets the column with the <i>columnCode</i>
@param columnCode the code of the column
@return the column
**********************************************************************************************
**/
  public static WSTableColumn[] getSearchableColumns() {
    WSTableColumn[] columns = getColumns();

    WSTableColumn[] outColumns = new WSTableColumn[columns.length];
    int numColumns = 0;

    for (int i=0;i<columns.length;i++){
      if (columns[i].getType() != Icon.class){
        outColumns[numColumns] = columns[i];
        numColumns++;
        }
      }


    if (numColumns != outColumns.length){
      columns = outColumns;
      outColumns = new WSTableColumn[numColumns];
      System.arraycopy(columns,0,outColumns,0,numColumns);
      }

    return outColumns;
    }


/**
**********************************************************************************************
Makes a new archive. Effectively resets the globals to their initial values. If there is an
archive already opened, and if the archive has been modified, it will ask the user to save first.
**********************************************************************************************
**/
  public static void makeNewArchive() {

    resources = new Resource[0];
    readPlugin = new AllFilesPlugin();
    oldReadPlugin = readPlugin;
    basePath = null;

    columns = getDefaultColumns();

    setBasePath(null);

    SidePanel_DirectoryList panel = (SidePanel_DirectoryList)WSRepository.get("SidePanel_DirectoryList");
    panel.checkInvalidControls();
    }


/**
**********************************************************************************************
Removes all resources from the archive
**********************************************************************************************
**/
  public static void removeAllResources() {
    resources = new Resource[0];
    }


/**
**********************************************************************************************
Removes all null resources from the array, which may be caused when removing files in batch, or
when adding directories of files.
**********************************************************************************************
**/
  public static void removeNullResources() {
    try {

      // find the 2 pointers
      int nullPos = -1;
      int nextFile = -1;
      for (int i=0;i<resources.length;i++){
        if (nullPos == -1 && resources[i] == null){
          nullPos = i;
          }
        if (nullPos > -1 && resources[i] != null){
          nextFile = i;
          i = resources.length;
          }
        }

      // re-shuffle the resource array to the top
      if (nullPos > -1 && nextFile > -1){
        for (int i=nextFile;i<resources.length;i++){
          if (resources[i] != null){
            resources[nullPos] = resources[i];
            nullPos ++;
            }
          }
        }


      // resize the resources array
      if (nullPos > -1){
        resizeResources(nullPos);
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Removes the resource <i>num</i> from the archive
@param num the resource to remove
**********************************************************************************************
**/
  public static void removeResource(int num) {
    removeResource(resources[num]);
    }


/**
**********************************************************************************************
Removes the <i>file</i> from the archive
@param file the resource to remove
**********************************************************************************************
**/
  public static void removeResource(Resource file) {
    removeResources(new Resource[]{file});
    }


/**
**********************************************************************************************
Removes the <i>files</i> from the archive
@param files the resources to remove.
**********************************************************************************************
**/
  public static void removeResources(Resource[] files) {
    try {

      // null out the resources to remove
      int filesPos = 0;
      while (filesPos < files.length){
        for (int i=0;i<resources.length;i++){
          if (files[filesPos] == resources[i]){
            resources[i] = null;
            filesPos++;
            if (filesPos >= files.length){
              i = resources.length;
              }
            }
          }
        }

      removeNullResources();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Changes the size of the <i>resources</i> array
@param numResources the new size of the array
**********************************************************************************************
**/
  public static void resizeResources(int numResources) {
    Resource[] temp = resources;
    resources = new Resource[numResources];

    if (numResources < temp.length){
      System.arraycopy(temp,0,resources,0,numResources);
      }
    else {
      System.arraycopy(temp,0,resources,0,temp.length);
      }
    }


/**
**********************************************************************************************
Sets the <i>basePath</i> of the opened file
@param basePathNew the new path
**********************************************************************************************
**/
  public static void setBasePath(File basePathNew) {
    basePath = basePathNew;

    try {
      new FullVersionVerifier();
      if (basePath == null){
        GameTranslator.getInstance().setTitle(Language.get("ProgramName") + " " + Settings.get("Version") + " - http://www.watto.org");
        }
      else {
        GameTranslator.getInstance().setTitle(Language.get("ProgramName") + " " + Settings.get("Version") + " [" + basePath.getName() + "]");
        }
      }
    catch (Throwable t){
      if (basePath == null){
        GameTranslator.getInstance().setTitle(Language.get("ProgramName_Free") + " " + Settings.get("Version") + " - http://www.watto.org");
        }
      else {
        GameTranslator.getInstance().setTitle(Language.get("ProgramName_Free") + " " + Settings.get("Version") + " [" + basePath.getName() + "]");
        }
      }
    }


/**
**********************************************************************************************
Sets the columns to be shown by the current FileListPanel to the default
**********************************************************************************************
**/
  public static void setColumns() {
    columns = getDefaultColumns();
    }


/**
**********************************************************************************************
Sets the columns to be shown by the current FileListPanel
@param columnsNew the new columns
**********************************************************************************************
**/
  public static void setColumns(WSTableColumn[] columnsNew) {
    columns = columnsNew;
    }


/**
**********************************************************************************************
Sets the number of resources in the archive. Used for undo() in Task_AddFiles();
@param numFiles the new number of files
**********************************************************************************************
**/
  public static void setNumFiles(int numFiles) {
    resizeResources(numFiles);
    }


/**
**********************************************************************************************
Sets the plugin used to read the archive
@param pluginNew the new plugin
**********************************************************************************************
**/
  public static void setReadPlugin(ArchivePlugin pluginNew) {
    oldReadPlugin = readPlugin;
    readPlugin = pluginNew;

    SidePanel_DirectoryList panel = (SidePanel_DirectoryList)WSRepository.get("SidePanel_DirectoryList");
    panel.checkInvalidControls();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setOldReadPlugin(ArchivePlugin plugin) {
    oldReadPlugin = plugin;
    }


/**
**********************************************************************************************
Sets the resources in the archive
@param resourcesNew the new resources
**********************************************************************************************
**/
  public static void setResources(Resource[] resourcesNew) {
    resources = resourcesNew;
    }


/**
**********************************************************************************************
Records an error to the log file
@param t the error that occurred.
**********************************************************************************************
**/
  public static void logError(Throwable t) {
    ErrorLogger.log(t);
    }


  }