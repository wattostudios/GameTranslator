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

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.JComponent;
import javax.swing.tree.*;
import java.io.File;
import javax.swing.tree.TreePath;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;

/**
**********************************************************************************************

**********************************************************************************************
**/

public class AquanauticTreeUI extends BasicTreeUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent tree){
    return new AquanauticTreeUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JTree tree = (JTree)c;


    AquanauticTreeCellRenderer rend = new AquanauticTreeCellRenderer();
    tree.setCellRenderer(rend);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JTree tree = (JTree)c;
    tree.setCellRenderer(new DefaultTreeCellRenderer());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void paintExpandControl(Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf){
    int leftPos = (int)bounds.getX() - 17;
    int topPos = (int)bounds.getY()+5;

    int width = 9;
    int height = 9;

    g.setColor(Color.WHITE);
    g.fillRect(leftPos,topPos,width,height);

    g.setColor(Color.BLACK);
    g.drawRect(leftPos,topPos,width-1,height-1);

    g.drawLine(leftPos+2,topPos+4,leftPos+width-3,topPos+4);

    if (!isExpanded){
      g.drawLine(leftPos+4,topPos+2,leftPos+4,topPos+height-3);
      }

    }


  }