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
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JSliders
**********************************************************************************************
**/

public class AquanauticSliderUI extends MetalSliderUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticSliderUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);
    ((JSlider)c).setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    ((JSlider)c).setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintTrack(Graphics g){
    int bor = AquanauticTheme.MENU_BORDER_WIDTH;

    int x = (int)trackRect.getX();
    int y = (int)trackRect.getY();
    int w = (int)trackRect.getWidth();
    int h = (int)trackRect.getHeight();

    if (slider.isOpaque()){
      g.setColor(AquanauticTheme.COLOR_BG);
      g.fillRect(x,y,w,h);
      }

    if (slider.getOrientation() == slider.VERTICAL){
      x += bor;
      w -= (bor+bor);
      }
    else {
      y += bor;
      h -= (bor+bor);
      }

    Graphics t = g.create(x,y,w,h);

    AquanauticPainter.paintSquareGradient((Graphics2D)t,0,0,w,h);

    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintThumb(Graphics g){

    int x = (int)thumbRect.getX();
    int y = (int)thumbRect.getY();
    int w = (int)thumbRect.getWidth();
    int h = (int)thumbRect.getHeight();

    Graphics t = g.create(x,y,w,h);

    if (slider.hasFocus()){
      AquanauticPainter.paintCurvedGradient((Graphics2D)t,0,0,w,h,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
      }
    else {
      AquanauticPainter.paintCurvedGradient((Graphics2D)t,0,0,w,h);
      }


    }




  }
