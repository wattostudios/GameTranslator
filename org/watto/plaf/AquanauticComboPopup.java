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
import javax.swing.plaf.basic.*;
import javax.swing.JList;
import javax.swing.Icon;
import javax.swing.Action;
import javax.swing.border.EmptyBorder;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class AquanauticComboPopup extends BasicComboPopup {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticComboPopup(JComboBox box) {
    super(box);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected JScrollPane createScroller() {
    return new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  protected Rectangle computePopupBounds(int px,int py,int pw,int ph) {
    int bor = AquanauticTheme.BORDER_WIDTH;
    Rectangle bounds = super.computePopupBounds(px+bor,py-bor,pw-bor-bor,ph+bor+bor);
    return bounds;
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void configureScroller() {
    //scroller.setFocusable(false);
    //scroller.getVerticalScrollBar().setFocusable(false);
    scroller.setOpaque(false);
    //scroller.setBackground(AquanauticTheme.COLOR_DARK);
    }


  }

