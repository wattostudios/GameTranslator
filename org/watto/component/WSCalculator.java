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


/*
// TODO
- Change the methods that use text.equals("0") to use getDisplay() instead!!!
- The display should clear when entering the second value
*/


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSCalculator extends WSPanel implements WSClickableInterface{

  double firstValue = 0;
  double secondValue = 0;
  int function = 0;

  // for doing * or / before other functions like + and -
  double priorityFirstValue = 0;
  int priorityFunction = 0;

  public static int FUNCTION_NONE = 0;
  public static int FUNCTION_ADD = 1;
  public static int FUNCTION_SUBTRACT = 2;
  public static int FUNCTION_MULTIPLY = 3;
  public static int FUNCTION_DIVIDE = 4;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSCalculator(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSCalculator(XMLNode node){
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
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    super.buildObject(node);

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File(Settings.getString("WSCalculatorXML")));

    TemporarySettings.set("CalculatorMemory","0");

    add(WSHelper.buildComponent(srcNode),BorderLayout.CENTER);
    }



/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (!(c instanceof WSButton)){
      return false;
      }

    WSButton button = (WSButton)c;
    String code = button.getCode();

    if (code.equals("CalculatorBackspace")){
      removeFromDisplay();
      }
    else if (code.equals("CalculatorClearEverything")){
      firstValue = 0;
      secondValue = 0;
      function = 0;
      }
    else if (code.equals("CalculatorClear")){
      setDisplay(0);
      }
    else if (code.equals("CalculatorMemoryClear")){
      TemporarySettings.set("CalculatorMemory","0");
      ((WSLabel)WSRepository.get("CalculatorMemoryIndicator")).setText("");
      }
    else if (code.equals("CalculatorMemoryRecall")){
      setDisplay(TemporarySettings.getDouble("CalculatorMemory"));
      }
    else if (code.equals("CalculatorMemoryStore")){
      TemporarySettings.set("CalculatorMemory",getDisplay());
      ((WSLabel)WSRepository.get("CalculatorMemoryIndicator")).setText("M");
      }
    else if (code.equals("CalculatorMemoryAdd")){
      TemporarySettings.set("CalculatorMemory",TemporarySettings.getDouble("CalculatorMemory") + getDisplay());
      ((WSLabel)WSRepository.get("CalculatorMemoryIndicator")).setText("M");
      }
    else if (code.equals("CalculatorNumber0")){
      addToDisplay("0");
      }
    else if (code.equals("CalculatorNumber1")){
      addToDisplay("1");
      }
    else if (code.equals("CalculatorNumber2")){
      addToDisplay("2");
      }
    else if (code.equals("CalculatorNumber3")){
      addToDisplay("3");
      }
    else if (code.equals("CalculatorNumber4")){
      addToDisplay("4");
      }
    else if (code.equals("CalculatorNumber5")){
      addToDisplay("5");
      }
    else if (code.equals("CalculatorNumber6")){
      addToDisplay("6");
      }
    else if (code.equals("CalculatorNumber7")){
      addToDisplay("7");
      }
    else if (code.equals("CalculatorNumber8")){
      addToDisplay("8");
      }
    else if (code.equals("CalculatorNumber9")){
      addToDisplay("9");
      }
    else if (code.equals("CalculatorDivide")){
      priorityFirstValue = getDisplay();
      priorityFunction = FUNCTION_DIVIDE;
      }
    else if (code.equals("CalculatorMultiply")){
      priorityFirstValue = getDisplay();
      priorityFunction = FUNCTION_MULTIPLY;
      }
    else if (code.equals("CalculatorSubtract")){
      firstValue = getDisplay();
      function = FUNCTION_SUBTRACT;
      }
    else if (code.equals("CalculatorAdd")){
      firstValue = getDisplay();
      function = FUNCTION_ADD;
      }
    else if (code.equals("CalculatorSquareRoot")){
      doSquareRoot();
      }
    else if (code.equals("CalculatorPercentage")){
      changeToPercentage();
      }
    else if (code.equals("CalculatorInverse")){
      doInverse();
      }
    else if (code.equals("CalculatorNegative")){
      toggleNegative();
      }
    else if (code.equals("CalculatorDecimalPoint")){
      addDecimalPoint();
      }
    else if (code.equals("CalculatorEquals")){
      secondValue = getDisplay();

      if (priorityFunction == FUNCTION_MULTIPLY){
        firstValue = priorityFirstValue * secondValue;
        }
      else if (priorityFunction == FUNCTION_DIVIDE){
        firstValue = priorityFirstValue / secondValue;
        }

      if (function == FUNCTION_ADD){
        firstValue = firstValue + secondValue;
        }
      else if (function == FUNCTION_SUBTRACT){
        firstValue = firstValue - secondValue;
        }

      function = 0;
      priorityFunction = 0;
      setDisplay(firstValue);

      }
    else {
      return false;
      }

    return true;
    }


/**
**********************************************************************************************
Adds a decimal point, if there isn't one already
**********************************************************************************************
**/
  public void addDecimalPoint(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() <= 0 || text.equals("0") || text.indexOf(".") >= 0){
      return;
      }
    display.setText(text + ".");
    }


/**
**********************************************************************************************
Adds a character to the end of the display
**********************************************************************************************
**/
  public void addToDisplay(String number){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() == 1 && text.equals("0")){
      text = "";
      }
    display.setText(text + number);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void changeToPercentage(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() <= 0 || text.equals("0")){
      return;
      }
    double value = Double.parseDouble(text)/100;
    display.setText("" + value);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void doInverse(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() <= 0 || text.equals("0")){
      return;
      }
    double value = 1 / Double.parseDouble(text);
    display.setText("" + value);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void doSquareRoot(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() <= 0 || text.equals("0")){
      return;
      }
    double value = Math.sqrt(Double.parseDouble(text));
    display.setText("" + value);
    }


/**
**********************************************************************************************
Removes the last character from the display
**********************************************************************************************
**/
  public void removeFromDisplay(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() < 1){
      return;
      }
    text = text.substring(0,text.length()-1);
    display.setText(text);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void toggleNegative(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    String text = display.getText();
    if (text.length() <= 0 || text.equals("0")){
      return;
      }
    double value = 0 - Double.parseDouble(text);
    display.setText("" + value);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public double getDisplay(){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    return Double.parseDouble(display.getText());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setDisplay(double number){
    WSTextField display = ((WSTextField)WSRepository.get("CalculatorField"));
    display.setText(number + "");
    }



  }