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
  UI for JPanels
**********************************************************************************************
**/

public class AquanauticSplitPaneDivider extends BasicSplitPaneDivider {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticSplitPaneDivider(BasicSplitPaneUI ui) {
    super(ui);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g){
    int pad = AquanauticTheme.BORDER_WIDTH;

    int w = getWidth();
    int h = getHeight();

    if (orientation == JSplitPane.HORIZONTAL_SPLIT){
      //Graphics t = g.create(0,-pad,w,h+pad+pad);
      //AquanauticPainter.paintSquareBorder((Graphics2D)t,0,0,w,h+pad+pad);
      Graphics t = g.create(0,0,w,h);
      AquanauticPainter.paintSquareGradient((Graphics2D)t,0,0,w,h);
      }
    else {
      //Graphics t = g.create(-pad,0,w+pad+pad,h);
      //AquanauticPainter.paintSquareBorder((Graphics2D)t,0,0,w+pad+pad,h);
      Graphics t = g.create(0,0,w,h);
      AquanauticPainter.paintSquareGradient((Graphics2D)t,0,0,w,h);
      }


    /*
    if (leftButton != null){
      //leftButton.repaint();
      Graphics t = g.create(pad,pad,16-pad-pad,16-pad-pad);
      leftButton.setOpaque(false);
      leftButton.paint(t);
      }

    if (rightButton != null){
      //rightButton.repaint();
      Graphics t = g.create(pad,pad+18,16-pad-pad,16-pad-pad);
      rightButton.setOpaque(false);
      rightButton.paint(t);
      }
    */


    }


  }
