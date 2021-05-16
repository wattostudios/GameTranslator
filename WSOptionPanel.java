////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
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

import org.watto.Settings;
import org.watto.component.*;
import org.watto.xml.XMLNode;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public abstract class WSOptionPanel extends WSPanel implements Comparable {

  String setting = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSOptionPanel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSOptionPanel(XMLNode node){
    super(node);
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    super.buildObject(node);

    String tag = node.getAttribute("setting");
    if (tag != null){
      setSetting(tag);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int compareTo(Object otherResource){
    return getComparison().compareTo(((WSOptionPanel)otherResource).getComparison());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract String getComparison();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getSetting(){
    return setting;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSetting(String setting){
    this.setting = setting;
    }



  }