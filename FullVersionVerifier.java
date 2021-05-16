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

/**
**********************************************************************************************
A class that is used to verify whether the program is full version or not. Simply, if this
class exists, no errors will occur when you call <code>new FullVersionVerifier()</code>, so it
must be the full version. For basic versions of the program, do not include this class in
your distribution, therefore when <code>new FullVersionVerifier()</code> is called it will fail
and you will know it isn't the full version. You can easily put the check in a try-catch
to grab the error, like so...
<pre>
boolean fullVersion = false;
try {
  new FullVersionVerifier();
  fullVersion = true;
  }
catch (Throwable t){
  // not the full version - the class does not exist
  }
</pre>
**********************************************************************************************
**/
public class FullVersionVerifier {

/**
**********************************************************************************************
Constructor that doesn't do anything except make a blank object if this class exists, and
fails if this class doesn't exist.
**********************************************************************************************
**/
  public FullVersionVerifier(){
    //System.out.println(Language.get("Thanks"));
    }

  }