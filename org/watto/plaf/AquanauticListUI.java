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
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JLists
**********************************************************************************************
**/

public class AquanauticListUI extends BasicListUI {

  JList c;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticListUI(JComponent c) {
    super();
    this.c = (JList)c;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticListUI(c);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer, ListModel dataModel, ListSelectionModel selModel, int leadIndex){
    Object value = dataModel.getElementAt(row);
    boolean cellHasFocus = (list.hasFocus() && (row == leadIndex));
    boolean isSelected = selModel.isSelectedIndex(row);

    Component rendererComponent = cellRenderer.getListCellRendererComponent(list, value, row, isSelected, cellHasFocus);

    ((JComponent)rendererComponent).setOpaque(c.isOpaque());

    if (isSelected) {
      rendererComponent.setForeground(AquanauticTheme.COLOR_TEXT);
      rendererComponent.setBackground(AquanauticTheme.COLOR_MID);
      ((JComponent)rendererComponent).setOpaque(true);
      }
    else {
      rendererComponent.setForeground(AquanauticTheme.COLOR_TEXT);
      rendererComponent.setBackground(AquanauticTheme.COLOR_BG);
      }

    int cx = rowBounds.x;
    int cy = rowBounds.y;
    int cw = rowBounds.width+2;
    int ch = rowBounds.height;

    rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw, ch, true);
    }


  }
