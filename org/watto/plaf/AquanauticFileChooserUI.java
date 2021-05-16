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

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JFileChoosers
**********************************************************************************************
**/
public class AquanauticFileChooserUI extends MetalFileChooserUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticFileChooserUI(JFileChooser fc) {
    super(fc);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticFileChooserUI((JFileChooser)c);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JFileChooser chooser = (JFileChooser)c;

    Dimension d = c.getPreferredSize();
    Dimension newD = new Dimension((int)d.getWidth(),(int)d.getHeight() + 44);
    c.setPreferredSize(newD);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void installIcons(JFileChooser fc) {

    fileIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/FileIcon.png"));
    directoryIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/DirectoryIcon.png"));
    floppyDriveIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/FloppyDriveIcon.png"));
    upFolderIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/UpFolderIcon.png"));
    newFolderIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/NewFolderIcon.png"));
    computerIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/ComputerIcon.png"));
    hardDriveIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/HardDriveIcon.png"));
    homeFolderIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/HomeFolderIcon.png"));
    listViewIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/ListViewIcon.png"));
    detailsViewIcon = new ImageIcon(WSHelper.getResource("images/WSFileChooser/DetailsViewIcon.png"));

    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  protected JPanel createList(JFileChooser fc) {
    JPanel listPanel = super.createList(fc);

    Dimension d = listPanel.getPreferredSize();
    Dimension newD = new Dimension((int)d.getWidth(),(int)d.getHeight() + 240);
    listPanel.setMaximumSize(newD);
    listPanel.setMinimumSize(newD);
    listPanel.setPreferredSize(newD);

    return listPanel;
    }




  }