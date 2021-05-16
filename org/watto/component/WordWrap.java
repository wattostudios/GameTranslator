////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
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

package org.watto.component;

import org.watto.*;
import org.watto.manipulator.*;
import org.watto.manipulator.StringBuffer;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
**********************************************************************************************
Methods for performing word-wrap features on Strings, such as splitting the text up into
separate lines.
**********************************************************************************************
**/
public class WordWrap {

  /** the singleton instance of this class **/
  static WordWrap instance = new WordWrap();


/**
**********************************************************************************************
Constructor - not needed because it is a singleton/static class
**********************************************************************************************
**/
  public WordWrap(){
    }


/**
**********************************************************************************************
Gets the singleton instance of this class
@return the singleton instance
**********************************************************************************************
**/
  public static WordWrap getInstance(){
    return instance;
    }


/**
**********************************************************************************************
Cuts the <i>text</i> so that each line fits in the given width of the Component
@param text the text to wordwrap
@param c the component that will contain the text
@return the text, split up into separate lines
**********************************************************************************************
**/
  public static String[] wrap(String text, JComponent c){
    int width = c.getWidth();
    Insets insets = c.getInsets();
    width -= (insets.left + insets.right);
    return wrap(text,c,width);
    }


/**
**********************************************************************************************
Cuts the <i>text</i> so that each line fits in the given <i>width</i> of the Component
@param text the text to wordwrap
@param c the component that will contain the text
@param maxWidth the maximum width of each line
@return the text, split up into separate lines
**********************************************************************************************
**/
  public static String[] wrap(String text, JComponent c, int maxWidth){
    try {

      // Allows the last word to be added to the last line.
      // Without this, we would need to add in a lot of repeating code
      // at the end to add in this extra word, whereas adding this space
      // will do it all automatically.
      text += " ";

      // get the metrics for calculating the string widths
      FontMetrics metric;
      try {
        metric = c.getGraphics().getFontMetrics();
        }
      catch (Throwable t2){
        return new String[]{text};
        }

      // set up a buffer for reading over the text
      StringBuffer buffer = new StringBuffer(text);
      FileManipulator fm = new FileManipulator(buffer);


      String[] lines = new String[0];

      // read over the string
      String line = "";
      String word = "";
      while (fm.getOffset() < fm.length()){
        char letter = (char)fm.read();

        if (letter == '\n' || letter == '\r'){
          // a new line

          // add the word to the line
          line += word;

          // add the new line
          int insertPos = lines.length;
          lines = ArrayTools.resize(lines,insertPos+1);
          lines[insertPos] = line;

          line = "";
          word = "";

          // check for \r\n
          if (letter == '\r' && fm.getOffset() < fm.length()){
            letter = (char)fm.read();
            if (letter != '\n'){
              // wasn't a \n, so was an actual character - add it to the next word
              word += letter;
              }
            }
          }
        else if (letter == ' '){
          // a separator between words

          // see if the word can be added to the line, and still be under the maxLength
          if (metric.stringWidth(line + " " + word) < maxWidth){
            // add the word, and continue
            line += " " + word;
            word = "";
            }
          else {
            // adding the word will make the line too long.

            // if there is no current line (ie this word will be the only word on this line),
            // then we want to just use the full word for this line
            if (line.equals("")){
              line = word;
              word = "";
              }

            // else, add the current line, and the word becomes the start of the next line
            int insertPos = lines.length;
            lines = ArrayTools.resize(lines,insertPos+1);
            lines[insertPos] = line;

            line = word;
            word = "";
            }
          }
        else {
          // a normal letter

          // add the letter to the current word
          word += letter;
          }
        }

      if (!line.equals("")){
        // add any leftover text
        int insertPos = lines.length;
        lines = ArrayTools.resize(lines,insertPos+1);
        lines[insertPos] = line;
        }

      return lines;
      }
    catch (Throwable t){
      return new String[]{text};
      }
    }



  }