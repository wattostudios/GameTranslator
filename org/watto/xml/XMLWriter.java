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

import org.watto.*;
import org.watto.manipulator.*;

import java.io.*;

/**
**********************************************************************************************
A static class that writes a tree of <i>XMLNode</i>s to an output file.
**********************************************************************************************
**/
public class XMLWriter {

  /** The stream for writing the file **/
  static FileManipulator fm = null;

  /** the depth of te currently-processed tag - used for padding the lines **/
  static int level = 0;

  /** is this the start of a new line - used to determine when to apply line padding **/
  static boolean newLine = true;

  /** whether to write in Unicode or ASCII format **/
  static boolean unicode = false;


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public XMLWriter() {
    }


/**
**********************************************************************************************
Writes the tree at <i>root</i> to the file <i>path</i> using ASCII formatting
@param path the output file
@param root the tree
**********************************************************************************************
**/
  public static void write(File path, XMLNode root){
    write(path,root,false);
    }


/**
**********************************************************************************************
Writes the tree at <i>root</i> to the file <i>fmIn</i> using ASCII formatting
@param fmIn the file to write to
@param root the tree
**********************************************************************************************
**/
  public static void write(FileManipulator fmIn, XMLNode root){
    write(fmIn,root,false);
    }


/**
**********************************************************************************************
Writes the tree at <i>root</i> to the file <i>fmIn</i> using ASCII formatting. Assumes that
the file has already been opened via setFile();
@param root the tree
**********************************************************************************************
**/
  public static void write(XMLNode root){
    write(fm,root);
    }


/**
**********************************************************************************************
Writes the tree at <i>root</i> to the file <i>path</i>
@param path the output file
@param root the tree
@param unicode whether to output in Unicode or ASCII format
**********************************************************************************************
**/
  public static void write(File path, XMLNode root, boolean unicodeIn){
    try {
      unicode = unicodeIn;

      if (path.exists()){
        path.delete();
        }

      fm = new FileManipulator(path,"rw");

      if (unicode){
        // write unicode header
        fm.writeByte((byte)255);
        fm.writeByte((byte)254);
        }

      level = 0;

      writeTag(root);

      File writePath = fm.getFile();
      fm.close();

      //if (writePath.getAbsolutePath() != path.getAbsolutePath()){
      //  path.delete();
      //  writePath.renameTo(path);
      //  }



      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes the tree at <i>root</i> to the file <i>fmIn</i>
@param fmIn the file to write to
@param root the tree
@param unicode whether to output in Unicode or ASCII format
**********************************************************************************************
**/
  public static void write(FileManipulator fmIn, XMLNode root, boolean unicodeIn){
    try {
      unicode = unicodeIn;

      fm = fmIn;

      if (unicode){
        // write unicode header
        fm.writeByte((byte)255);
        fm.writeByte((byte)254);
        }

      level = 0;

      writeTag(root);

      File writePath = fm.getFile();
      fm.close();

      //if (writePath.getAbsolutePath() != path.getAbsolutePath()){
      //  path.delete();
      //  writePath.renameTo(path);
      //  }



      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setFile(FileManipulator fmIn){
    setFile(fmIn,false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setFile(FileManipulator fmIn, boolean unicodeIn){
    try {
      unicode = unicodeIn;

      fm = fmIn;

      if (unicode){
        // write unicode header
        fm.writeByte((byte)255);
        fm.writeByte((byte)254);
        }

      level = 0;

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes the XML Header
**********************************************************************************************
**/
  public static void writeHeader(double version, String encoding){
    writeLine("<?xml version=\"" + version + "\" encoding=\"" + encoding + "\"?>");
    }


/**
**********************************************************************************************
Writes the XML DocType
**********************************************************************************************
**/
  public static void writeDocType(String docFile){
    writeLine("<!DOCTYPE resources SYSTEM \"" + docFile + "\">");
    }



/**
**********************************************************************************************
Appends the data to the current line of the output
@param partialLine a piece of line data
**********************************************************************************************
**/
  public static void write(String partialLine){
    try {
      if (newLine){
        writeLevel();
        newLine = false;
        }

      if (unicode){
        fm.writeUnicodeString(partialLine);
        }
      else {
        fm.writeString(partialLine);
        }
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Pads the beginning of the line out to the correct level
@param partialLine a piece of line data
**********************************************************************************************
**/
  public static void writeLevel(){
    try {
      if (unicode){
        for (int i=0;i<level;i++){
          fm.writeUnicodeString("\t");
          }
        }
      else {
        for (int i=0;i<level;i++){
          fm.writeString("\t");
          }
        }
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes a line of data to the output
@param line a line of data
**********************************************************************************************
**/
  public static void writeLine(String line){
    write(line);
    nextLine();
    }


/**
**********************************************************************************************
Sets the output up for the next line of input
**********************************************************************************************
**/
  public static void nextLine(){
    try {
      if (unicode){
        fm.writeUnicodeString("\n\r");
        }
      else {
        fm.writeString("\n\r");
        }
      newLine = true;
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes a single tag at the current position
@param root the tag
**********************************************************************************************
**/
  public static void writeTag(XMLNode root){
    try {

      String name = root.getTag();

      // write the start tag
      write("<" + name);

      if (root.hasAttributes()){
        // write the attributes
        writeAttributes(root);
        }


      if (root.hasChildren() && root.hasContent()){
        // close the start tag
        write (">");
        nextLine();

        // write the children
        level++;

        XMLNode[] children = root.getChildren();
        for (int i=0;i<children.length;i++){
          writeTag(children[i]);
          }

        level--;

        // write the content
        writeText(root);

        // write the end tag
        write("</" + name + ">");
        nextLine();
        }

      else if (root.hasChildren()){
        // close the start tag
        write (">");
        nextLine();

        // write the children
        level++;

        XMLNode[] children = root.getChildren();
        for (int i=0;i<children.length;i++){
          writeTag(children[i]);
          }

        level--;

        // write the end tag
        write("</" + name + ">");
        nextLine();
        }

      else if (root.hasContent()){
        // close the start tag
        write(">");

        // write the content
        writeText(root);

        // write the end tag
        write("</" + name + ">");
        nextLine();
        }

      else {
        // close the single tag
        write (" />");
        nextLine();
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes the text of the tag
@param root the tag containing the text
**********************************************************************************************
**/
  public static void writeText(XMLNode root){
    try {
      write(root.getContent());
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Writes the attributes of the tag
@param root the tag with the attributes
**********************************************************************************************
**/
  public static void writeAttributes(XMLNode root){
    try {

      String[][] attributes = root.getAttributes();

      for (int i=0;i<attributes.length;i++){
        write(" " + attributes[i][0] + "=\"" + attributes[i][1] + "\"");
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }



/**
**********************************************************************************************
Prints an error report
@param t the error that occurred.
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }


  }

