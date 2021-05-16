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
import org.watto.manipulator.*;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
**********************************************************************************************
The ArchivePlugin is one of the most important classes in Game Extractor. A class that extends
from ArchivePlugin is able to read, and possibly write, a particular format of archive.
<br><br>
This class contains many methods and globals that make it easy to write an extending class,
such as methods for reading and writing using different inputs, methods to control the data
displayed by the FileTablePanels and by the FileTableSorter, and globals for verification of
fields when reading an archive.
<br><br>
It also contains methods to allow automatic replacing support with only slight alteration to
the code in your plugin. Methods to assist Game Extractor in the automatic detection of a
compatable read plugin are also supplied.
**********************************************************************************************
**/
public abstract class ArchivePlugin extends WSObjectPlugin {

  /** Can this plugin read an archive? **/
  boolean canRead = true;
  /** Can this plugin write an archive? **/
  boolean canWrite = false;
  /** Can this plugin rename files within the archive? **/
  boolean canRename = false;

  boolean allowsUnicode = false;

  /** The games that use this archive format **/
  String[] games = new String[]{""};
  /** The default extension of archives complying to this format **/
  String[] extensions = new String[]{""};
  /** The platforms that this archive exists on (such as "PC", "XBox", or "PS2") **/
  String[] platforms = new String[]{""};

  /** properties of the archive **/
  Resource_Property[] properties = new Resource_Property[0];


