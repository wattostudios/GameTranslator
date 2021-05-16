////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
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

package org.watto.event;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.*;

/**
**********************************************************************************************
An interface that accepts table column events from a WSTable. Note that there is no object
called WSColumnableListener - this is because the table columns are listened to internally
by a JTable rather then registering listeners. In effect, WSTable becomes a listener in
itself, as it passes table column events to this class.
**********************************************************************************************
**/
public interface WSTableColumnableInterface{


/**
**********************************************************************************************
The event that is triggered from a WSTable when a table column is resized
@param c the component that triggered the event
@param columns the columns of the table
@param e the event that occurred
**********************************************************************************************
**/
  //public void onColumnResize(JTable c, Enumeration columns, javax.swing.event.ChangeEvent e);
  public boolean onColumnResize(TableColumnModel c, javax.swing.event.ChangeEvent e);


  }