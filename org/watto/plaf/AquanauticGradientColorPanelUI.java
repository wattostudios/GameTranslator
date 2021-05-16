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

public class AquanauticGradientColorPanelUI extends BasicPanelUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticGradientColorPanelUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    WSGradientColorPanel panel = (WSGradientColorPanel)c;
    Color color = panel.getColor();
    Color selectedColor = panel.getSelectedColor();

    int selRed = selectedColor.getRed();
    int selGreen = selectedColor.getGreen();
    int selBlue = selectedColor.getBlue();

    boolean foundDot = false;
    int selPosX = 255;
    int selPosY = 0;

    Dimension dotPos = panel.getDotPos();
    if (dotPos != null){
      selPosX = (int)dotPos.getWidth();
      selPosY = (int)dotPos.getHeight();
      foundDot = true;
      }

    double red   = color.getRed();
    double green = color.getGreen();
    double blue  = color.getBlue();

    int lastPixel = 256*256;
    int[] colors = new int[lastPixel];

    lastPixel--;

    for (int d=0;d<256;d++){
      double redBlack   = 255 - ((double)d)/255*red;
      double greenBlack = 255 - ((double)d)/255*green;
      double blueBlack  = 255 - ((double)d)/255*blue;

      for (int l=0;l<256;l++){
        int redBlackWhite   = (int)(255 - ((255-((double)l))/255*redBlack));
        int greenBlackWhite = (int)(255 - ((255-((double)l))/255*greenBlack));
        int blueBlackWhite  = (int)(255 - ((255-((double)l))/255*blueBlack));

        if (!foundDot && redBlackWhite == selRed && greenBlackWhite == selGreen && blueBlackWhite == selBlue){
          selPosX = 255 - l;
          selPosY = 255 - d;
          foundDot = true;
          }

        int colorNum = lastPixel - (d*256 + l);
        colors[colorNum] = ((255 << 24) | (redBlackWhite<<16) | (greenBlackWhite<<8) | blueBlackWhite);
        }
      }


    Image image = c.createImage(new MemoryImageSource(256,256,ColorModel.getRGBdefault(),colors,0,256));
    g.drawImage(image,0,0,null);


    // circle the selected color
    selPosX -= 5;
    selPosY -= 5;

    AquanauticPainter.enableAntialias((Graphics2D)g);

    g.setColor(Color.BLACK);
    g.drawOval(selPosX+1,selPosY+1,8,8);
    g.drawOval(selPosX-1,selPosY-1,12,12);

    g.setColor(Color.WHITE);
    g.drawOval(selPosX,selPosY,10,10);

    }


  }
