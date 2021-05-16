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

import java.util.*;

/**
**********************************************************************************************
An enumeration of the children in an XMLNode. Constructed by method children()
**********************************************************************************************
**/
public class XMLChildEnumeration implements Enumeration{

  /** The node whose children are being enumerated over **/
  XMLNode node;

  /** the position of the current child in the array **/
  int childPos = 0;

/**
**********************************************************************************************
Constructor
@param node the node for which the children will be enumerated
**********************************************************************************************
**/
  public XMLChildEnumeration(XMLNode node){
    this.node = node;
    }


/**
**********************************************************************************************
Are there any more children on this node?
@return true if there are more children, false if there are no more children
**********************************************************************************************
**/
  public boolean hasMoreElements(){
    if (childPos < node.getChildCount()){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Gets the next child from the node
@return the next child
**********************************************************************************************
**/
  public Object nextElement(){
    try {
      Object child = node.getChild(childPos);
      childPos++;
      return child;
      }
    catch (XMLException e){
      return null;
      }
    }


}