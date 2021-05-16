////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       GAME EXTRACTOR                                       //
//                               Extensible Game Archive Editor                               //
//                                http://www.watto.org/extract                                //
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

import org.watto.component.WSComboBox;
import org.watto.xml.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class DirectoryListDrivesComboBox extends WSComboBox{

  static File[] drives = null;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public DirectoryListDrivesComboBox() {
    super(new XMLNode(""));
    if (drives == null){
      loadDrives();
      }
    setRenderer(new DirectoryListDrivesComboBoxCellRenderer());
    setCurrentValueRenderer(new DirectoryListDrivesComboBoxCurrentValueCellRenderer());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDrives() {

    drives = new File[100];
    int numDrives = 0;

    // Add roots (drives)
    File[] roots = File.listRoots();
    for (int i=0;i<roots.length;i++){
      drives[numDrives] = roots[i];
      numDrives++;
      }

    /*
    // Add roots (My Documents, etc)
    FileSystemView fsv = FileSystemView.getFileSystemView();

    roots = fsv.getRoots();
    for (int i=0;i<roots.length;i++){
      drives[numDrives] = roots[i];
      numDrives++;
      }
    */

    File[] temp = new File[numDrives];
    System.arraycopy(drives,0,temp,0,numDrives);
    drives = temp;

    setModel(new DefaultComboBoxModel(drives));

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDirectory(File directory) {
    setModel(new DefaultComboBoxModel(drives));
    int insertPos = getItemCount();


    File drive = directory;
    File parent = directory.getParentFile();
    while (parent != null){
      drive = parent;
      parent = parent.getParentFile();
      }
    for (int i=0;i<getItemCount();i++){
      if (((File)getItemAt(i)).getAbsolutePath().equals(drive.getAbsolutePath())){
        insertPos = i+1;
        break;
        }
      }


    int selectPos = insertPos;
    // don't add the item if it is a drive, as the drive letters already exist in the list normally.
    if (directory.getParentFile() != null){
      insertItemAt(directory,insertPos);
      }
    else {
      selectPos--;
      }


    directory = directory.getParentFile();
    while (directory != null){
      File parentDirectory = directory.getParentFile();
      if (parentDirectory != null){
        insertItemAt(directory,insertPos);
        selectPos++;
        }
      directory = parentDirectory;
      }

    setSelectedIndex(selectPos);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDirectory() {
    setModel(new DefaultComboBoxModel(drives));
    }



/**
**********************************************************************************************
Overwritten for performance reasons - makes it much faster!
Overwritten, so it does not trigger a selectedItemChanged() when a change occurs. We already
handle the event with our listener anyway.
**********************************************************************************************
**/
    public void setSelectedItem(Object anObject) {
    Object oldSelection = selectedItemReminder;
        Object objectToSelect = anObject;
    if (oldSelection == null || !oldSelection.equals(anObject)) {

        if (anObject != null && !isEditable()) {
        // For non editable combo boxes, an invalid selection
        // will be rejected.
        boolean found = false;
        for (int i = 0; i < dataModel.getSize(); i++) {
                    Object element = dataModel.getElementAt(i);
            if (anObject.equals(element)) {
            found = true;
                        objectToSelect = element;
            break;
            }
        }
        if (!found) {
            return;
        }
        }


        // Must toggle the state of this flag since this method
        // call may result in ListDataEvents being fired.
        boolean selectingItem = true;
        dataModel.setSelectedItem(objectToSelect);
        selectingItem = false;

        if (selectedItemReminder != dataModel.getSelectedItem()) {
        // in case a users implementation of ComboBoxModel
        // doesn't fire a ListDataEvent when the selection
        // changes.
        //selectedItemChanged(); // changed
        }
    }
    fireActionEvent();
    }


/**
**********************************************************************************************
Overwritten for performance reasons - makes it much faster!
Overwritten, so it removeListDataListener() instead of adding it.
**********************************************************************************************
**/
    public void setModel(ComboBoxModel aModel) {
        ComboBoxModel oldModel = dataModel;
    if (oldModel != null) {
        oldModel.removeListDataListener(this);
    }
        dataModel = aModel;

    dataModel.removeListDataListener(this); // changed

    // set the current selected item.
    selectedItemReminder = dataModel.getSelectedItem();
        firePropertyChange( "model", oldModel, dataModel);
    }


  }