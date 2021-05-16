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
import org.watto.event.*;
import org.watto.manipulator.FileManipulator;
import org.watto.manipulator.FileExtensionFilter;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import javax.swing.*;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_DirectoryList extends WSPanelPlugin implements WSSelectableInterface,
                                                                      WSEnterableInterface {

  /** The current controls **/
  WSPanel currentControl;

  // Panels and buttons for the control subgroups at the bottom of the sidepanel
  /** The controls for reading archives **/
  WSPanel readControls;
  /** The controls for modifying archives **/
  WSPanel modifyControls;
  /** The controls for converting archives **/
  WSPanel convertControls;
  /** The controls for writing archives **/
  WSPanel writeControls;

  /** The controls to show when tying to write/modify archives that are uneditable **/
  WSPanel invalidControls;
  /** The controls to show when saving a new archive (tells them to use Convert instead) **/
  WSPanel saveNewArchiveControls;

  /** Holder for the controls **/
  WSPanel controlHolder;
  /** Holder for the directory list **/
  WSDirectoryListHolder dirHolder;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_DirectoryList(){
    super(new XMLNode());
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
@param caller the object that contains this component, created this component, or more formally,
              the object that receives events from this component.
**********************************************************************************************
**/
  public SidePanel_DirectoryList(XMLNode node){
    super(node);
    }



///////////////
//
// Configurable
//
///////////////


/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    super.buildObject(node);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_DirectoryList.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);

    loadControlPanels();

    controlHolder = (WSPanel)WSRepository.get("SidePanel_DirectoryList_ControlsHolder");
    dirHolder = (WSDirectoryListHolder)WSRepository.get("SidePanel_DirectoryList_DirectoryListHolder");

    //changeControls("ReadPanel",false);

    //loadDirList();
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    return super.buildXML();
    }


/**
**********************************************************************************************
Registers the events that this component generates
**********************************************************************************************
**/
  public void registerEvents(){
    super.registerEvents();
    }



