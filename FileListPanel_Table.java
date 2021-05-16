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
import org.watto.plaf.AquanauticTheme;
import org.watto.xml.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.io.File;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;

public class FileListPanel_Table extends FileListPanel implements WSClickableInterface,
                                                                  WSDoubleClickableInterface,
                                                                  WSKeyableInterface,
                                                                  WSTableColumnableInterface,
                                                                  WSMotionableInterface {

  WSTable table;
  FileListModel_Table model;

  WSTable propTable;
  FileListModel_Table propModel;
  JPanel propPanel;


  /** Set to true when reload() so that all the column widths aren't reset to 75 **/
  boolean initColumnSizes = false;
  /** so the column sizes are only changed once **/
  int changeColumnNumber = 0;

  /** for tooltips **/
  String lastMotionObject = "";

  // files being dropped from a drag-drop operation
  File[] dropFiles;
  // the point where the drop occurred
  Point dropPoint;

  /** the selected row when right-clicking, for things like PreviewFile that only want 1 file **/
  int rightClickSelectedRow = -1;

  /** for wrapping around when pressing up or down on the table **/
  boolean moveAgain = false;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileListPanel_Table() {
    super("Table");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void changeSelection(int row){
    table.changeSelection(row,0,true,false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void constructInterface(){
    removeAll();



    table = new WSTable(XMLReader.readString("<WSTable code=\"FileList\" />"));
    propTable = new WSTable(XMLReader.readString("<WSTable code=\"PropList\" />"));

    // pressing enter defaults to moving to the next row - don't want to do this
    table.disableAutomaticKeyEvent("pressed ENTER");
    propTable.disableAutomaticKeyEvent("pressed ENTER");

    // option to auto-resize columns to fit, or allow overflow horizontally (with scrollbar)
    if (! Settings.getBoolean("AutoResizeTableColumns")){
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      propTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      }
    else {
      table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
      propTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
      }




    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane,BorderLayout.CENTER);

    propPanel = new WSPanel(XMLReader.readString("<WSPanel code=\"FileListPanel_Table_Properties\" showLabel=\"true\" showBorder=\"true\" />"));
    propPanel.add(new JScrollPane(propTable),BorderLayout.CENTER);
    add(propPanel,BorderLayout.SOUTH);



    //propPanel.setHeight(100);
    //propPanel.setMinimumSize(new Dimension(0,0));
    //propPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,150));
    propPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE,150));
    propPanel.setVisible(false);



    table.setColumnSelectionAllowed(false);
    propTable.setColumnSelectionAllowed(false);


    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setReorderingAllowed(false);
    tableHeader.setResizingAllowed(true);
    tableHeader.addMouseListener(new WSClickableListener(this));

    JTableHeader propTableHeader = propTable.getTableHeader();
    propTableHeader.setReorderingAllowed(false);
    propTableHeader.setResizingAllowed(true);
    propTableHeader.addMouseListener(new WSClickableListener(this));


    try {
      ((DefaultCellEditor)table.getDefaultEditor(Object.class)).setClickCountToStart(1);
      ((DefaultCellEditor)table.getDefaultEditor(Number.class)).setClickCountToStart(1);
      ((DefaultCellEditor)propTable.getDefaultEditor(Object.class)).setClickCountToStart(1);
      ((DefaultCellEditor)propTable.getDefaultEditor(Number.class)).setClickCountToStart(1);
      }
    catch (Throwable t){
      }


    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getFirstSelectedRow(){
    return table.getSelectedRow();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getNumSelected(){
    return table.getSelectedRowCount();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource getResource(int row){
    return model.getResource(row);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource[] getSelected(){
    int[] selectedRows = table.getSelectedRows();
    Resource[] resources = new Resource[selectedRows.length];
    for (int i=0;i<resources.length;i++){
      resources[i] = model.getResource(selectedRows[i]);
      }

    return resources;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload(){
    reloadMainTable();
    reloadPropTable();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadMainTable(){

    TableColumnModel columnModel = table.getColumnModel();
    WSTableColumn[] columns = Archive.getReadPlugin().getViewingColumns();

    // If the column counts are different, we need to build a new model.
    // This is because columns have been added or removed from the table
    // and we thus need to rebuild the model. Refreshing the model is not
    // sufficient in this case - crashes with ArrayIndexOutOfBounds.
    if (model == null || columnModel.getColumnCount() != columns.length){
      model = new FileListModel_Table();
      table.setModel(model);
      columnModel = table.getColumnModel();
      }
    else {
      model.reload();
      }



    int screenWidth = getWidth();
    if (screenWidth <= 0){
      // The FileListPanel hasn't been displayed yet.
      // Therefore, get the width of the FileListPanelHolder instead
      screenWidth = ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getWidth();
      }

    initColumnSizes = true;
    for (int i=0;i<columns.length;i++){
      WSTableColumn columnDetails = columns[i];

      TableColumn column = columnModel.getColumn(i);
      column.setHeaderValue(columnDetails.getName());

      int minWidth = columnDetails.getMinWidth();
      int maxWidth = columnDetails.getMaxWidth();

      if (minWidth < 0){
        minWidth = 0;
        }
      if (maxWidth < 0){
        maxWidth = screenWidth;
        }

      column.setMinWidth(minWidth);
      column.setMaxWidth(maxWidth);
      column.setPreferredWidth(columnDetails.getWidth());
      }


    // repaint the table header in the correct colors
    JTableHeader header = table.getTableHeader();
    header.setBackground(AquanauticTheme.COLOR_LIGHT);
    header.setBorder(new LineBorder(AquanauticTheme.COLOR_DARK,1));

    initColumnSizes = false;


    table.revalidate();
    table.repaint();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadPropTable(){

    TableColumnModel columnModel = propTable.getColumnModel();
    WSTableColumn[] columns = Archive.getReadPlugin().getViewingPropColumns();

    // If the column counts are different, we need to build a new model.
    // This is because columns have been added or removed from the table
    // and we thus need to rebuild the model. Refreshing the model is not
    // sufficient in this case - crashes with ArrayIndexOutOfBounds.
    if (propModel == null || columnModel.getColumnCount() != columns.length){
      propModel = new FileListModel_Table_Properties();
      propTable.setModel(propModel);
      columnModel = propTable.getColumnModel();
      }
    else {
      propModel.reload();
      }



    int screenWidth = getWidth();
    if (screenWidth <= 0){
      // The FileListPanel hasn't been displayed yet.
      // Therefore, get the width of the FileListPanelHolder instead
      screenWidth = ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getWidth();
      }

    initColumnSizes = true;
    for (int i=0;i<columns.length;i++){
      WSTableColumn columnDetails = columns[i];

      TableColumn column = columnModel.getColumn(i);
      column.setHeaderValue(columnDetails.getName());

      int minWidth = columnDetails.getMinWidth();
      int maxWidth = columnDetails.getMaxWidth();

      if (minWidth < 0){
        minWidth = 0;
        }
      if (maxWidth < 0){
        maxWidth = screenWidth;
        }

      column.setMinWidth(minWidth);
      column.setMaxWidth(maxWidth);
      column.setPreferredWidth(columnDetails.getWidth());
      }


    // repaint the table header in the correct colors
    JTableHeader header = propTable.getTableHeader();
    header.setBackground(AquanauticTheme.COLOR_LIGHT);
    header.setBorder(new LineBorder(AquanauticTheme.COLOR_DARK,1));

    initColumnSizes = false;

    if (Archive.getReadPlugin().getNumProperties() > 0){
      propPanel.setVisible(true);
      }
    else {
      propPanel.setVisible(false);
      }


    propTable.revalidate();
    propTable.repaint();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectAll(){
    table.selectAll();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectInverse(){
    table.setVisible(false);
    for (int i=1;i<table.getRowCount();i++){
      changeSelection(i);
      }
    changeSelection(0);
    table.setVisible(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectNone(){
    table.clearSelection();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectResource(int row){
    selectNone();
    changeSelection(row);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (c instanceof JTableHeader){
      if (c == table.getTableHeader()){
        TableColumnModel columnModel = table.getColumnModel();
        int viewColumn = columnModel.getColumnIndexAtX(e.getX());
        int column = table.convertColumnIndexToModel(viewColumn);

        if (column != -1) {
          // if inline editing, stop the editing first.
          if (table.isEditing()){
            table.getCellEditor().stopCellEditing();
            }

          FileListModel_Table model = (FileListModel_Table) table.getModel();
          model.sortResources(column);
          table.repaint();
          return true;
          }
        }
      /*
      else if (c == propTable.getTableHeader()){
        TableColumnModel columnModel = propTable.getColumnModel();
        int viewColumn = columnModel.getColumnIndexAtX(e.getX());
        int column = propTable.convertColumnIndexToModel(viewColumn);

        if (column != -1) {
          // if inline editing, stop the editing first.
          if (propTable.isEditing()){
            propTable.getCellEditor().stopCellEditing();
            }

          FileListModel_Table model = (FileListModel_Table) propTable.getModel();
          model.sortResources(column);
          propTable.repaint();
          return true;
          }
        }
      */
      }
    else {
      ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onClick(c,e);
      return true;
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSidePanel(String name){
    ((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).loadPanel("SidePanel_" + name);
    WSPopup.showMessage("SidePanelChanged",true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setFileListPanel(String name){
    Settings.set("FileListView",name);
    ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).loadPanel(name);
    WSPopup.showMessage("FileListViewChanged",true);
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves out of an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, MouseEvent e){
    lastMotionObject = "";
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onDoubleClick(JComponent c, java.awt.event.MouseEvent e){
    if (c == table){
      ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onDoubleClick(c,e);
      }
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, java.awt.event.KeyEvent e){
    if (c == table){
      // move to the next file starting with this letter

      int keyCodeInt = e.getKeyCode();
      if (keyCodeInt == KeyEvent.VK_ENTER){
        editNextRow(table,false);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_TAB){
        editNextCell(table,true);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_UP){
        editPreviousRow(table,true);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_DOWN){
        editNextRow(table,true);
        return true;
        }

      char keyCode = e.getKeyChar();
      if (keyCode == KeyEvent.CHAR_UNDEFINED || keyCode == '?'){
        ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onKeyPress(c,e);
        return true; // not a letter or number
        }


      if (table.isEditing()){
        // if editing, don't change the selection
        ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onKeyPress(c,e);
        return true;
        }

      keyCode = (""+keyCode).toLowerCase().charAt(0);
      char keyCodeCaps = (""+keyCode).toUpperCase().charAt(0);

      int numFiles = table.getRowCount();
      int selectedIndex = table.getSelectedRow() + 1;

      if (selectedIndex >= numFiles){
        selectedIndex = 0;
        }

      char columnChar = 'O';
      ArchivePlugin plugin = Archive.getReadPlugin();

      // search the bottom half of the list
      for (int i=selectedIndex;i<numFiles;i++){
        String filename = (String)plugin.getColumnValue(model.getResource(i),columnChar);
        if (filename.length() > 0){
          char currentChar = filename.charAt(0);
          if (currentChar == keyCode || currentChar == keyCodeCaps){
            table.setRowSelectionInterval(i,i);
            return true;
            }
          }
        }

      if (selectedIndex == 0){
        // we started searching from the start of the list, so we don't want to re-search
        return false;
        }

      //  search the top half of the list, if not found in the bottom half.
      for (int i=0;i<=selectedIndex;i++){
        String filename = (String)plugin.getColumnValue(model.getResource(i),columnChar);
        if (filename.length() > 0){
          char currentChar = filename.charAt(0);
          if (currentChar == keyCode || currentChar == keyCodeCaps){
            table.setRowSelectionInterval(i,i);
            return true;
            }
          }
        }

      }
    else if (c == propTable){
      // move to the next file starting with this letter

      int keyCodeInt = e.getKeyCode();
      if (keyCodeInt == KeyEvent.VK_ENTER){
        editNextRow(propTable,false);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_TAB){
        editNextCell(propTable,true);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_UP){
        editPreviousRow(propTable,true);
        return true;
        }
      else if (keyCodeInt == KeyEvent.VK_DOWN){
        editNextRow(propTable,true);
        return true;
        }

      char keyCode = e.getKeyChar();
      if (keyCode == KeyEvent.CHAR_UNDEFINED || keyCode == '?'){
        ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onKeyPress(c,e);
        return true; // not a letter or number
        }


      if (propTable.isEditing()){
        // if editing, don't change the selection
        ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onKeyPress(c,e);
        return true;
        }

      keyCode = (""+keyCode).toLowerCase().charAt(0);
      char keyCodeCaps = (""+keyCode).toUpperCase().charAt(0);

      int numFiles = propTable.getRowCount();
      int selectedIndex = propTable.getSelectedRow() + 1;

      if (selectedIndex >= numFiles){
        selectedIndex = 0;
        }

      char columnChar = 'O';
      ArchivePlugin plugin = Archive.getReadPlugin();

      // search the bottom half of the list
      for (int i=selectedIndex;i<numFiles;i++){
        String filename = (String)plugin.getColumnValue(propModel.getResource(i),columnChar);
        if (filename.length() > 0){
          char currentChar = filename.charAt(0);
          if (currentChar == keyCode || currentChar == keyCodeCaps){
            propTable.setRowSelectionInterval(i,i);
            return true;
            }
          }
        }

      if (selectedIndex == 0){
        // we started searching from the start of the list, so we don't want to re-search
        return false;
        }

      //  search the top half of the list, if not found in the bottom half.
      for (int i=0;i<=selectedIndex;i++){
        String filename = (String)plugin.getColumnValue(propModel.getResource(i),columnChar);
        if (filename.length() > 0){
          char currentChar = filename.charAt(0);
          if (currentChar == keyCode || currentChar == keyCodeCaps){
            propTable.setRowSelectionInterval(i,i);
            return true;
            }
          }
        }

      }

    ((WSPanelPlugin)((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).getCurrentPanel()).onKeyPress(c,e);
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onColumnResize(TableColumnModel c, javax.swing.event.ChangeEvent ce){
    if (initColumnSizes){
      return false;
      }

    ArchivePlugin readPlugin = Archive.getReadPlugin();
    if (readPlugin == null){
      return false;
      }
    WSTableColumn[] columns = readPlugin.getViewingColumns();
    if (columns == null){
      return false;
      }

    if (changeColumnNumber != columns.length-1){
      changeColumnNumber++;
      return true;
      }

    changeColumnNumber = 0;

    DefaultTableColumnModel model = (DefaultTableColumnModel)c;
    java.util.Enumeration e = model.getColumns();

    int i=0;
    while (e.hasMoreElements()){
      TableColumn column = (TableColumn)e.nextElement();
      if (i >= columns.length){
        break;
        }
      columns[i].setWidth(column.getWidth());
      i++;
      }

    return true;
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
      if (c == table){
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int column = table.columnAtPoint(point);

        Object selectedObj = table.getValueAt(row,column);
        if (selectedObj == null){
          return true;
          }
        String selectedObject = selectedObj.toString();
        if (selectedObject == null || lastMotionObject.equals(selectedObject)){
          return true; // still over the same object on the list
          }
        lastMotionObject = selectedObject;

        String columnHeading;
        try {
          columnHeading = Archive.getReadPlugin().getViewingColumn(column).toString();
          }
        catch (Throwable t){
          columnHeading = Language.get("ColumnValue");
          }

        ((WSStatusBar)WSRepository.get("StatusBar")).setText(columnHeading + ": " + selectedObject);
        return true;
        }
      else if (c == propTable){
        Point point = e.getPoint();
        int row = propTable.rowAtPoint(point);
        int column = propTable.columnAtPoint(point);

        String selectedObject = propTable.getValueAt(row,column).toString();
        if (selectedObject == null || lastMotionObject.equals(selectedObject)){
          return true; // still over the same object on the list
          }
        lastMotionObject = selectedObject;

        String columnHeading;
        try {
          columnHeading = Archive.getReadPlugin().getViewingPropColumn(column).toString();
          }
        catch (Throwable t){
          columnHeading = Language.get("ColumnValue");
          }

        ((WSStatusBar)WSRepository.get("StatusBar")).setText(columnHeading + ": " + selectedObject);
        return true;
        }
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void stopInlineEditing(){
    // if inline editing, stop the editing first.
    if (table.isEditing()){
      table.getCellEditor().stopCellEditing();
      }
    if (propTable.isEditing()){
      propTable.getCellEditor().stopCellEditing();
      }
    }




/**
**********************************************************************************************

**********************************************************************************************
**/
  public void editNextRow(JTable table, boolean alreadyMoved){
    // move to the next row, and start editing it

    int numRows = table.getRowCount();

    int row = table.getSelectedRow();
    if (!alreadyMoved){
      row++;
      moveAgain = false;
      }
    else if (alreadyMoved && row == numRows-1){
      if (moveAgain){
        row++;
        //moveAgain = false;
        }
      else {
        moveAgain = true;
        }
      }
    else {
      moveAgain = false;
      }
    int col = table.getSelectedColumn();

    if (row >= numRows){
      row = 0; // start again from the top
      }

    if (row > numRows){
      // no rows in the table
      return;
      }

    if (table.isEditing()){
      table.getCellEditor().stopCellEditing();
      }

    if (row < numRows){
      table.setRowSelectionInterval(row,row);
      table.setColumnSelectionInterval(col,col);
      table.scrollRectToVisible(table.getCellRect(row,col,true));
      table.editCellAt(row,col);
      try {
        JTextField textField = ((JTextField)((DefaultCellEditor)table.getCellEditor()).getComponent());
        textField.selectAll();
        textField.requestFocus();
        }
      catch (Throwable t){
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void editPreviousRow(JTable table, boolean alreadyMoved){
    // move to the next row, and start editing it

    int row = table.getSelectedRow();
    if (!alreadyMoved){
      row--;
      moveAgain = false;
      }
    else if (alreadyMoved && row == 0){
      if (moveAgain){
        row--;
        //moveAgain = false;
        }
      else {
        moveAgain = true;
        }
      }
    else {
      moveAgain = false;
      }
    int col = table.getSelectedColumn();

    if (row < 0){
      row = table.getRowCount()-1; // start again from the top
      }

    if (row < 0){
      // no rows in the table
      return;
      }

    if (table.isEditing()){
      table.getCellEditor().stopCellEditing();
      }

    if (row < table.getRowCount()){
      table.setRowSelectionInterval(row,row);
      table.setColumnSelectionInterval(col,col);
      table.scrollRectToVisible(table.getCellRect(row,col,true));
      table.editCellAt(row,col);
      try {
        JTextField textField = ((JTextField)((DefaultCellEditor)table.getCellEditor()).getComponent());
        textField.selectAll();
        textField.requestFocus();
        }
      catch (Throwable t){
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void editNextCell(JTable table, boolean alreadyMoved){
    // move to the next editable cell, and start editing it

    int row = table.getEditingRow();
    int col = table.getEditingColumn();

    if (row == -1){
      row = table.getSelectedRow();
      }
    if (col == -1){
      col = table.getSelectedColumn();
      }
    //col++; // already moved to the next column by the TAB key

    int origRow = row;
    int origCol = col;

    int numRows = table.getRowCount();
    int numCols = table.getColumnCount();

    boolean found = false;

    while (!found && row < numRows){
      while (!found && col < numCols){
        if (table.isCellEditable(row,col)){
          found = true;
          }
        else {
          col++;
          }
        }
      if (!found){
        row++;
        col = 0;
        }
      }

    if (!found){
      //start again from the top
      row = 0;
      col = 0;

      while (!found && row <= origRow){
        while (!found && col < numCols){
          if (table.isCellEditable(row,col)){
            found = true;
            }
          else {
            col++;
            }
          }
        if (!found){
          row++;
          col = 0;
          }
        }

      }

    if (found){
      if (table.isEditing()){
        table.getCellEditor().stopCellEditing();
        }

      if (row < table.getRowCount()){
        table.setRowSelectionInterval(row,row);
        table.setColumnSelectionInterval(col,col);
        table.editCellAt(row,col);
        try {
          JTextField textField = ((JTextField)((DefaultCellEditor)table.getCellEditor()).getComponent());
          textField.selectAll();
          textField.requestFocus();
          }
        catch (Throwable t){
          t.printStackTrace();
          }
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void editPreviousCell(JTable table, boolean alreadyMoved){
    // move to the next editable cell, and start editing it

    int row = table.getEditingRow();
    int col = table.getEditingColumn();

    if (row == -1){
      row = table.getSelectedRow();
      }
    if (col == -1){
      col = table.getSelectedColumn();
      }
    //col++; // already moved to the next column by the LEFT key

    int origRow = row;
    int origCol = col;

    int numRows = table.getRowCount();
    int numCols = table.getColumnCount();

    boolean found = false;

    while (!found && row >= 0){
      while (!found && col >= 0){
        if (table.isCellEditable(row,col)){
          found = true;
          }
        else {
          col--;
          }
        }
      if (!found){
        row--;
        col = numCols-1;
        }
      }

    if (!found){
      //start again from the top
      row = numRows-1;
      col = numCols-1;

      while (!found && row >= origRow){
        while (!found && col >= 0){
          if (table.isCellEditable(row,col)){
            found = true;
            }
          else {
            col--;
            }
          }
        if (!found){
          row--;
          col = numCols-1;
          }
        }

      }

    if (found){
      if (table.isEditing()){
        table.getCellEditor().stopCellEditing();
        }

      if (row < table.getRowCount()){
        table.setRowSelectionInterval(row,row);
        table.setColumnSelectionInterval(col,col);
        table.editCellAt(row,col);
        try {
          JTextField textField = ((JTextField)((DefaultCellEditor)table.getCellEditor()).getComponent());
          textField.selectAll();
          textField.requestFocus();
          }
        catch (Throwable t){
          t.printStackTrace();
          }
        }
      }
    }

  }