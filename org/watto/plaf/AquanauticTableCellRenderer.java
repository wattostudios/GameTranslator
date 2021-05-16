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

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.event.MouseEvent;
import javax.swing.border.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Color;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class AquanauticTableCellRenderer extends DefaultTableCellRenderer{


/**
**********************************************************************************************
  Constructor
**********************************************************************************************
**/
  public AquanauticTableCellRenderer(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JComponent rend = (JComponent)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);

    rend.setForeground(AquanauticTheme.COLOR_TEXT);

    if (isSelected || hasFocus) {
      rend.setBorder(new LineBorder(AquanauticTheme.COLOR_DARK,1));
      rend.setBackground(AquanauticTheme.COLOR_LIGHT);
      }
    else {
      rend.setBorder(new LineBorder(AquanauticTheme.COLOR_LIGHT,1));
      rend.setBackground(Color.WHITE);
      }

    if (rend instanceof JLabel){
      JLabel label = (JLabel)rend;
      if (value instanceof Icon){
        label.setIcon((Icon)value);
        label.setText("");
        label.setBorder(new EmptyBorder(0,0,0,0));
        }
      else {
        label.setIcon(null);
        }
      }

    //rend.setSize(new Dimension((int)rend.getMinimumSize().getWidth(),table.getRowHeight()));

    return rend;
    }


}