///////////////
//
// Class-Specific Methods
//
///////////////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addFiles(){
    try {
      int numFiles = Integer.parseInt(((WSTextField)WSRepository.get("SidePanel_DirectoryList_NumFilesToAdd")).getText());
      addFiles(numFiles);
      }
    catch (Throwable t){
      addFiles(-1);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addFiles(int numFiles){
    ArchivePlugin plugin = Archive.getReadPlugin();
    if (plugin != null){
      if (!plugin.canWrite()){
        WSPopup.showMessage("ModifyArchive_NotWritable",true);
        return;
        }
      }

    Task_AddFiles task = new Task_AddFiles(numFiles);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();
    UndoManager.add(task);
    }


/**
**********************************************************************************************
Changes the control bar to a different panel
@param controlName the name of the control panel to load
@param fullVersionOnly true if the panel to change to is only a full version feature
**********************************************************************************************
**/
  public void changeControls(String controlName, boolean fullVersionOnly) {

    boolean fullVersion = true;
    if (fullVersionOnly){
      if (!checkFullVersion(false)){
        //return;
        fullVersion = false;
        }
      }

    WSPanel newControl = currentControl;

    // reset the filter on the directoryList
    setFileFilter(null);


    boolean invalid = false;
    boolean savingNewArchive = false;
    if (controlName.equals("ReadPanel")){
      newControl = readControls;
      loadReadPlugins();
      setPluginFilter("SidePanel_DirectoryList_ReadPluginList","CurrentSelectedReadPlugin");
      }
    else if (controlName.equals("ModifyPanel")){
      if (!Archive.getReadPlugin().canWrite() && !Archive.getReadPlugin().canReplace()){
        invalid = true;
        }
      else {
        newControl = modifyControls;
        }
      }
    else if (controlName.equals("ConvertPanel")){
      newControl = convertControls;
      if (fullVersion){
        loadConvertPlugins();
        setConvertFilename(dirHolder.getSelectedFile());
        setPluginFilter("SidePanel_DirectoryList_ConvertPluginList");
        }
      }
    else if (controlName.equals("WritePanel")){
      newControl = writeControls;
      if (fullVersion){
        if (!Archive.getReadPlugin().canWrite() && !Archive.getReadPlugin().canReplace()){
          invalid = true;
          }
        else if (Archive.getReadPlugin() instanceof AllFilesPlugin){
          // display a message saying that it is a new archive, so use the Convert panel so they can choose a plugin
          savingNewArchive = true;
          }
        else {
          setWriteFilename(dirHolder.getSelectedFile());
          }
        }
      }


    controlHolder.removeAll();


    if (invalid){
      // the panel is invalid. eg WritePanel is disabled for non-writable archives
      controlHolder.add(invalidControls,BorderLayout.NORTH);
      }
    else if (savingNewArchive){
      controlHolder.add(saveNewArchiveControls,BorderLayout.NORTH);
      }
    else {
      currentControl = newControl;
      controlHolder.add(currentControl,BorderLayout.NORTH);
      }

    controlHolder.revalidate();
    controlHolder.repaint();

    Settings.set("SidePanel_DirectoryList_CurrentControl",controlName);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean checkFullVersion() {
    return checkFullVersion(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean checkFullVersion(boolean showPopup) {
    try {
      new FullVersionVerifier();
      return true;
      }
    catch (Throwable t){
      // basic version
      if (showPopup){
        WSPopup.showError("FullVersionOnly",true);
        }
      return false;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void checkInvalidControls(){
    WSPanel newControl = currentControl;
    if (currentControl == null){
      return;
      }

    String controlName = Settings.getString("SidePanel_DirectoryList_CurrentControl");
    if (controlName.equals("ModifyPanel") || controlName.equals("WritePanel")){

      if (currentControl != invalidControls && (!Archive.getReadPlugin().canWrite() && !Archive.getReadPlugin().canReplace())){
        // on one of these panels already, but these controls aren't allowed for this archive.
        // change it to the invalid controls
        newControl = invalidControls;
        }
      else if (currentControl == invalidControls){
        // change it to the normal panel
        if (controlName.equals("ModifyPanel")){
          newControl = modifyControls;
          }
        else if (controlName.equals("WritePanel")){
          newControl = writeControls;
          }
        }
      }


    controlHolder.removeAll();
    controlHolder.add(newControl,BorderLayout.NORTH);
    controlHolder.revalidate();
    controlHolder.repaint();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void convertArchive(){
    WSComboBox convertPlugins = (WSComboBox)WSRepository.get("SidePanel_DirectoryList_ConvertPluginList");
    Object pluginObj = convertPlugins.getSelectedItem();
    if (pluginObj == null || !(pluginObj instanceof PluginList)){
      return;
      }
    ArchivePlugin plugin = ((PluginList)pluginObj).getPlugin();


    String dirName = dirHolder.getCurrentDirectory().getAbsolutePath();
    String filename = ((WSTextField)WSRepository.get("SidePanel_DirectoryList_ConvertFilenameField")).getText();

    if (filename == null || filename.equals("")){
      WSPopup.showError("ConvertArchive_FilenameMissing",true);
      return;
      }

    // append the default extension, if no extension exists
    if (FileManipulator.getExtension(filename).equals("")){
      filename += "." + plugin.getExtension(0);
      }

    if (filename.indexOf(dirName) >= 0){
      }
    else {
      filename = dirName + File.separator + filename;
      }

    File outputPath = new File(filename);

    convertArchive(outputPath,plugin);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void convertArchive(File outputPath, ArchivePlugin plugin){
    if (plugin != Archive.getReadPlugin()){
      plugin.setDefaultProperties(true);
      }
    writeArchive(outputPath,plugin);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadControlPanels() {
    readControls = (WSPanel)WSHelper.buildComponent(XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_DirectoryList_ReadPanel.wsd")));

    if (checkFullVersion(false)){
      // full version panels
      modifyControls = (WSPanel)WSHelper.buildComponent(XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_DirectoryList_ModifyPanel.wsd")));
      convertControls = (WSPanel)WSHelper.buildComponent(XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_DirectoryList_ConvertPanel.wsd")));
      writeControls = (WSPanel)WSHelper.buildComponent(XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_DirectoryList_WritePanel.wsd")));
      }
    else {
      // basic panels
      modifyControls = (WSPanel)WSHelper.buildComponent(XMLReader.readString("<WSPanel code=\"SidePanel_DirectoryList_ModifyPanel_Main\" showBorder=\"true\" layout=\"BorderLayout\"><WSLabel code=\"SidePanel_DirectoryList_ModifyPanel_Basic\" wrap=\"true\" vertical-alignment=\"true\" height=\"80\" position=\"CENTER\" /></WSPanel>"));
      convertControls = (WSPanel)WSHelper.buildComponent(XMLReader.readString("<WSPanel code=\"SidePanel_DirectoryList_ConvertPanel_Main\" showBorder=\"true\" layout=\"BorderLayout\"><WSLabel code=\"SidePanel_DirectoryList_ConvertPanel_Basic\" wrap=\"true\" vertical-alignment=\"true\" height=\"80\" position=\"CENTER\" /></WSPanel>"));
      writeControls = (WSPanel)WSHelper.buildComponent(XMLReader.readString("<WSPanel code=\"SidePanel_DirectoryList_WritePanel_Main\" showBorder=\"true\" layout=\"BorderLayout\"><WSLabel code=\"SidePanel_DirectoryList_WritePanel_Basic\" wrap=\"true\" vertical-alignment=\"true\" height=\"80\" position=\"CENTER\" /></WSPanel>"));
      }

    invalidControls = (WSPanel)WSHelper.buildComponent(XMLReader.readString("<WSPanel code=\"SidePanel_DirectoryList_ReadPanel_Main\" repository=\"false\" showBorder=\"true\" layout=\"BorderLayout\"><WSLabel code=\"SidePanel_DirectoryList_InvalidControls\" wrap=\"true\" vertical-alignment=\"true\" height=\"80\" position=\"CENTER\" /></WSPanel>"));
    saveNewArchiveControls = (WSPanel)WSHelper.buildComponent(XMLReader.readString("<WSPanel code=\"SidePanel_DirectoryList_ReadPanel_Main\" repository=\"false\" showBorder=\"true\" layout=\"BorderLayout\"><WSLabel code=\"SidePanel_DirectoryList_SaveNewArchiveControls\" wrap=\"true\" vertical-alignment=\"true\" height=\"80\" position=\"CENTER\" /></WSPanel>"));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadConvertPlugins() {

    WSComboBox convertPluginList = (WSComboBox)WSRepository.get("SidePanel_DirectoryList_ConvertPluginList");
    convertPluginList.setModel(new DefaultComboBoxModel(PluginListBuilder.getWritePluginList()));

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDirList() {
    //dirHolder.loadPanel(Settings.get("DirectoryListView"));
    //dirHolder.setMultipleSelection(false);

    //dirHolder.revalidate();
    //dirHolder.repaint();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadReadPlugins() {

    WSComboBox readPluginList = (WSComboBox)WSRepository.get("SidePanel_DirectoryList_ReadPluginList");

    readPluginList.setModel(new DefaultComboBoxModel(PluginListBuilder.getPluginList()));
    readPluginList.addItem(new PluginList(Language.get("AllFiles"),new AllFilesPlugin()));
    //readPluginList.setSelectedIndex(readPluginList.getItemCount()-1);

    int selectedItem = TemporarySettings.getInt("CurrentSelectedReadPlugin");
    if (selectedItem != -1){
      readPluginList.setSelectedIndex(selectedItem);
      }
    else {
      readPluginList.setSelectedIndex(readPluginList.getItemCount()-1);
      }

    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c instanceof WSButton){
      String code = ((WSButton)c).getCode();

      if (code == null){
        return false;
        }

      // Control Panels

      else if (code.equals("SidePanel_DirectoryList_ReadPanelButton")){
        // read panel
        changeControls("ReadPanel",false);
        }
      else if (code.equals("SidePanel_DirectoryList_ModifyPanelButton")){
        // modify panel
        changeControls("ModifyPanel",true);
        }
      else if (code.equals("SidePanel_DirectoryList_ConvertPanelButton")){
        // convert panel
        changeControls("ConvertPanel",true);
        }
      else if (code.equals("SidePanel_DirectoryList_WritePanelButton")){
        // write panel
        changeControls("WritePanel",true);
        }

      // Buttons on the Control Panels
      else if (code.equals("SidePanel_DirectoryList_ReadArchiveButton")){
        // read archive
        readArchive();
        }
      else if (code.equals("SidePanel_DirectoryList_AddFileButton")){
        // add files
        if (checkFullVersion()){
          addFiles();
          }
        }
      else if (code.equals("SidePanel_DirectoryList_RemoveFileButton")){
        // remove files
        if (checkFullVersion()){
          removeFiles();
          }
        }
      else if (code.equals("SidePanel_DirectoryList_WriteArchiveButton")){
        // write archive
        if (checkFullVersion()){
          writeArchive();
          }
        }
      else if (code.equals("SidePanel_DirectoryList_ConvertArchiveButton")){
        // convert archive
        if (checkFullVersion()){
          convertArchive();
          }
        }

      else {
        return false;
        }

      // returns true even if not the full version,
      // because the click was still handled by this class.
      return true;

      }

    else if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code == null){
        return false;
        }

      if (code.equals("DirectoryList")){
        // directory list
        if (currentControl == writeControls){
          setWriteFilename(dirHolder.getSelectedFile());
          }
        else if (currentControl == convertControls){
          setConvertFilename(dirHolder.getSelectedFile());
          }
        else {
          return false;
          }
        return true;
        }

      }

    return false;
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be closed. This method
does nothing by default, but can be overwritten to do anything else needed before the panel is
closed, such as garbage collecting and closing pointers to temporary objects.
**********************************************************************************************
**/
  public void onCloseRequest(){
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDeselect(JComponent c, Object e){
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSDoubleClickableListener when a double click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDoubleClick(JComponent c, MouseEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code == null){
        return false;
        }


      if (code.equals("DirectoryList")){
        // perform double click on the directory list

        File selected = dirHolder.getSelectedFile();

        // read
        if (currentControl == readControls){
          if (! selected.isDirectory()){
            readArchive(selected);
            }
          return true;
          }

        // double click reads file by default (if setting is enabled)
        // it is here so that it is checked AFTER the script, thus allowing
        // double-clicks to be handled by the script panel if it is open
        else if (Settings.getBoolean("OpenArchiveOnDoubleClick")){
          if (! selected.isDirectory()){
            readArchive(selected);
            }
          return true;
          }

        // modify (add)
        else if (currentControl == modifyControls){
          if (checkFullVersion(false)){
            addFiles();
            }
          return true;
          }

        }

      }
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSEnterableListener when a key is pressed
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onEnter(JComponent c, java.awt.event.KeyEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code.equals("SidePanel_DirectoryList_ConvertFilenameField")){
        if (checkFullVersion()){
          convertArchive();
          }
        }
      else if (code.equals("SidePanel_DirectoryList_NumFilesToAdd")){
        if (checkFullVersion()){
          addFiles();
          }
        }
      else if (code.equals("SidePanel_DirectoryList_WriteFilenameField")){
        if (checkFullVersion()){
          writeArchive();
          }
        }
      return true;
      }
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSKeyableListener when a key press occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    return false;
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be opened. By default,
it just calls checkLoaded(), but can be overwritten to do anything else needed before the
panel is displayed, such as resetting or refreshing values.
**********************************************************************************************
**/
  public void onOpenRequest(){
    String controlName = Settings.getString("SidePanel_DirectoryList_CurrentControl");
    boolean fullVersionOnly = true;
    if (controlName.equals("ReadPanel")){
      fullVersionOnly = false;
      }
    changeControls(controlName,fullVersionOnly);

    dirHolder.checkFiles();
    dirHolder.scrollToSelected();
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is selected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onSelect(JComponent c, Object e){
    if (c instanceof WSComboBox){
      String code = ((WSComboBox)c).getCode();

      if (code.equals("SidePanel_DirectoryList_ReadPluginList")){
        // read plugin list
        setPluginFilter("SidePanel_DirectoryList_ReadPluginList","CurrentSelectedReadPlugin");
        }
      else if (code.equals("SidePanel_DirectoryList_ConvertPluginList")){
        // convert plugin list
        setPluginFilter("SidePanel_DirectoryList_ConvertPluginList");
        setConvertFilename();
        }

      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void readArchive(){
    File selectedFile = dirHolder.getSelectedFile();
    readArchive(selectedFile);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void readArchive(File selectedFile){
    WSComboBox readPlugins = (WSComboBox)WSRepository.get("SidePanel_DirectoryList_ReadPluginList");
    PluginList selectedItem = (PluginList)readPlugins.getSelectedItem();

    WSPlugin plugin = null;
    if (selectedItem != null){
      plugin = selectedItem.getPlugin();
      }
    else {
      plugin = new AllFilesPlugin();
      }

    if (plugin instanceof AllFilesPlugin){
      // auto-detect a plugin
      Task_ReadArchive task = new Task_ReadArchive(selectedFile);
      task.setDirection(UndoTask.DIRECTION_REDO);
      new Thread(task).start();
      }
    else {
      // open with the chosen plugin
      Task_ReadArchiveWithPlugin task = new Task_ReadArchiveWithPlugin(selectedFile,plugin);
      task.setDirection(UndoTask.DIRECTION_REDO);
      new Thread(task).start();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadDirectoryList() {
    dirHolder.reload();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void removeFiles(){
    Resource[] selectedFiles = ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getAllSelectedFiles();
    removeFiles(selectedFiles);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void removeFiles(Resource[] selectedFiles){
    ArchivePlugin plugin = Archive.getReadPlugin();
    if (plugin != null){
      if (!plugin.canWrite()){
        WSPopup.showMessage("ModifyArchive_NotWritable",true);
        return;
        }
      }

    Task_RemoveFiles task = new Task_RemoveFiles(selectedFiles);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();
    UndoManager.add(task);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void requestFocus(){
    dirHolder.requestFocus();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setFileFilter(FileFilter filter) {
    dirHolder.setMatchFilter(filter);
    dirHolder.reload();
    dirHolder.scrollToSelected();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setConvertFilename(){
    WSTextField field = (WSTextField)WSRepository.get("SidePanel_DirectoryList_ConvertFilenameField");
    String filename = field.getText();

    if (filename == null || filename.length() <= 0){
      setConvertFilename(dirHolder.getSelectedFile());
      }
    else {
      setConvertFilename(new File(filename));
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setConvertFilename(File file){
    if (file.isDirectory()){
      return;
      }

    WSTextField field = (WSTextField)WSRepository.get("SidePanel_DirectoryList_ConvertFilenameField");
    String filename = field.getText();

    int dotPos = filename.lastIndexOf(".");
    int slashPos = filename.lastIndexOf("\\");
    if (slashPos < 0){
      slashPos = filename.lastIndexOf("/");
      }

    if (dotPos > 0 && dotPos > slashPos){
      filename = filename.substring(0,dotPos);
      }
    else {
      filename = "newLanguage";
      }


    WSComboBox convertPluginList = (WSComboBox)WSRepository.get("SidePanel_DirectoryList_ConvertPluginList");

    String extension = "unk";

    Object selectedItem = convertPluginList.getSelectedItem();
    if (selectedItem != null){
      ArchivePlugin plugin = ((PluginList)(selectedItem)).getPlugin();
      if (plugin != null){
        extension = plugin.getExtension(0);
        if (extension == null || extension.length() == 0 || extension.equals("*")){
          extension = "unk";
          }
        }
      }

    filename += "." + extension;

    field.setText(filename);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setWriteFilename(File file){
    if (file.isDirectory()){
      return;
      }

    WSTextField field = (WSTextField)WSRepository.get("SidePanel_DirectoryList_WriteFilenameField");
    String filename = field.getText();

    int dotPos = filename.lastIndexOf(".");
    int slashPos = filename.lastIndexOf("\\");
    if (slashPos < 0){
      slashPos = filename.lastIndexOf("/");
      }

    if (slashPos > 0 && dotPos > slashPos){
      filename = filename.substring(slashPos+1);
      }
    else {
      filename = "newLanguage";
      }

    dotPos = filename.lastIndexOf(".");
    if (dotPos <= 0){
      String extension = Archive.getReadPlugin().getExtension(0);
      if (extension == null || extension.length() == 0 || extension.equals("*")){
        extension = "unk";
        }
      filename += "." + extension;
      }

    field.setText(filename);
    }


/**
**********************************************************************************************
sets the filter to the filter obtained from the WSComboBox with the given code name
**********************************************************************************************
**/
  public void setPluginFilter(String comboBoxCode) {
    setPluginFilter(comboBoxCode,"");
    }


/**
**********************************************************************************************
sets the filter to the filter obtained from the WSComboBox with the given code name
**********************************************************************************************
**/
  public void setPluginFilter(String comboBoxCode, String settingCode) {

    WSComboBox combo = (WSComboBox)WSRepository.get(comboBoxCode);

    PluginList list = (PluginList)combo.getSelectedItem();
    if (list == null || ! combo.isEnabled()){
      setFileFilter(null);
      }
    else {
      setFileFilter(new PluginFinderMatchFileFilter(list.getPlugin()));
      }

    if (settingCode != null && !settingCode.equals("")){
      TemporarySettings.set(settingCode,combo.getSelectedIndex());
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeArchive(){
    ArchivePlugin plugin = Archive.getReadPlugin();

    String dirName = dirHolder.getCurrentDirectory().getAbsolutePath();
    String filename = ((WSTextField)WSRepository.get("SidePanel_DirectoryList_WriteFilenameField")).getText();

    if (filename == null || filename.equals("")){
      WSPopup.showError("WriteArchive_FilenameMissing",true);
      return;
      }

    // append the default extension, if no extension exists
    if (FileManipulator.getExtension(filename).equals("")){
      filename += "." + plugin.getExtension(0);
      }

    if (filename.indexOf(dirName) >= 0){
      }
    else {
      filename = dirName + File.separator + filename;
      }

    File outputPath = new File(filename);

    writeArchive(outputPath,plugin);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeArchive(File outputPath, ArchivePlugin plugin){
    Task_WriteArchive task = new Task_WriteArchive(outputPath,plugin);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();
    }



///////////////
//
// Default Implementations
//
///////////////


/**
**********************************************************************************************
Gets the plugin description
**********************************************************************************************
**/
  public String getDescription(){
    String description = toString() + "\n\n" + Language.get("Description_SidePanel");

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
Gets the plugin name
**********************************************************************************************
**/
  public String getText(){
    return super.getText();
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves over an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, MouseEvent e){
    return super.onHover(c,e);
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves out of an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, MouseEvent e){
    return super.onHoverOut(c,e);
    }


/**
**********************************************************************************************
Sets the description of the plugin
@param description the description
**********************************************************************************************
**/
  public void setDescription(String description){
    super.setDescription(description);
    }



  }