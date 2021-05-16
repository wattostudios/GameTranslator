////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      AQUANAUTIC THEME                                      //
//                                  A Look And Feel For Java                                  //
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

package org.watto.plaf;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JTables
**********************************************************************************************
**/

public class AquanauticTableUI extends BasicTableUI {

  //private final static AquanauticTableUI buttonUI = new AquanauticTableUI();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticTableUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JTable table = (JTable)c;

    AquanauticTableCellRenderer rend = new AquanauticTableCellRenderer();
    table.setDefaultRenderer(Object.class,rend);
    table.setDefaultRenderer(Number.class,rend);
    table.setDefaultRenderer(Icon.class,rend);

    //table.setIntercellSpacing(new Dimension(0,0));

    TableColumnModel model = table.getColumnModel();
    for (int i=0;i<table.getColumnCount();i++){
      //model.getColumn(i).sizeWidthToFit();
      TableColumn column = model.getColumn(i);
      column.setCellRenderer(rend);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JTable table = (JTable)c;

    DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
    table.setDefaultRenderer(Object.class,rend);

    TableColumnModel model = table.getColumnModel();
    for (int i=0;i<table.getColumnCount();i++){
      //model.getColumn(i).sizeWidthToFit();
      TableColumn column = model.getColumn(i);
      column.setCellRenderer(rend);
      }

    }


  }
