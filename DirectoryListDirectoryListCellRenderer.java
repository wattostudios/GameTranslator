////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       GAME EXTRACTOR                                       //
//                               Extensible Game Archive Editor                               //
//                                http://www.watto.org/extract                                //
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

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class DirectoryListDirectoryListCellRenderer extends DefaultListCellRenderer {

  static ImageIcon fileIcon;
  static ImageIcon dirIcon;
  static boolean sysIcons = false;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public DirectoryListDirectoryListCellRenderer() {
    fileIcon = new ImageIcon(getClass().getResource("images/WSFileChooser/FileIcon.png"));
    dirIcon = new ImageIcon(getClass().getResource("images/WSFileChooser/DirectoryIcon.png"));
    sysIcons = Settings.getBoolean("ShowSystemSpecificIcons");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
    JLabel rend = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

    File file = (File)value;
    rend.setText(file.getName());

    //System.out.println("Painting " + file.getName());

    if (sysIcons){
      rend.setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
      }
    else {
      if (file.isFile()){
        rend.setIcon(fileIcon);
        }
      else {
        rend.setIcon(dirIcon);
        }
      }

    return rend;
    }


  }