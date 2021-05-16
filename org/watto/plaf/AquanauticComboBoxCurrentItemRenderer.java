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
import java.awt.Component;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.JList;
import javax.swing.Icon;
import javax.swing.Action;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class AquanauticComboBoxCurrentItemRenderer extends BasicComboBoxRenderer {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticComboBoxCurrentItemRenderer() {
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){

    if (index == -1){
      setBackground(new Color(0,0,0,0));
      setOpaque(false);
      }
    else {
      setOpaque(true);
      }

    if (cellHasFocus){
      setBackground(AquanauticTheme.COLOR_MID);
      }
    else {
      setBackground(new Color(0,0,0,0));
      }

    if (list != null){
      setFont(list.getFont());
      }

    if (value instanceof Icon) {
      setIcon((Icon)value);
      }
    else {
      setText((value == null) ? "" : value.toString());
      }

    //setBorder(new EmptyBorder(0,0,0,0));

    return this;

    }


  }

