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
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************
  UI for JScrollBars
**********************************************************************************************
**/

public class AquanauticScrollBarUI extends MetalScrollBarUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticScrollBarUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);
    //((JScrollBar)c).setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    //((JScrollBar)c).setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected JButton createDecreaseButton(int orientation) {
    decreaseButton = new AquanauticScrollButton(orientation, scrollBarWidth, isFreeStanding);
    return decreaseButton;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected JButton createIncreaseButton(int orientation) {
    increaseButton =  new AquanauticScrollButton(orientation, scrollBarWidth, isFreeStanding);
    return increaseButton;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds){
    AquanauticPainter.paintOpaque(g,c);

    // paint the thumb as a full-sized thing when it isn't scrollable but it is displayed
    Rectangle thumbBounds = getThumbBounds();
    if (thumbBounds.getX() == 0 && thumbBounds.getY() == 0 && thumbBounds.getWidth() == 0 && thumbBounds.getHeight() == 0){
      paintThumb(g,c,trackBounds);
      }
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void paintThumb(Graphics g, JComponent c, Rectangle trackBounds){
    int x = (int)trackBounds.getX();
    int y = (int)trackBounds.getY();
    int w = (int)trackBounds.getWidth();
    int h = (int)trackBounds.getHeight();

    Graphics t = g.create(x,y,w,h);

    AquanauticPainter.paintCurvedGradient((Graphics2D)t,0,0,w,h);

    }


  }
