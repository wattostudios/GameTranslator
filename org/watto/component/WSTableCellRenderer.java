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

import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.AbstractButton;
import javax.swing.JTable;
import javax.swing.JComponent;


/**
**********************************************************************************************
Allows all WSComponents and JComponents to be rendered normally in a table.
eg. paints a WSButton as a WSButton instead of a String
**********************************************************************************************
**/
public class WSTableCellRenderer extends DefaultTableCellRenderer {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableCellRenderer(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (value instanceof JComponent){
      JComponent component = (JComponent)value;
      if (value instanceof AbstractButton){
        ((AbstractButton)value).setSelected(isSelected);
        }
      return component;
      }
    return super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    }



  }