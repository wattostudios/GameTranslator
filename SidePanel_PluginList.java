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
import org.watto.manipulator.FileExtensionFilter;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_PluginList extends WSPanelPlugin implements WSSelectableInterface {


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_PluginList(){
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
  public SidePanel_PluginList(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_PluginList.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);

    loadComboBox();
    changePluginType();
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
  public void changePluginType(){
    WSComboBox pluginTypes = (WSComboBox)WSRepository.get("SidePanel_PluginList_PluginTypes");
    changePluginType((String)pluginTypes.getSelectedItem());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void changePluginType(String type){

    DefaultListModel model = new DefaultListModel();

    WSPlugin[] plugins = new WSPlugin[0];
    if (type.equals(Language.get("Plugin_Archive_Name"))){
      plugins = WSPluginManager.group("Archive").getPlugins();
      }
    else if (type.equals(Language.get("Plugin_DirectoryList_Name"))){
      plugins = WSPluginManager.group("DirectoryList").getPlugins();
      }
    else if (type.equals(Language.get("Plugin_FileList_Name"))){
      plugins = WSPluginManager.group("FileList").getPlugins();
      }
    else if (type.equals(Language.get("Plugin_OptionGroup_Name"))){
      plugins = WSPluginManager.group("Options").getPlugins();
      }
    else if (type.equals(Language.get("Plugin_SidePanel_Name"))){
      plugins = WSPluginManager.group("SidePanel").getPlugins();
      }


    if (Settings.getBoolean("SortPluginLists")){
      java.util.Arrays.sort(plugins);
      }


    model.ensureCapacity(plugins.length);
    for (int i=0;i<plugins.length;i++){
      model.addElement(plugins[i]);
      }


    WSList pluginsList = (WSList)WSRepository.get("SidePanel_PluginList_Plugins");
    pluginsList.clearSelection();
    pluginsList.setModel(model);

    WSTextArea descriptionField = (WSTextArea)WSRepository.get("SidePanel_PluginList_PluginDescription");
    descriptionField.setText("");

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadComboBox(){

    WSComboBox pluginTypes = (WSComboBox)WSRepository.get("SidePanel_PluginList_PluginTypes");

    pluginTypes.removeAllItems();

    String[] types = new String[]{"Archive","DirectoryList","FileList","OptionGroup","SidePanel"};

    String[] items = new String[types.length];
    for (int i=0;i<types.length;i++){
      items[i] = Language.get("Plugin_" + types[i] + "_Name");
      }

    if (Settings.getBoolean("SortPluginLists")){
      java.util.Arrays.sort(items);
      }

    pluginTypes.setModel(new DefaultComboBoxModel(items));
    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
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
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is selected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onSelect(JComponent c, Object e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code.equals("SidePanel_PluginList_Plugins")){
        showPluginDescription();
        return true;
        }
      else if (code.equals("SidePanel_PluginList_PluginTypes")){
        changePluginType();
        return true;
        }
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void requestFocus(){
    ((WSComboBox)WSRepository.get("SidePanel_PluginList_PluginTypes")).requestFocus();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void showPluginDescription(){
    WSList pluginsList = (WSList)WSRepository.get("SidePanel_PluginList_Plugins");
    showPluginDescription(pluginsList.getSelectedValue());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void showPluginDescription(Object pluginObj){
    if (pluginObj == null){
      return;
      }

    WSPlugin plugin = (WSPlugin)pluginObj;

    WSTextArea descriptionField = (WSTextArea)WSRepository.get("SidePanel_PluginList_PluginDescription");
    descriptionField.setText(plugin.getDescription());
    descriptionField.setCaretPosition(0);
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