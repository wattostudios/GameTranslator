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

import org.watto.component.WSHelper;
import org.watto.component.WSLabel;
import org.watto.xml.XMLNode;

import javax.swing.tree.*;
import javax.swing.*;
import javax.swing.plaf.LabelUI;
import javax.swing.border.*;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Color;
import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class AquanauticTreeCellRenderer extends DefaultTreeCellRenderer{


/**
**********************************************************************************************
  Constructor
**********************************************************************************************
**/
  public AquanauticTreeCellRenderer(){
    super();

    setClosedIcon(new ImageIcon(WSHelper.getResource("images/WSTree/ClosedIcon.png")));
    setLeafIcon(new ImageIcon(WSHelper.getResource("images/WSTree/FileIcon.png")));
    setOpenIcon(new ImageIcon(WSHelper.getResource("images/WSTree/DirectoryIcon.png")));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getTreeCellRendererComponent(JTree table, Object value, boolean isSelected, boolean isExpanded, boolean isLeaf, int row, boolean hasFocus) {
    DefaultTreeCellRenderer rend = (DefaultTreeCellRenderer)super.getTreeCellRendererComponent(table,value,isSelected,isExpanded,isLeaf,row,hasFocus);
    rend.setBorder(new EmptyBorder(1,1,1,1));
    return rend;
    }



}