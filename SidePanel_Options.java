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
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_Options extends WSPanelPlugin implements WSSelectableInterface, WSMotionableInterface {

  WSPanelPlugin currentGroup = null;
  Object lastMotionObject = null;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_Options(){
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
  public SidePanel_Options(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_Options.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);

    loadOptionGroups();
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
  public void loadGroup(WSPanelPlugin group) {
    if (group == null){
      return;
      }

    if (currentGroup != null){
      currentGroup.onCloseRequest();
      }

    this.currentGroup = group;

    // reload the settings
    currentGroup.revalidate();

    currentGroup.onOpenRequest();

    WSOptionGroupHolder groupHolder = (WSOptionGroupHolder)WSRepository.get("SidePanel_Options_OptionGroupHolder");
    groupHolder.loadPanel(group);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadOptionGroups(){
    WSComboBox groupSelectionList = (WSComboBox)WSRepository.get("SidePanel_Options_OptionGroups");

    if (groupSelectionList.getItemCount() <= 0){
      // need to load the list
      WSPlugin[] plugins = WSPluginManager.group("Options").getPlugins();

      if (Settings.getBoolean("SortPluginLists")){
        java.util.Arrays.sort(plugins);
        }

      groupSelectionList.setModel(new DefaultComboBoxModel(plugins));

      int selected = Settings.getInt("SelectedOptionGroup");
      if (selected >= 0 && selected < plugins.length){
        groupSelectionList.setSelectedIndex(selected);
        }

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
      if (code.equals("SidePanel_Options_SaveOptionsButton")){
        GameTranslator.getInstance().reload();
        WSPopup.showMessage("Options_OptionsSaved",true);
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
    // save the options
    GameTranslator.getInstance().reload();
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
The event that is triggered from a WSMotionableListener when a component is moved over
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onMotion(JComponent c, java.awt.event.MouseEvent e){
    //if (c == lastMotionObject){
    //  return true;
    //  }

    if (c instanceof WSList){
      String code = ((WSList)c).getCode();
      if (code.equals("Option_ToolbarButtons_CurrentList") || code.equals("Option_ToolbarButtons_ChoicesList")){
        Object selectedObject = ((WSList)c).getModel().getElementAt(((WSList)c).locationToIndex(e.getPoint()));
        if (lastMotionObject == selectedObject || selectedObject == null){
          return true; // still over the same object on the list
          }
        lastMotionObject = selectedObject;
        String tooltip = Language.get("WSButton_" + (String)selectedObject + "_Tooltip");

        if (tooltip != null && tooltip.length() > 0){
          WSTextArea detailsArea = (WSTextArea)WSRepository.get("SidePanel_Options_DescriptionField");
          detailsArea.setText(tooltip);
          }
        ((WSStatusBar)WSRepository.get("StatusBar")).setText(tooltip);
        return true;
        }
      }
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
    WSComboBox groupSelectionList = (WSComboBox)WSRepository.get("SidePanel_Options_OptionGroups");
    int selectedGroup = Settings.getInt("SelectedOptionGroup");

    if (selectedGroup >= 0 && selectedGroup < groupSelectionList.getItemCount()){
      if (selectedGroup == groupSelectionList.getSelectedIndex()){
        // reload the current group, because the event isn't triggered if the index is the same
        loadGroup((WSPanelPlugin)groupSelectionList.getSelectedItem());
        }
      else {
        // changing the index will trigger the event to change the option group
        groupSelectionList.setSelectedIndex(selectedGroup);
        }
      }
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

      if (code.equals("SidePanel_Options_OptionGroups")){
        WSComboBox combo = (WSComboBox)c;
        Settings.set("SelectedOptionGroup",combo.getSelectedIndex());

        loadGroup((WSPanelPlugin)combo.getSelectedItem());

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
    ((WSComboBox)WSRepository.get("SidePanel_Options_OptionGroups")).requestFocus();
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
CHANGED
The event that is triggered from a WSHoverableListener when the mouse moves over an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, MouseEvent e){
    String tooltip = ((JComponent)c).getToolTipText();
    //System.out.println(c.getClass());
    if (tooltip != null && tooltip.length() > 0){
      WSTextArea detailsArea = (WSTextArea)WSRepository.get("SidePanel_Options_DescriptionField");
      detailsArea.setText(tooltip);
      }
    lastMotionObject = null;
    return super.onHover(c,e);
    }


/**
**********************************************************************************************
CHANGED
The event that is triggered from a WSHoverableListener when the mouse moves out of an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, MouseEvent e){
    WSTextArea detailsArea = (WSTextArea)WSRepository.get("SidePanel_Options_DescriptionField");
    detailsArea.setText(" ");
    lastMotionObject = null;
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