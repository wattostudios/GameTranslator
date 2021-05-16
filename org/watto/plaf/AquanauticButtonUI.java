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

import org.watto.component.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JButtons
**********************************************************************************************
**/

public class AquanauticButtonUI extends BasicButtonUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticButtonUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JButton button = (JButton) c;

    button.setBorderPainted(false);
    button.setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JButton button = (JButton) c;

    button.setBorderPainted(true);
    button.setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JButton button = (JButton) c;

    int bor = AquanauticTheme.BORDER_WIDTH;

    int x = 0;
    int y = 0;
    int w = button.getWidth();
    int h = button.getHeight();


    ButtonModel model = button.getModel();
    if (model.isArmed() && model.isPressed()){
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h);
      }
//    else if (c instanceof WSButton && ((WSButton)c).isCurrentPanel()){
//      AquanauticPainter.paintFocusedBorder((Graphics2D)g,x,y,w,h);
//      }
    else {
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h,AquanauticTheme.COLOR_LIGHT,AquanauticTheme.COLOR_MID);
      }


    Icon icon = button.getIcon();
    String text = button.getText();

    if (icon != null){
      int iconLeft = bor;
      int iconTop = bor;

      int iconWidth = icon.getIconWidth();
      int iconHeight = icon.getIconHeight();

      if (iconTop + bor + iconHeight < h){
        iconTop = h/2 - iconHeight/2;
        }

      if (text == null || text.equals("")){
        if (iconLeft + bor + iconWidth < w){
          iconLeft = w/2 - iconWidth/2;
          }
        }

      paintIcon(g,button,new Rectangle(iconLeft,iconTop,iconWidth,iconHeight));
      }

    if (text != null && !text.equals("")){

      FontMetrics metrics = g.getFontMetrics();
      int textHeight =  metrics.getHeight();
      int textWidth =  metrics.stringWidth(text);

      int textLeft = button.getWidth()/2 - textWidth/2;
      int textTop = button.getHeight()/2 + bor-2;

      if (icon != null){
        textLeft += icon.getIconWidth()/2 + bor/2;
        //textTop += (icon.getIconHeight()/2 - textHeight/2);
        }

      if (((textLeft <= bor) || (textLeft + textWidth + bor >= w)) && button instanceof WSButton){
        // try to get the small text
        text = ((WSButton)button).getSmallText();

        textWidth =  metrics.stringWidth(text);
        textLeft = button.getWidth()/2 - textWidth/2;

        if (icon != null){
          textLeft += icon.getIconWidth()/2 + bor/2;
          //textTop += (icon.getIconHeight()/2 - textHeight/2);
          }
        }


      if (model.isArmed() && model.isPressed()){
        //paintText(g,button,new Rectangle(textLeft,textTop,textWidth,textHeight),text);
        AquanauticPainter.paintShadowText((Graphics2D)g,text,textLeft,textTop);
        }
      else {
        //paintText(g,button,new Rectangle(textLeft,textTop,textWidth,textHeight),text);
        AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop);
        }

      }

    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  /*
  public void paintText(Graphics g, AbstractButton button, Rectangle rect, String text){
    //AquanauticPainter.paintText((Graphics2D)g,text,(int)rect.getWidth()/2,(int)rect.getHeight());
    AquanauticPainter.enableAntialias(g);
    super.paintText(g,button,rect,text);
    }
  */


  }
