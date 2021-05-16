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

import java.util.Hashtable;
import java.awt.*;
import javax.swing.*;

/**
**********************************************************************************************
Maintains a list of ButtonGroups used for components like WSRadioButton, and controls the
creation and adding of components to the groups. If you intend to use ButtonGroups as well as
components that use this class, you should convert all components to use this class so that
there is no confusion and no chance of reading the wrong thing.
**********************************************************************************************
**/
public class ButtonGroupManager {

  /** the groups **/
  static Hashtable groups = new Hashtable(5);

/**
**********************************************************************************************
Constructor if you want to use it, but is isn't necessary.
**********************************************************************************************
**/
  public ButtonGroupManager() {
    }


/**
**********************************************************************************************
Sets the group of a <i>component</i>. If the group does not exist, a new ButtonGroup is
created, otherwise the <i>component</i> is added to the existing ButtonGroup with the
<i>groupName</i>
@param groupName the name of the group that the <i>component</i> should be added to
@param component the component to add to the group
**********************************************************************************************
**/
  public static void setGroup(String groupName, AbstractButton component){
    Object groupExists = groups.get(groupName);
    ButtonGroup group;
    if (groupExists != null){
      group = (ButtonGroup)groupExists;
      }
    else {
      group = new ButtonGroup();
      }
    group.add(component);
    groups.put(groupName,group);
    }


  }

