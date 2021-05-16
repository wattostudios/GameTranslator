////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       XML UTILITIES                                        //
//                Java Classes for Reading, Writing, and Manipulating XML Files               //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2003-2008  WATTO Studios                           //
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

package org.watto.xml;

import org.watto.ErrorLogger;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import javax.swing.ImageIcon;


/**
**********************************************************************************************

**********************************************************************************************
**/
public class XHTMLRenderer extends DefaultMutableTreeNode{


/**
**********************************************************************************************

**********************************************************************************************
**/
  public XHTMLRenderer(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension paint(XMLNode tree, Graphics g,  int x, int y, int width, int height){

    String tag = tree.getTag();

    // paint text if it is a text node
    if (tag.equals("!TEXT!")){
      return paintText(tree,g,x,y,width,height);
      }


    int textHeight = g.getFontMetrics().getHeight();

    // GETTING THE OLD VALUES (such as colors)
    Color oldColor = g.getColor();


    // APPLYING THE ATTRIBUTES FOR THIS TAG
    String newColor = (String)tree.getAttribute("color");
    g.setColor(parseColor(newColor));


    int componentHeight = 0;
    int componentWidth = 0;

    // BEFORE RENDERING TASKS

    if (tag.equals("li")){
      // paint bullet
      g.fillOval(x,y+(textHeight/2)-7,6,6);

      // adjust sizes/positions so the text doesn't overwrite the bullet
      width = width - 10;
      x = x + 10;
      }
    else if (tag.equals("span")){
      Object borderWidthValue = tree.getAttribute("border-width");
      if (borderWidthValue != null){
        int borderWidth = Integer.parseInt((String)borderWidthValue);

        // set up the children paint area
        width -= borderWidth*4;
        height -= borderWidth*4;
        x += borderWidth*2;
        y += borderWidth*2;

        }
      }
    else if (tag.equals("ul") || tag.equals("hr")){
      componentHeight += textHeight;
      }


    // RENDERING SINGLE TAGS

    // single tags
    if (tag.equals("hr")){
      int hrTop = y+componentHeight + 2;
      g.drawLine(x+10,hrTop,width+x-10,hrTop);
      g.drawLine(x+10,hrTop+2,width+x-10,hrTop+2);

      Color oldHRColor = g.getColor();
      g.setColor(oldHRColor.brighter());
      g.drawLine(x+10,hrTop+1,width+x-10,hrTop+1);
      g.setColor(oldHRColor);
      }
    else if (tag.equals("img")){
      try {
        String src = (String)tree.getAttribute("src");
        //ImageIcon icon = new ImageIcon(getClass().getResource(src));
        ImageIcon icon = new ImageIcon(org.watto.component.WSHelper.getResource(src));

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        int imgTop = y+componentHeight + 2;
        int imgLeft = x+componentWidth + 2;

        if (isCentered(tree)){
          imgLeft += (width/2) - (iconWidth/2);
          }

        g.drawImage(icon.getImage(),imgLeft,imgTop,iconWidth,iconHeight,null);

        componentHeight += iconHeight + 4;
        componentWidth += iconWidth + 4;
        }
      catch (Throwable t){
        ErrorLogger.log(t);
        }
      }


    // RENDERING THE CHILDREN


    int numChildren = tree.getChildCount();

    for (int i=0;i<numChildren;i++){
      //Graphics t = g.create(0,componentHeight,width,height-componentHeight);

      Dimension childSize = paint(((XMLNode)tree.getChildAt(i)),g,x,y+componentHeight,width,height);
      componentHeight += (int)childSize.getHeight();

      int childWidth = (int)childSize.getWidth();
      if (childWidth > componentWidth){
        componentWidth = childWidth;
        }
      }



    // AFTER RENDERING TASKS
    if (tag.equals("br") || tag.equals("li") || tag.equals("hr")){
      componentHeight += textHeight;
      }
    else if (tag.equals("u")){
      // paint underline
      int uTop = y+componentHeight + textHeight - 5;
      int uLeft = x+(width/2)-componentWidth/2;
      g.drawLine(uLeft,uTop,uLeft+componentWidth,uTop);
      }
    else if (tag.equals("span")){
      Object borderWidthValue = tree.getAttribute("border-width");
      if (borderWidthValue != null){
        int borderWidth = Integer.parseInt((String)borderWidthValue);

        // set up the border paint area
        width += borderWidth*6;
        height = componentHeight + borderWidth*11;
        x -= borderWidth*4;
        y -= borderWidth*4;

        // paint the new border
        for (int i=0;i<borderWidth;i++){
          g.drawRect(x+i,y+i,width-i-i-1,height-i-i-1);
          }

        componentHeight += borderWidth*4;

        }
      }



    // SETTING BACK THE OLD VALUES
    g.setColor(oldColor);


    return new Dimension(componentWidth,componentHeight);


    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension paintText(XMLNode tree, Graphics g,  int x, int y, int width, int height){

    boolean centered = isCentered(tree);

    int componentHeight = 0;
    int componentWidth = 0;

    int textHeight = 0;
    int textWidth = 0;

    String text = tree.getContent();


    if (text.equals("")){
      // keep it centered for the top and left, and don't change the width of height
      }
    else {
      // determine the size, the top position, and the left position of the text
      g.setFont(g.getFont().deriveFont(Font.BOLD));
      FontMetrics metrics = g.getFontMetrics();

      textHeight =  metrics.getHeight();
      textWidth =  metrics.stringWidth(text);

      String[] lines;

      if (textWidth > width){
        lines = splitText(text,metrics,width);
        }
      else {
        lines = new String[]{text};
        }


      componentHeight = textHeight*(lines.length-1);


      int top = y + textHeight/2;
      for (int i=0;i<lines.length;i++){
        int lineWidth = metrics.stringWidth(lines[i]);

        int left = x;
        if (centered){
          left += (width/2) - (lineWidth/2);
          }

        paintLine(g,left,top,lines[i]);

        top += textHeight;

        if (lineWidth > componentWidth){
          componentWidth = lineWidth;
          }

        }

      }


    return new Dimension(componentWidth,componentHeight);

    }



/**
**********************************************************************************************
  Changes the color name into the actual Color object
**********************************************************************************************
**/
  public Color parseColor(String colorName){

    if (colorName == null){
      return new Color(255,255,255);
      }
    else if (colorName.equals("red")){
      return new Color(200,50,50);
      }
    else if (colorName.equals("blue")){
      return new Color(50,50,200);
      }
    else if (colorName.equals("green")){
      return new Color(50,150,50);
      }
    else if (colorName.equals("purple")){
      return new Color(200,50,200);
      }
    else if (colorName.equals("orange")){
      return new Color(200,150,50);
      }
    else if (colorName.equals("yellow")){
      return new Color(200,200,50);
      }
    else {
      return new Color(255,255,255);
      }
    }


/**
**********************************************************************************************
  Determines whether the text is centered (by looking for a <center> tag in one of the parents)
**********************************************************************************************
**/
  public boolean isCentered(XMLNode tree){
    try {
      XMLNode parent = (XMLNode)tree.getParent();
      while (parent != null){
        String parentTag = parent.getTag();
        if (parentTag.equalsIgnoreCase("center")){
          return true;
          }
        else {
          parent = (XMLNode)parent.getParent();
          }
        }

      return false;
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      return false;
      }
    }


/**
**********************************************************************************************
  Paints the component onto the graphic
**********************************************************************************************
**/
  public String[] splitText(String text, FontMetrics metrics, int width){

    String[] words = text.split(" ");
    String[] lines = new String[words.length];
    int numLines = 0;

    String line = words[0];
    for (int i=1;i<words.length;i++){
      if (metrics.stringWidth(line + " " + words[i]) < width){
        line += " " + words[i];
        }
      else {
        lines[numLines] = line;
        numLines++;

        line = words[i];
        }
      }

    lines[numLines] = line;
    numLines++;

    String[] temp = lines;
    lines = new String[numLines];
    System.arraycopy(temp,0,lines,0,numLines);

    return lines;

    }


/**
**********************************************************************************************
  Paints the component onto the graphic
**********************************************************************************************
**/
  public void paintLine(Graphics g, int x, int y, String line){
    g.drawString(line,x,y);
    }



}