    /** quick access to the field validator **/
  static FieldValidator check = new FieldValidator();


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public ArchivePlugin() {
    setCode("ArchivePlugin");
    setName("Archive Plugin");
    }


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public ArchivePlugin(String code) {
    setCode(code);
    setName(code);
    }


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public ArchivePlugin(String code, String name) {
    setCode(code);
    setName(name);
    }


/**
**********************************************************************************************
Can this plugin read an archive?
@return true if the plugin can read, false if it cannot.
**********************************************************************************************
**/
  public boolean canRead() {
    return canRead;
    }


/**
**********************************************************************************************
Can this plugin rename files within an archive?
@return true if files can be renamed, false if replacing is not allowed.
**********************************************************************************************
**/
  public boolean canRename() {
    return canRename;
    }


/**
**********************************************************************************************
Can this plugin rename files within an archive?
@return true if files can be renamed, false if replacing is not allowed.
**********************************************************************************************
**/
  public boolean canReplace() {
    return canRename;
    }


/**
**********************************************************************************************
Can this plugin write archives?
@return true is the plugin can write archives, false if it cannot.
**********************************************************************************************
**/
  public boolean canWrite() {
    return canWrite;
    }


/**
**********************************************************************************************
Gets a list of the allowed functions
@return the list
**********************************************************************************************
**/
  public String getAllowedFunctionsList() {
    String list = "";

    if (canRead){
      list += Language.get("Description_ReadOperation");
      }
    if (canWrite){
      if (list.length() > 0){
        list += ", ";
        }
      list += Language.get("Description_WriteOperation");
      }

    if (canRename){
      if (list.length() > 0){
        list += ", ";
        }
      list += Language.get("Description_RenameOperation");
      }

    return list;
    }


/**
**********************************************************************************************
Gets a blank resource of this type, for use when adding resources
**********************************************************************************************
**/
public Resource getBlankResource(){
  return new Resource();
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


/**
**********************************************************************************************
Gets all the columns for displaying information in the FileTablePanel
@return the columns
**********************************************************************************************
**/
  public WSTableColumn[] getColumns() {
    WSTableColumn[] columns = new WSTableColumn[4];

    //code,languageCode,class,editable,sortable
    columns[0] = new WSTableColumn("AddedIcon"  ,'a',Icon.class  ,false,false,18,18); //added
    columns[1] = new WSTableColumn("RenamedIcon",'r',Icon.class  ,false,false,18,18); //renamed
    columns[2] = new WSTableColumn("Original"   ,'O',String.class,false,true);
    columns[3] = new WSTableColumn("Translated" ,'T',String.class,true ,true);

    return columns;
    }


/**
**********************************************************************************************
Gets the value to be shown in the column <i>code</i> for the given <i>resource</i>
@param resource the resource to get the value for
@param code the code of the column to show
@return the value to display
**********************************************************************************************
**/
  public Object getColumnValue(Resource resource, char code) {
    if (code == 'O'){
      return resource.getOriginal();
      }
    else if (code == 'T'){
      return resource.getTranslated();
      }
    else if (code == 'a'){
      return resource.getAddedIcon();
      }
    else if (code == 'r'){
      return resource.getRenamedIcon();
      }
    else if (code == 'P' && resource instanceof Resource_Property){
      return ((Resource_Property)resource).getProperty();
      }
    else {
      return null;
      }
    }


/**
**********************************************************************************************
Gets the value to be shown in the column <i>code</i> for the given <i>resource</i> (AS A STRING - for the icons!)
AT THE MOMENT, ONLY USED FOR THE MESSAGE IN THE STATUSBAR ON HOVER IN THE FILELISTTABLE
@param resource the resource to get the value for
@param code the code of the column to show
@return the value to display
**********************************************************************************************
**/
  /*
  public String getColumnValueString(Resource resource, char code) {
    if (code == 'i'){
      return "";
      }
    else if (code == 'a'){
      if (resource.isAdded()){
        return "true";
        }
      return "false";
      }
    else if (code == 'r'){
      if (resource.isRenamed()){
        return "true";
        }
      return "false";
      }
    else if (code == 'R'){
      if (resource.isRenamed()){
        return "true";
        }
      return "false";
      }
    else {
      return getColumnValue(resource,code).toString();
      }
    }
  */


/**
**********************************************************************************************
Gets the description of the plugin, such as the games and platforms that are supported, and the
functions that can be performed.
@return the description of this plugin
**********************************************************************************************
**/
  public String getDescription(){

    String description = toString() + "\n\n" + Language.get("Description_ArchivePlugin");


    if (games.length <= 0){
      description += "\n\n" + Language.get("Description_NoDefaultGames");
      }
    else {
      description += "\n\n" + Language.get("Description_DefaultGames");

      for (int i=0;i<games.length;i++){
        description += "\n -" + games[i];
        }

      }


    if (platforms.length <= 0){
      description += "\n\n" + Language.get("Description_NoDefaultPlatforms");
      }
    else {
      description += "\n\n" + Language.get("Description_DefaultPlatforms");

      for (int i=0;i<platforms.length;i++){
        description += "\n -" + platforms[i];
        }

      }


    if (extensions.length <= 0 || extensions[0].length() == 0){
      description += "\n\n" + Language.get("Description_NoDefaultExtensions");
      }
    else {
      description += "\n\n" + Language.get("Description_DefaultExtensions") + "\n";

      for (int i=0;i<extensions.length;i++){
        if (i > 0){
          description += " *." + extensions[i];
          }
        else {
          description += "*." + extensions[i];
          }
        }

      }


    description += "\n\n" + Language.get("Description_SupportedOperations");
    if (canRead){
      description += "\n - " + Language.get("Description_ReadOperation");
      }
    if (canWrite){
      description += "\n - " + Language.get("Description_WriteOperation");
      }
    if (canRename){
      description += "\n - " + Language.get("Description_RenameOperation");
      }


    if (allowsUnicode){
      description += "\n\n" + Language.get("Description_Unicode");
      }
    else {
      description += "\n\n" + Language.get("Description_ASCII");
      }


    if (! isEnabled()){
      description += "\n\n" + Language.get("Description_PluginDisabled");
      }
    else {
      description += "\n\n" + Language.get("Description_PluginEnabled");
      }

    return description;

    }


/**
**********************************************************************************************
Gets a file with the same name, but different extension, to the <i>source</i>
@param source the source file to use as the name and diretory base
@param extension the new extension
@return the file with the same name, different extension
@throws WSPluginException if the file does not exist.
**********************************************************************************************
**/
  public static File getDirectoryFile(File source, String extension) throws WSPluginException {
    String pathName = source.getPath();
    int dotPos = pathName.lastIndexOf(".");
    if (dotPos < 0){
      throw new WSPluginException("Missing Directory File");
      }

    File path = new File(pathName.substring(0,dotPos) + "." + extension);
    if (! path.exists()){
      throw new WSPluginException("Missing Directory File");
      }

    return path;
    }


/**
**********************************************************************************************
Gets the extension at position <i>num</i> of the array
@param num the extension number
@return the extension
**********************************************************************************************
**/
  public String getExtension(int num) {
    if (num < extensions.length){
      return extensions[num];
      }
    else {
      return "unk";
      }
    }


/**
**********************************************************************************************
Gets all the extensions
@return the extensions
**********************************************************************************************
**/
  public String[] getExtensions() {
    return extensions;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean allowsUnicode() {
    return allowsUnicode;
    }


/**
**********************************************************************************************
Gets a list of the extensions
@return the list
**********************************************************************************************
**/
  public String getExtensionsList() {
    String list = "";

    for (int i=0;i<extensions.length;i++){
      if (i > 0){
        list += ", ";
        }
      list += "*." + extensions[i];
      }

    return list;
    }


/**
**********************************************************************************************
Gets all the games
@return the games
**********************************************************************************************
**/
  public String[] getGames() {
    return games;
    }


/**
**********************************************************************************************
Gets a list of the games
@return the list
**********************************************************************************************
**/
  public String getGamesList() {
    String list = "";

    for (int i=0;i<games.length;i++){
      if (i > 0){
        list += ", ";
        }
      list += games[i];
      }

    return list;
    }


/**
**********************************************************************************************
Converts a length from bytes into kilobytes
@param length the length in bytes
@return the length in kilobytes
**********************************************************************************************
**/
  public long getLengthKB(long length){
      if (length == 0){
        return 0;
        }

      length = length / 1024;

      if (length == 0){
        return 1;
        }
      else {
        return length;
        }
     }


/**
**********************************************************************************************
Gets the percentage chance that this plugin can read the <i>file</i>
@param file the file to analyse
@return the percentage (0-100) chance
**********************************************************************************************
**/
  public int getMatchRating(File file){
    try {
      FileManipulator fm = new FileManipulator(file,"r");
      int rating = getMatchRating(fm);
      fm.close();
      return rating;
      }
    catch (Throwable t){
      return 0;
      }
    }


/**
**********************************************************************************************
Gets the percentage chance that this plugin can read the file <i>fm</i>
@param fm the file to analyse
@return the percentage (0-100) chance
**********************************************************************************************
**/
  public abstract int getMatchRating(FileManipulator fm);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getNumProperties() {
    return properties.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_Property[] getProperties() {
    return properties;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setProperties(Resource_Property[] properties) {
    this.properties = properties;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String[] getPlatforms() {
    return platforms;
    }


/**
**********************************************************************************************
Gets a list of the platforms
@return the list
**********************************************************************************************
**/
  public String getPlatformsList() {
    String list = "";

    for (int i=0;i<platforms.length;i++){
      if (i > 0){
        list += ", ";
        }
      list += platforms[i];
      }

    return list;
    }


/**
**********************************************************************************************
Gets only the columns that are being shown in the FileListPanel (ie not the columns that have
been hidden in the Settings.)
@return the columns that will be shown
**********************************************************************************************
**/
  public WSTableColumn getViewingColumn(int column) {
    WSTableColumn[] columns = getColumns();

    int numCols = 0;
    for (int i=0;i<columns.length;i++){
      if (Settings.getBoolean("ShowTableColumn_" + columns[i].getCode())){
        if (numCols == column){
          return columns[i];
          }
        numCols++;
        }
      }

    return null;
    }


/**
**********************************************************************************************
Gets only the columns that are being shown in the FileListPanel (ie not the columns that have
been hidden in the Settings.)
@return the columns that will be shown
**********************************************************************************************
**/
  public WSTableColumn getViewingPropColumn(int column) {
    WSTableColumn[] columns = getViewingPropColumns();

    int numCols = 0;
    for (int i=0;i<columns.length;i++){
      if (numCols == column){
        return columns[i];
        }
      numCols++;
      }

    return null;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn[] getViewingPropColumns() {

    WSTableColumn[] columns = new WSTableColumn[4];


    //code,languageCode,class,editable,sortable
    columns[0] = new WSTableColumn("RenamedIcon",'r',Icon.class  ,false,false,18,18); //renamed
    columns[1] = new WSTableColumn("Property"   ,'P',String.class,false,true);
    columns[2] = new WSTableColumn("Original"   ,'O',String.class,false,true);
    columns[3] = new WSTableColumn("Translated" ,'T',String.class,true ,true);


    int numTexts = 0;
    for (int i=0;i<columns.length;i++){
      if (Settings.getBoolean("ShowTableColumn_" + columns[i].getCode())){
        columns[numTexts] = columns[i];
        numTexts++;
        }
      }

    if (numTexts < columns.length){
      WSTableColumn[] temp = columns;
      columns = new WSTableColumn[numTexts];
      System.arraycopy(temp,0,columns,0,numTexts);
      }

    return columns;
    }


/**
**********************************************************************************************
Gets only the columns that are being shown in the FileListPanel (ie not the columns that have
been hidden in the Settings.
@return the columns that will be shown
**********************************************************************************************
**/
  public WSTableColumn[] getViewingColumns() {
    WSTableColumn[] columns = getColumns();

    int numColumns = columns.length;
    if (!Settings.getBoolean("ShowCustomColumns")){
      numColumns = 4;
      }

    int numTexts = 0;
    for (int i=0;i<numColumns;i++){
      if (Settings.getBoolean("ShowTableColumn_" + columns[i].getCode())){
        columns[numTexts] = columns[i];
        numTexts++;
        }
      }

    if (numTexts < columns.length){
      WSTableColumn[] temp = columns;
      columns = new WSTableColumn[numTexts];
      System.arraycopy(temp,0,columns,0,numTexts);
      }

    return columns;
    }


/**
**********************************************************************************************
Reads the archive <i>source</i>
@param source the archive file
@return the resources in the archive
**********************************************************************************************
**/
  public abstract Resource[] read(File source);


/**
**********************************************************************************************
Writes the <i>resources</i> to the archive <i>destination</i>, where the archive was opened and
modified (as opposed to write() which writes an archive from scratch). This directs to
write(resources,destination) if the method is not overwritten
@param resources the files to write
@param destination the place to store the archive
**********************************************************************************************
**/
  public void rename(Resource[] resources, File destination) {
    write(resources,destination);
    }


/**
**********************************************************************************************
Resizes the <i>resources</i> array to the new size, where <i>numResources</i> MUST be smaller
than the current array length.
@param resources the array to resize
@param numResources the new size of the array
@return the resized array
**********************************************************************************************
**/
  public static Resource[] resizeResources(Resource[] resources, int numResources) {
    Resource[] temp = resources;
    resources = new Resource[numResources];
    System.arraycopy(temp,0,resources,0,numResources);
    return resources;
    }


/**
**********************************************************************************************
Sets whether this plugin can read archives or not
@param canRead is reading allowed?
**********************************************************************************************
**/
  public void setCanRead(boolean canRead) {
    this.canRead = canRead;
    }


/**
**********************************************************************************************
Sets whether this plugin can rename files in an archive
@param canRename is replacing allowed?
**********************************************************************************************
**/
  public void setCanRename(boolean canRename) {
    this.canRename = canRename;
    }


/**
**********************************************************************************************
Sets whether this plugin can rename files in an archive
@param canRename is replacing allowed?
**********************************************************************************************
**/
  public void setCanReplace(boolean canReplace) {
    this.canRename = canReplace;
    }


/**
**********************************************************************************************
Sets whether this plugin can write archives
@param canWrite is writing allowed?
**********************************************************************************************
**/
  public void setCanWrite(boolean canWrite) {
    this.canWrite = canWrite;
    }


/**
**********************************************************************************************
Gets the value of the <i>resource</i> corresponding to the column <i>code</i>
@param resource the resource to change the value of
@param code the code of the column corresponding to the data being changed
@param value the new value for the field.
**********************************************************************************************
**/
  public void setColumnValue(Resource resource, char code, Object value) {
    if (code == 'O'){
      resource.setOriginal((String)value);
      }
    else if (code == 'T'){
      resource.setTranslated((String)value);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setExtensions(String ... extensions) {
    this.extensions = extensions;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setGames(String ... games) {
    this.games = games;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setPlatforms(String ... platforms) {
    this.platforms = platforms;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setProperties(boolean canRead, boolean canWrite, boolean canRename) {
    this.canRead = canRead;
    this.canWrite = canWrite;
    this.canRename = canRename;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setAllowsUnicode(boolean unicode) {
    this.allowsUnicode = unicode;
    }


/**
**********************************************************************************************
Writes the <i>resources</i> to the archive <i>destination</i>, where the archive was constructed
from scratch (as opposed to rename() which writes an archive that was already opened). If
<i>allowImplicitReplacing</i> is enabled, it will write the archive without the need for
overwriting this method.
@param resources the files to write
@param destination the place to store the archive
**********************************************************************************************
**/
  public void write(Resource[] resources, File destination) {
    // Should be overwritten by the plugin
    }


/**
**********************************************************************************************
Writes the <i>resource</i> into the <i>destination</i> archive
@param resource the file to write
@param destination the archive to write to.
**********************************************************************************************
**/
  public static void write(Resource resource, FileManipulator destination) {
    write(new Resource[]{resource},destination);
    }


/**
**********************************************************************************************
Writes the <i>resources</i> into the <i>destination</i> archive
@param resources the files to write
@param destination the archive to write to.
**********************************************************************************************
**/
  public static void write(Resource[] resources, FileManipulator destination) {
    for (int i=0;i<resources.length;i++){
      write(resources[i],destination);
      WSProgressDialog.setValue(i);
      }
    }


/**
**********************************************************************************************
Records the error/exception stack trace in the log file. If debug is enabled, it will also
write the error to the <i>System.out</i> command prompt
@param t the <i>Throwable</i> error/exception
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }


  }