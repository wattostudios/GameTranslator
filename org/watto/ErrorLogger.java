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

package org.watto;

import org.watto.manipulator.FileManipulator;
import org.watto.manipulator.FileExtensionFilter;

import java.io.File;
import java.io.FilenameFilter;

/**
**********************************************************************************************
A singleton class that logs errors to an external file. Error logs are given a new file with
a timestamp on it, and the older logs are removed if there are more than 5 present in the log
path. Errors can also optionally be written out to the command prompt.
**********************************************************************************************
**/
public final class ErrorLogger{

  /** whether to write to the <i>System.out</i> command line in addition to writing to the file **/
  static boolean debug = true;

  /** The file to which the errors are written **/
  static FileManipulator errorLog;


/**
**********************************************************************************************
Constructor. Initializes the <i>errorLog</i> file.
**********************************************************************************************
**/
  public ErrorLogger(){
    this(new File("logs" + java.io.File.separator + "errors-" + java.util.Calendar.getInstance().getTime().toString().replaceAll(":","_") + ".log"));
    }


/**
**********************************************************************************************
Constructor. Clears the logs in the <i>path</i>, and initializes the <i>errorLog</i> file.<br>
You probably shouldn't use this constructor - it is called by the default constructor by
passing in an automatically generated filename that contains the current date and time.
@param path the directory where the error logs are stored.
**********************************************************************************************
**/
  public ErrorLogger(File path){
    clearLogs(path);
    errorLog = new FileManipulator(path,"rw");
    }


/**
**********************************************************************************************
If there are more than 5 files in the <i>logDir</i>, it will continually remove the oldest file
until there are only 5 remaining.
WARNING! This will remove any file with the extension *.log, regardless of whether the file is
a log or not. For safety, the <i>logDir</i> should be a directory that only contains error logs
in it, rather than specifying a communal directory.
@param logDir the directory that contains the log files
**********************************************************************************************
**/
  public static void clearLogs(File logDir) {
    File[] logs = new File(logDir.getParent()).listFiles((FilenameFilter)new FileExtensionFilter("log"));

    if (logs == null){
      return;
      }

    if (logs.length > 5){
      long[][] logDates = new long[logs.length][2];

      for (int i=0;i<logs.length;i++){
        logDates[i][0] = logs[i].lastModified();
        logDates[i][1] = i;
        }

      for (int i=0;i<logs.length;i++){
        for (int j=i+1;j<logs.length;j++){
          if (logDates[i][0] > logDates[j][0]){
            long[] temp = logDates[i];
            logDates[i] = logDates[j];
            logDates[j] = temp;
            }
          }
        }

      int numToRemove = logs.length - 5;
      for (int i=0;i<numToRemove;i++){
        logs[(int)logDates[i][1]].delete();
        }

      }
    }


/**
**********************************************************************************************
Closes the log file
**********************************************************************************************
**/
  public static void closeLog(){
    try {
      errorLog.close();
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************
Records the error/exception stack trace in the log file. If debug is enabled, it will also
write the error to the <i>System.out</i> command prompt
@param t the <i>Throwable</i> error/exception
**********************************************************************************************
**/
  public static void log(Throwable t){
    log("ERROR",t);
    }


/**
**********************************************************************************************
Records the error/exception stack trace in the log file. If debug is enabled, it will also
write the error to the <i>System.out</i> command prompt
@param t the <i>Throwable</i> error/exception
**********************************************************************************************
**/
  public static void log(String heading, Throwable t){
    try {
      if (debug){
        System.out.println(heading + ": " + t);
        t.printStackTrace();
        }

      errorLog.writeLine(heading + ": " + t);
      errorLog.writeLine(t.toString());

      StackTraceElement[] errorStack = t.getStackTrace();
      for (int i=0;i<errorStack.length;i++){
        errorLog.writeLine(errorStack[i].toString());
        }

      errorLog.writeLine("-======================-");
      }
    catch (Throwable thr){
      // Hopefully this never happens :)
      }
    }


/**
**********************************************************************************************
Records the String in the log file. If debug is enabled, it will also write the String to the
<i>System.out</i> command prompt
@param t the String
**********************************************************************************************
**/
  public static void log(String t){
    log("LOG",t);
    }


/**
**********************************************************************************************
Records the String in the log file. If debug is enabled, it will also write the String to the
<i>System.out</i> command prompt
@param t the String
**********************************************************************************************
**/
  public static void log(String heading, String t){
    try {
      if (debug){
        System.out.println(heading + ": " + t);
        }

      errorLog.writeLine(heading + ": " + t);
      errorLog.writeLine("-======================-");
      }
    catch (Throwable thr){
      // only occurs when the errorLog file is being set up
      }
    }


/**
**********************************************************************************************
Sets the <i>debug</i> mode on/off. If <i>enable</i>=true, errors are both written to a file AND
to the <i>System.out</i> command prompt. If <i>enable</i>=false, the errors are only recorded
in the file.
@param enable whether to enable or disable debugging output
**********************************************************************************************
**/
  public static void setDebug(boolean enable){
    debug = enable;
    }


  }

