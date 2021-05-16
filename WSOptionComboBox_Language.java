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

import org.watto.Language;
import org.watto.Settings;
import org.watto.component.*;
import org.watto.event.WSSelectableInterface;
import org.watto.xml.*;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSOptionComboBox_Language extends WSOptionComboBox {

/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSOptionComboBox_Language(XMLNode node){
    // NEED TO DO THIS HERE, OTHERWISE THE SETTING VARIABLE DOESN'T GET SAVED!!! (not sure why)
    //super(node);
    super();
    buildObject(node);
    registerEvents();
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
  public void loadComboBoxData(){
    String[] languages = Language.getLanguageNames();

    int selected = 0;
    String value = Settings.get(setting);

    for (int i=0;i<languages.length;i++){
      if (languages[i].equals(value)){
        selected = i;
        break;
        }
      }

    comboBox.setModel(new DefaultComboBoxModel(languages));
    comboBox.setSelectedIndex(selected);
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is selected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onSelect(JComponent c, Object e){
    super.onSelect(c,e);
    Language.changeLanguage(comboBox.getSelectedItem().toString());
    return true;
    }



  }