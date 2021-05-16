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
public class SidePanel_Search extends WSPanelPlugin implements WSEnterableInterface {

  WSObjectCheckBox[] checkboxes;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_Search(){
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
  public SidePanel_Search(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_Search.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);
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
Adds the checkboxes for each table column
**********************************************************************************************
**/
  public void addCheckboxes(){

    // get the panel that holds the checkboxes
    WSPanel checkboxPanel = (WSPanel)WSRepository.get("SidePanel_Search_ColumnsHolder");
    checkboxPanel.removeAll();

    WSTableColumn[] columns = Archive.getColumns();

    // only allow searching on columns that are String, Long, or Boolean (eg not Icons, etc)
    int numColumns = columns.length;
    for (int i=0;i<columns.length;i++){
      Class type = columns[i].getType();
      if (type != String.class && type != Long.class && type != Integer.class && type != Short.class && type != Boolean.class){
        numColumns--;
        }
      }

    checkboxPanel.setLayout(new GridLayout(numColumns,1,0,0));

    checkboxes = new WSObjectCheckBox[numColumns];

    for (int i=0,j=0;i<columns.length;i++){
      Class type = columns[i].getType();
      if (type != String.class && type != Long.class && type != Integer.class && type != Short.class && type != Boolean.class){
        continue;
        }

      WSObjectCheckBox checkbox = new WSObjectCheckBox(XMLReader.readString("<WSObjectCheckBox />"),columns[i]);
      checkbox.setSelected(Settings.getBoolean("SearchColumn_" + columns[i].getCode()));

      checkboxes[j] = checkbox;

      checkboxPanel.add(checkbox);
      j++;
      //checkboxPanel.add(new JCheckBox("BOO"));
      }

    /*
    // build the XML that will be used to construct the interface
    String xml = "<WSPanel layout=\"GridLayout\" columns=\"1\" rows=\"" + numColumns + "\" >";

    xml += "</WSPanel>";

    // build and add the components to the panel
    checkboxPanel.add(WSHelper.buildComponent(XMLReader.readString(xml)));
    */

    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c instanceof WSObjectCheckBox){
      WSObjectCheckBox checkbox = (WSObjectCheckBox)c;
      WSTableColumn column = (WSTableColumn)checkbox.getObject();
      Settings.set("SearchColumn_" + column.getCode(),"" + checkbox.isSelected());
      return true;
      }
    else if (c instanceof WSButton){
      String code = ((WSButton)c).getCode();
      if (code.equals("SidePanel_Search_FindNextButton")){
        //findNextMatch();
        performSearch(true);
        }
      else if (code.equals("SidePanel_Search_SelectAllButton")){
        //selectAllMatches();
        performSearch(false);
        }
      return true;
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
The event that is triggered from a WSEnterableListener when a key is pressed
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onEnter(JComponent c, java.awt.event.KeyEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code.equals("SidePanel_Search_InputText")){
        performSearch(true);
        return true;
        }
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
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();

      if (code.equals("SidePanel_Search_InputText") && e.getKeyCode() == KeyEvent.VK_F3){
        performSearch(true);
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
    addCheckboxes();
    ((WSTextField)WSRepository.get("SidePanel_Search_InputText")).requestFocus();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void performSearch(boolean firstMatchOnly){
    // determine the columns to search
    int numColumns = 0;
    WSTableColumn[] columns = new WSTableColumn[checkboxes.length];

    for (int i=0;i<checkboxes.length;i++){
      if (checkboxes[i].isSelected()){
        columns[numColumns] = (WSTableColumn)checkboxes[i].getObject();
        numColumns++;
        }
      }

    if (numColumns < columns.length){
      WSTableColumn[] temp = columns;
      columns = new WSTableColumn[numColumns];
      System.arraycopy(temp,0,columns,0,numColumns);
      }

    // determine the search value
    String searchVal = ((WSTextField)WSRepository.get("SidePanel_Search_InputText")).getText();

    Task_SearchFileList task = new Task_SearchFileList(columns,searchVal,firstMatchOnly);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void requestFocus(){
    ((WSTextField)WSRepository.get("SidePanel_Search_InputText")).requestFocus();
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