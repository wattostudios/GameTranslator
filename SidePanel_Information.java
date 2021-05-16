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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_Information extends WSPanelPlugin implements WSMotionableInterface,
                                                                    WSDoubleClickableInterface {

  /** for tooltips **/
  String lastMotionObject = "";


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_Information(){
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
  public SidePanel_Information(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_Information.wsd"));

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

**********************************************************************************************
**/
  public void buildTable(JTable table, Object[][] tableData){
    table.setModel(new UneditableTableModel(tableData,new String[]{"",""}));

    /*
    TableColumnModel columnModel = table.getColumnModel();
    if (columnModel.getColumnCount() > 0){
      TableColumn column = columnModel.getColumn(0);
      column.setPreferredWidth(column.getMinWidth());
      }
    */
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadArchiveTable(){
    WSTable archiveTable = (WSTable)WSRepository.get("SidePanel_Information_ArchiveTable");
    archiveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    String[][] tableData = new String[8][2];
    int row = 0;

    if (Archive.getReadPlugin() != null){
      ArchivePlugin plugin = Archive.getReadPlugin();

      tableData[row] = new String[]{Language.get("Information_PluginName"),plugin.getName()};
      row++;

      tableData[row] = new String[]{Language.get("Information_PluginClass"),plugin.getClass().toString()};
      row++;

      tableData[row] = new String[]{Language.get("Information_SupportedExtensions"),plugin.getExtensionsList()};
      row++;

      tableData[row] = new String[]{Language.get("Information_SupportedPlatforms"),plugin.getPlatformsList()};
      row++;

      tableData[row] = new String[]{Language.get("Information_SupportedGames"),plugin.getGamesList()};
      row++;

      tableData[row] = new String[]{Language.get("Information_AllowedFunctions"),plugin.getAllowedFunctionsList()};
      row++;
      }

    if (Archive.getBasePath() != null){
      tableData[row] = new String[]{Language.get("Information_BasePath"),Archive.getBasePath().getAbsolutePath()};
      row++;
      }

    if (Archive.getResources() != null){
      Resource[] resources = Archive.getResources();

      tableData[row] = new String[]{Language.get("Information_NumberOfFiles"),resources.length + ""};
      row++;
      }


    if (row == 0){
      loadEmptyTable(archiveTable);
      return;
      }
    else if (row < 8){
      String[][] temp = tableData;
      tableData = new String[row][2];
      System.arraycopy(temp,0,tableData,0,row);
      }

    buildTable(archiveTable,tableData);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadEmptyTable(JTable table){
    table.setModel(new DefaultTableModel());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadPluginTable(File path){
    WSTable pluginTable = (WSTable)WSRepository.get("SidePanel_Information_PluginTable");
    pluginTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    RatedPlugin[] plugins = PluginFinder.findPlugins(path,ArchivePlugin.class);

    if (plugins == null || plugins.length == 0){
      loadEmptyTable(pluginTable);
      return;
      }

    java.util.Arrays.sort(plugins);

    Object[][] tableData = new Object[plugins.length][2];

    for (int i=0;i<plugins.length;i++){
      //tableData[i] = new String[]{plugins[i].getPlugin().getName(),plugins[i].getRating() + "%"};
      tableData[i] = new Object[]{plugins[i],plugins[i].getRating() + "%"};
      }

    buildTable(pluginTable,tableData);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadResourceTable(Resource resource){
    WSTable resourceTable = (WSTable)WSRepository.get("SidePanel_Information_ResourceTable");
    resourceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    ArchivePlugin readPlugin = Archive.getReadPlugin();
    WSTableColumn[] columns = Archive.getColumns();
    Object[][] tableData = new Object[columns.length][2];

    for (int i=0;i<columns.length;i++){
      tableData[i][0] = columns[i].getName();
      Object value = readPlugin.getColumnValue(resource,columns[i].getCharCode());
      if (value == null){
        tableData[i][1] = "";
        }
      else {
        tableData[i][1] = value;
        }
      }

    buildTable(resourceTable,tableData);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadResourceTable(Resource[] resources){
    WSTable resourceTable = (WSTable)WSRepository.get("SidePanel_Information_ResourceTable");
    resourceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    ArchivePlugin readPlugin = Archive.getReadPlugin();
    String[][] tableData = new String[1][2];
    tableData[0] = new String[]{Language.get("Information_NumSelectedFiles"),"" + resources.length};

    buildTable(resourceTable,tableData);
    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();
      if (code.equals("FileList")){
        reloadTable();
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
The event that is triggered from a WSDoubleClickableListener when a double click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDoubleClick(JComponent c, MouseEvent e){
    if (c instanceof WSTable){
      WSTable table = (WSTable)c;
      String code = table.getCode();

      if (code.equals("SidePanel_Information_PluginTable")){

        ArchivePlugin plugin = (ArchivePlugin)((RatedPlugin)table.getValueAt(table.getSelectedRow(),0)).getPlugin();

        // open the archive using the selected plugin
        Task_ReadArchiveWithPlugin task = new Task_ReadArchiveWithPlugin(Archive.getBasePath(),plugin);
        task.setDirection(UndoTask.DIRECTION_REDO);
        new Thread(task).start();

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
      if (code.equals("FileList")){
        reloadTable();
        return true;
        }
      }
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
    // Shows the value of the cell in the statusbar
    if (c instanceof WSTable){
      WSTable table = (WSTable)c;
      String code = table.getCode();

      if (code.equals("SidePanel_Information_ResourceTable") || code.equals("SidePanel_Information_ArchiveTable") || code.equals("SidePanel_Information_PluginTable")){
        Point point = e.getPoint();

        int row = table.rowAtPoint(point);
        if (row < 0){
          return true;
          }

        String selectedObject = table.getValueAt(row,1).toString();
        String columnHeading = table.getValueAt(row,0).toString();

        if (columnHeading == null || (lastMotionObject != null && lastMotionObject.equals(columnHeading))){
          return true; // still over the same object on the list
          }
        lastMotionObject = columnHeading;

        ((WSStatusBar)WSRepository.get("StatusBar")).setText(columnHeading + ": " + selectedObject);
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
    reloadTable();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadTable(){

    WSTable pluginTable = (WSTable)WSRepository.get("SidePanel_Information_PluginTable");
    WSTable resourceTable = (WSTable)WSRepository.get("SidePanel_Information_ResourceTable");

    loadArchiveTable();

    File basePath = Archive.getBasePath();
    if (basePath != null && basePath.exists()){
      loadPluginTable(basePath);
      }
    else {
      loadEmptyTable(pluginTable);
      }


    WSPanel fileListPanel = ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel();
    if (fileListPanel == null || !(fileListPanel instanceof FileListPanel)){
      loadEmptyTable(resourceTable);
      }
    else {
      Resource[] selected = ((FileListPanel)fileListPanel).getSelected();
      if (Archive.getNumFiles() <= 0){
        selected = null;
        }

      if (selected == null || selected.length <= 0){
        loadEmptyTable(resourceTable);
        }
      else if (selected.length == 1){
        loadResourceTable(selected[0]);
        }
      else {
        loadResourceTable(selected);
        }
      }
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