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

import org.watto.Settings;
import org.watto.xml.*;

import javax.swing.JComponent;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSRecentFileMenu extends WSMenu {


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSRecentFileMenu(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSRecentFileMenu(XMLNode node){
    super();
    buildObject(node);
    registerEvents();
    }



///////////////
//
// Configurable
//
///////////////

/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    WSHelper.buildObject(node,this);

    if (node.getAttribute("code") == null){
      setCode("RecentFileMenu");
      }

    //WSRepository.add(this);
    setIcons();

    addMenuItems();
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);
    return node;
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addMenuItems(){
    int numRecentFiles = Settings.getInt("NumberOfRecentFiles");

    for (int i=1;i<=numRecentFiles;i++){ // start at 1 because the 1st recent file is #1, not #0
      String recentFile = Settings.getString("RecentFile"+i);

      if (recentFile == null || recentFile.equals("")){
        // no more recent files
        break;
        }
      else {
        // add a recent file to the menu
        WSRecentFileMenuItem item = new WSRecentFileMenuItem(XMLReader.readString("<WSRecentFileMenuItem code=\""+recentFile+"\" />"));
        //item.addActionListener(new WSMenuableListener(this));

        if (i < 10){
          item.setMnemonic(WSHelper.parseMnemonic("I"));
          item.setAccelerator(WSHelper.parseShortcut("ctrl " + i));
          }

        add(item);
        }

      }
    }


/**
**********************************************************************************************
Rebuilds the list of recent files from the Settings
**********************************************************************************************
**/
  public void rebuild(){
    removeAll();
    addMenuItems();
    }




  }