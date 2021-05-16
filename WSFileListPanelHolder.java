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

import org.watto.*;
import org.watto.component.*;
import org.watto.xml.*;

import java.awt.BorderLayout;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSFileListPanelHolder extends WSPanel {

  WSPanel currentPanel = new WSPanel(XMLReader.readString("<WSPanel />"));

/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSFileListPanelHolder(XMLNode node){
    super(node);
    }


///////////////
//
// Configurable
//
///////////////


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    return WSHelper.buildXML(this);
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
  public Resource[] getAllSelectedFiles(){
    if (currentPanel != null && currentPanel instanceof FileListPanel){
      return ((FileListPanel)currentPanel).getSelected();
      }
    return new Resource[0];
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanel getCurrentPanel(){
    return currentPanel;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource getSelectedFile(){
    if (currentPanel != null && currentPanel instanceof FileListPanel){
      Resource[] resources = ((FileListPanel)currentPanel).getSelected();
      if (resources != null && resources.length >= 1){
        return resources[0];
        }
      }
    return null;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadPanel(String code){
    WSPanel newPanel = (WSPanel)WSPluginManager.group("FileList").getPlugin(code);
    if (newPanel == null){
      return;
      }
    else {
      if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
        ((WSPanelPlugin)currentPanel).onCloseRequest();
        }
      currentPanel = newPanel;
      if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
        ((WSPanelPlugin)currentPanel).onOpenRequest();
        }
      Settings.set("CurrentFileList",currentPanel.getCode());
      }

    loadPanel(currentPanel);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadPanel(WSPanel panel){
    if (panel == null){
      return;
      }

    if (panel instanceof FileListPanel){
      ((FileListPanel)panel).reload();
      }

    removeAll();
    add(panel,BorderLayout.CENTER);

    revalidate();
    repaint();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onCloseRequest(){
    if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
      ((WSPanelPlugin)currentPanel).onCloseRequest();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onOpenRequest(){
    if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
      ((WSPanelPlugin)currentPanel).onOpenRequest();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getFirstSelectedRow(){
    if (currentPanel instanceof FileListPanel){
      return ((FileListPanel)currentPanel).getFirstSelectedRow();
      }
    return -1;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getNumSelected(){
    if (currentPanel instanceof FileListPanel){
      return ((FileListPanel)currentPanel).getNumSelected();
      }
    return 0;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource getResource(int row){
    if (currentPanel instanceof FileListPanel){
      return ((FileListPanel)currentPanel).getResource(row);
      }
    return null;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource[] getSelected(){
    if (currentPanel instanceof FileListPanel){
      return ((FileListPanel)currentPanel).getSelected();
      }
    return new Resource[0];
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload(){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).reload();
      }
    revalidate();
    repaint();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectAll(){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).selectAll();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectInverse(){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).selectInverse();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectNone(){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).selectNone();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void selectResource(int row){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).selectResource(row);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void stopInlineEditing(){
    if (currentPanel instanceof FileListPanel){
      ((FileListPanel)currentPanel).stopInlineEditing();
      }
    }


/**
**********************************************************************************************
Rebuilds the panels from their XML
**********************************************************************************************
**/
  public void rebuild(){
    WSPlugin[] plugins = WSPluginManager.group("FileList").getPlugins();
    for (int i=0;i<plugins.length;i++){
      ((FileListPanel)plugins[i]).constructInterface();
      }
    reload();
    }


  }