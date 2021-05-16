////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2008  WATTO Studios                           //
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

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
**********************************************************************************************
  A DefaultTableModel that does not allow editing
**********************************************************************************************
**/
public class UneditableTableModel extends DefaultTableModel{


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel() {
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel(int rowCount, int columnCount) {
    super(rowCount,columnCount);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel(Object[][] data, Object[] columnNames) {
    super(data,columnNames);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel(Object[] columnNames, int rowCount) {
    super(columnNames,rowCount);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel(Vector columnNames, int rowCount) {
    super(columnNames,rowCount);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UneditableTableModel(Vector data, Vector columnNames) {
    super(data,columnNames);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isCellEditable(int row, int column) {
    return false;
    }


  }

