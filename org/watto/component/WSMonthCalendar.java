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
import org.watto.event.*;
import org.watto.xml.*;

import java.awt.*;
import java.io.File;
import java.util.Calendar;
import javax.swing.*;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSMonthCalendar extends WSPanel implements WSClickableInterface{

  WSButton nextMonth;
  WSButton previousMonth;
  WSLabel currentMonth;

  Calendar cal;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSMonthCalendar(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSMonthCalendar(XMLNode node){
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

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File(Settings.getString("WSMonthCalendarXML")));

    nextMonth = (WSButton)WSRepository.get("NextMonth");
    previousMonth = (WSButton)WSRepository.get("PreviousMonth");
    currentMonth = (WSLabel)WSRepository.get("CurrentMonth");

    // load the days and set the current month, etc.
    cal = Calendar.getInstance();
    setMonth(cal);

    add(WSHelper.buildComponent(srcNode),BorderLayout.CENTER);
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



/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (!(c instanceof WSComponent)){
      return false;
      }

    WSComponent component = (WSComponent)c;

    String code = component.getCode();

    if (code.equals("NextMonth")){
      cal.add(Calendar.MONTH, 1);
      }
    else if (code.equals("PreviousMonth")){
      cal.add(Calendar.MONTH, -1);
      }
    else if (code.equals("NextYear")){
      cal.add(Calendar.YEAR, 1);
      }
    else if (code.equals("PreviousYear")){
      cal.add(Calendar.YEAR, -1);
      }
    else if (code.indexOf("MonthCalendarDay") == 0){
      // selected a day
      String date = component.getText();
      if (date.equals(" ")){
        // empty day
        return false;
        }
      // actual day
      cal.set(Calendar.DATE,Integer.parseInt(date));
      c.setForeground(Color.RED);
      }
    else {
      return false;
      }

    setMonth(cal);
    repaint();
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMonth(Calendar cal){
    // setting the global calendar, just in case the user gives a different calendar
    this.cal = cal;

    int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    cal.set(Calendar.DAY_OF_MONTH,1);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    if (dayOfWeek == Calendar.SUNDAY){
      dayOfWeek = 0;
      }
    else if (dayOfWeek == Calendar.MONDAY){
      dayOfWeek = 1;
      }
    else if (dayOfWeek == Calendar.TUESDAY){
      dayOfWeek = 2;
      }
    else if (dayOfWeek == Calendar.WEDNESDAY){
      dayOfWeek = 3;
      }
    else if (dayOfWeek == Calendar.THURSDAY){
      dayOfWeek = 4;
      }
    else if (dayOfWeek == Calendar.FRIDAY){
      dayOfWeek = 5;
      }
    else if (dayOfWeek == Calendar.SATURDAY){
      dayOfWeek = 6;
      }

    // fill in the empty starting days
    for (int i=0;i<dayOfWeek;i++){
      Language.set("WSLabel_MonthCalendarDayR1C" + (i+1) + "_Text"," ");
      }

    int colPos = dayOfWeek;
    int rowPos = 0;

    for (int i=0;i<numDays;i++){
      Language.set("WSLabel_MonthCalendarDayR" + (rowPos+1) + "C" + (colPos+1) + "_Text","" + (i+1));
      colPos++;
      if (colPos == 7){
        colPos = 0;
        rowPos++;
        }
      }

    // fill in the empty ending days
    for (int i=(dayOfWeek+numDays);i<42;i++){ // 42 = 6 rows x 7 days
      Language.set("WSLabel_MonthCalendarDayR" + (rowPos+1) + "C" + (colPos+1) + "_Text"," ");
      colPos++;
      if (colPos == 7){
        colPos = 0;
        rowPos++;
        }
      }


    Language.set("WSLabel_CurrentMonth_Text",getMonthName(cal) + "\n" + cal.get(Calendar.YEAR));


    }



  public String getMonthName(Calendar cal){
    int month = cal.get(Calendar.MONTH);

    if (month == Calendar.JANUARY){
      return Language.get("MonthJanuary");
      }
    else if (month == Calendar.FEBRUARY){
      return Language.get("MonthFebruary");
      }
    else if (month == Calendar.MARCH){
      return Language.get("MonthMarch");
      }
    else if (month == Calendar.APRIL){
      return Language.get("MonthApril");
      }
    else if (month == Calendar.MAY){
      return Language.get("MonthMay");
      }
    else if (month == Calendar.JUNE){
      return Language.get("MonthJune");
      }
    else if (month == Calendar.JULY){
      return Language.get("MonthJuly");
      }
    else if (month == Calendar.AUGUST){
      return Language.get("MonthAugust");
      }
    else if (month == Calendar.SEPTEMBER){
      return Language.get("MonthSeptember");
      }
    else if (month == Calendar.OCTOBER){
      return Language.get("MonthOctober");
      }
    else if (month == Calendar.NOVEMBER){
      return Language.get("MonthNovember");
      }
    else if (month == Calendar.DECEMBER){
      return Language.get("MonthDecember");
      }
    else {
      return "Invalid Month";
      }

    }



  }