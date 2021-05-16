////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       GAME EXTRACTOR                                       //
//                               Extensible Game Archive Editor                               //
//                                http://www.watto.org/extract                                //
//                                                                                            //
//                           Copyright (C) 2002-2008  WATTO Studios                           //
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

package org.watto.component;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.event.TableModelListener;

public class WSTableModel implements TableModel {

Object[][] data;
WSTableColumn[] columns;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableModel() {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableModel(Object[][] data, String[] headings) {
    this.data = data;

    int numColumns = headings.length;
    columns = new WSTableColumn[numColumns];
    for (int i=0;i<numColumns;i++){
      columns[i] = new WSTableColumn(headings[i],(char)i,String.class,false,true);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableModel(Object[][] data, WSTableColumn[] columns) {
    this.data = data;
    this.columns = columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addTableModelListener(TableModelListener tml) {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void configureTable(WSTable table){

    TableColumnModel columnModel = table.getColumnModel();

    int screenWidth = table.getWidth();
    if (screenWidth <= 0){
      screenWidth = Integer.MAX_VALUE;
      }

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


    table.setColumnSelectionAllowed(false);

    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setReorderingAllowed(false);
    tableHeader.setResizingAllowed(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Class getColumnClass(int column) {
    if (column >= columns.length){
      return String.class;
      }
    return columns[column].getType();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getColumnCount() {
    return columns.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getColumnName(int column) {
    return columns[column].getName();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn[] getColumns() {
    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getRowCount() {
    return data.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getValueAt(int row, int column) {
    return data[row][column];
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isCellEditable(int row, int column) {
    if (columns[column].isEditable()){
      return true;
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void removeTableModelListener(TableModelListener tml) {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setValueAt(Object value, int row, int column) {
    data[row][column] = value;
    }


  }