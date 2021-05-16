////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      AQUANAUTIC THEME                                      //
//                                  A Look And Feel For Java                                  //
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

package org.watto.plaf;

import org.watto.Settings;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class AquanauticLookAndFeel extends MetalLookAndFeel {

  private static boolean isInstalled = false;
  static String packageName = "org.watto.plaf.";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticLookAndFeel() {

    // load the font from the settings
    try {
      String fontName = Settings.get("WSFontChooser_FontName_Selected");
      String fontSize = Settings.get("WSFontChooser_FontSize_Selected");
      String fontStyle = Settings.get("WSFontChooser_FontStyle_Selected");

      AquanauticTheme.setFont(Font.decode(fontName+"-"+fontStyle+"-"+fontSize));
      }
    catch (Throwable t){
      }

    if (!isInstalled) {
      UIManager.installLookAndFeel("Aquanautic", packageName + "AquanauticLookAndFeel");
      isInstalled = true;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getDescription() {
    return "WATTO Studios - Aquanautic Theme - http://www.watto.org";
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getID() {
    return "Aquanautic";
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getName() {
    return "Aquanautic";
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UIDefaults getDefaults() {
    UIDefaults table = new UIDefaults();

    initClassDefaults(table);
    initSystemColorDefaults(table);
    initComponentDefaults(table);

    return table;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void initClassDefaults(UIDefaults table) {
    super.initClassDefaults(table);

    table.put("ButtonUI",       packageName + "AquanauticButtonUI");
    table.put("ScrollBarUI",    packageName + "AquanauticScrollBarUI");
    table.put("ScrollPaneUI",   packageName + "AquanauticScrollPaneUI");
    table.put("LabelUI",        packageName + "AquanauticLabelUI");
    table.put("ProgressBarUI",  packageName + "AquanauticProgressBarUI");
    table.put("ToolBarUI",      packageName + "AquanauticToolBarUI");
    table.put("ToolBarSeparatorUI",   packageName + "AquanauticToolBarSeparatorUI");
    table.put("MenuBarUI",      packageName + "AquanauticMenuBarUI");
    table.put("MenuUI",         packageName + "AquanauticMenuUI");
    table.put("MenuItemUI",     packageName + "AquanauticMenuItemUI");
    table.put("PopupMenuUI",    packageName + "AquanauticPopupMenuUI");
    table.put("PopupMenuSeparatorUI", packageName + "AquanauticPopupMenuSeparatorUI");
    table.put("PanelUI",        packageName + "AquanauticPanelUI");
    table.put("ComboBoxUI",     packageName + "AquanauticComboBoxUI");
    table.put("CheckBoxUI",     packageName + "AquanauticCheckBoxUI");
    table.put("ListUI",         packageName + "AquanauticListUI");
    table.put("TextFieldUI",    packageName + "AquanauticTextFieldUI");
    table.put("TextAreaUI",     packageName + "AquanauticTextAreaUI");
    table.put("SliderUI",       packageName + "AquanauticSliderUI");
    table.put("TreeUI",         packageName + "AquanauticTreeUI");
    table.put("ToolTipUI",      packageName + "AquanauticToolTipUI");
    table.put("RadioButtonUI",  packageName + "AquanauticRadioButtonUI");
    table.put("ToggleButtonUI", packageName + "AquanauticToggleButtonUI");
    table.put("TableUI",        packageName + "AquanauticTableUI");
    table.put("FileChooserUI",  packageName + "AquanauticFileChooserUI");
    table.put("SplitPaneUI",    packageName + "AquanauticSplitPaneUI");
    table.put("EditorPaneUI",   packageName + "AquanauticEditorPaneUI");

    //table.put("ViewportUI","AquanauticViewportUI");
    //table.put("InternalFrameUI","AquanauticInternalFrameUI");
    //table.put("RootPaneUI","AquanauticRootPaneUI");
    //table.put("TabbedPaneUI","AquanauticTabbedPaneUI");
    //table.put("PasswordFieldUI","AquanauticPasswordFieldUI");
    //table.put("TableHeaderUI","AquanauticHeaderUI");
    //table.put("ColorChooserUI","AquanauticColorChooserUI");
    //table.put("FormattedTextFieldUI","AquanauticFormattedTextFieldUI");
    //table.put("CheckBoxMenuItemUI","AquanauticCheckBoxMenuItemUI");
    //table.put("RadioButtonMenuItemUI","AquanauticRadioButtonMenuItemUI");
    //table.put("SeparatorUI","AquanauticSeparatorUI");
    //table.put("SpinnerUI","AquanauticSpinnerUI");
    //table.put("ToolBarSeparatorUI","AquanauticToolBarSeparatorUI");
    //table.put("TextPaneUI","AquanauticTextPaneUI");
    //table.put("EditorPaneUI","AquanauticEditorPaneUI");
    //table.put("TableHeaderUI","AquanauticTableHeaderUI");
    //table.put("DesktopPaneUI","AquanauticDesktopPaneUI");
    //table.put("DesktopIconUI","AquanauticDesktopIconUI");
    //table.put("OptionPaneUI","AquanauticOptionPaneUI");

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
    static boolean usingOcean() {
      return false;
      }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void initSystemColorDefaults(UIDefaults table){
    super.initSystemColorDefaults(table);

    table.put("desktop",new ColorUIResource(AquanauticTheme.COLOR_BG));
    table.put("activeCaptionBorder",new ColorUIResource(AquanauticTheme.COLOR_MID));
    table.put("inactiveCaption",new ColorUIResource(AquanauticTheme.COLOR_LIGHT));
    table.put("inactiveCaptionText",new ColorUIResource(AquanauticTheme.COLOR_MID));
    table.put("inactiveCaptionBorder",new ColorUIResource(AquanauticTheme.COLOR_MID));
    table.put("menu",new ColorUIResource(AquanauticTheme.COLOR_DARK));
    table.put("text",new ColorUIResource(AquanauticTheme.COLOR_MID));
    table.put("textInactiveText",new ColorUIResource(AquanauticTheme.COLOR_LIGHT));
    table.put("control",new ColorUIResource(AquanauticTheme.COLOR_BG));
    table.put("controlHighlight",new ColorUIResource(AquanauticTheme.COLOR_MID));
    table.put("controlShadow",new ColorUIResource(AquanauticTheme.COLOR_LIGHT));
    table.put("scrollbar",new ColorUIResource(AquanauticTheme.COLOR_DARK));
    table.put("info",new ColorUIResource(AquanauticTheme.COLOR_DARK));
    table.put("infoText",new ColorUIResource(AquanauticTheme.COLOR_LIGHT));



    table.put("activeCaption",new ColorUIResource(Color.BLACK));
    table.put("activeCaptionText",new ColorUIResource(Color.WHITE));
    table.put("window",new ColorUIResource(Color.WHITE));
    table.put("windowBorder",new ColorUIResource(Color.BLACK));
    table.put("windowText",new ColorUIResource(Color.BLACK));
    table.put("menuText",new ColorUIResource(Color.BLACK));
    table.put("textText",new ColorUIResource(Color.BLACK));
    table.put("textHighlight",new ColorUIResource(Color.BLACK));
    table.put("textHighlightText",new ColorUIResource(Color.WHITE));
    table.put("controlText",new ColorUIResource(Color.BLACK));
    table.put("controlLtHighlight",new ColorUIResource(Color.WHITE));
    table.put("controlDkShadow",new ColorUIResource(Color.BLACK));


    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void installColors(JComponent c, String defaultBgName, String defaultFgName){
    Color bg = c.getBackground();
    if (bg == null || bg instanceof UIResource){
      c.setBackground(AquanauticTheme.COLOR_BG);
      }

    Color fg = c.getForeground();
    if (fg == null || fg instanceof UIResource) {
      c.setForeground(AquanauticTheme.COLOR_TEXT);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void createDefaultTheme() {
    getCurrentTheme();
    }


  public static MetalTheme getCurrentTheme() {
    AquanauticTheme theme = new AquanauticTheme();
    setCurrentTheme(theme);
    return theme;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);

    int bor = AquanauticTheme.BORDER_WIDTH;
    int menubor = AquanauticTheme.MENU_BORDER_WIDTH;
    //int pad = AquanauticTheme.PADDING_WIDTH;

    EmptyBorder noBorder = new EmptyBorder(0,0,0,0);
    EmptyBorder oneBorder = new EmptyBorder(1,1,1,1);
    EmptyBorder borBorder = new EmptyBorder(bor,bor,bor,bor);
    //EmptyBorder menuBorder = new EmptyBorder(menubor,menubor,menubor-1,menubor);
    EmptyBorder menuBorder = new EmptyBorder(menubor,menubor,menubor,menubor);

    Color darkColor = new ColorUIResource(AquanauticTheme.COLOR_DARK);
    Color lightColor = new ColorUIResource(AquanauticTheme.COLOR_LIGHT);
    Color midColor = new ColorUIResource(AquanauticTheme.COLOR_MID);

    Color bgColor = new ColorUIResource(AquanauticTheme.COLOR_BG);
    Color textColor = new ColorUIResource(AquanauticTheme.COLOR_TEXT);

    Color noColor = new Color(0,0,0,0);

    Font font = AquanauticTheme.FONT;
    Font fontBold = AquanauticTheme.FONT.deriveFont(Font.BOLD);

    Object[] newValues = new Object[]{
      "Button.border", borBorder,
      "Button.font", fontBold,

      "CheckBox.font", fontBold,

      "ComboBox.font", fontBold,

      "EditorPane.border", noBorder,

      "FormattedTextField.border", noBorder,
      "FormattedTextField.margin", new Insets(0,0,0,0),

      "Label.font", fontBold,

      "List.background", bgColor,
      "List.border",oneBorder,
      "List.focusCellHighlightBorder", oneBorder,
      "List.font", font,
      "List.selectionBackground", midColor,
      "List.selectionForeground", darkColor,

      "Menu.border", new EmptyBorder(menubor+1,menubor,menubor,menubor),
      "Menu.font", fontBold,

      "MenuBar.border", menuBorder,
      "MenuBar.font", fontBold,

      "MenuItem.acceleratorForeground",textColor,
      "MenuItem.acceleratorSelectionForeground",textColor,
      "MenuItem.border", new EmptyBorder(menubor+1,menubor,menubor,menubor),
      "MenuItem.font", fontBold,

      "PopupMenu.border", menuBorder,
      "PopupMenu.font", fontBold,

      "ProgressBar.border", noBorder,
      "ProgressBar.font", fontBold,

      "RadioButton.font", fontBold,

      "ScrollBar.border",noBorder,

      "ScrollPane.viewportBorder",noBorder,
      "ScrollPane.border",new EmptyBorder(menubor+1,menubor+1,menubor+1,menubor+1) ,

      "Slider.font", fontBold,

      "SplitPane.border", noBorder,

      "Table.focusCellBackground",bgColor,
      "Table.focusCellHighlightBorder",noBorder,
      "Table.font", font,
      "Table.gridColor", bgColor,

      "TableHeader.background", lightColor,
      "TableHeader.cellBorder", new LineBorder(darkColor,1),
      "TableHeader.font", fontBold,

      //"TextArea.border",borBorder,
      "TextArea.border",noBorder,
      "TextArea.font", font,
      "TextArea.margin", new Insets(0,0,0,0),
      "TextArea.selectionBackground", midColor,
      "TextArea.selectionForeground", textColor,

      "TextField.border",borBorder,
      "TextField.font", font,
      "TextField.margin", new Insets(0,0,0,0),
      "TextField.selectionBackground", midColor,
      "TextField.selectionForeground", textColor,

      "ToggleButton.border",borBorder,
      "ToggleButton.font", fontBold,

      "ToolBar.border", menuBorder,
      "ToolBar.background", midColor,
      "ToolBar.darkShadow", midColor,
      "ToolBar.dockingBackground", midColor,
      "ToolBar.dockingForeground", darkColor,
      "ToolBar.floatingBackground", midColor,
      "ToolBar.floatingForeground", darkColor,
      "ToolBar.font", fontBold,
      "ToolBar.foreground", darkColor,
      "ToolBar.highlight", midColor,
      "ToolBar.light", midColor,
      "ToolBar.shadow", midColor,

      "ToolTip.font", font,

      "Tree.font", font,
      "Tree.hash",darkColor,  // legs
      "Tree.line",darkColor, // horiz lines
      "Tree.selectionBackground", midColor,
      "Tree.selectionBorderColor", darkColor,
      "Tree.selectionForeground", textColor,
      "Tree.textBackground", bgColor,
      "Tree.textForeground", textColor,


    // Java 1.5 Tweaks
      "MenuBar.gradient",Arrays.asList(new Object[]{new Float(0f),new Float(0f),new ColorUIResource(AquanauticTheme.COLOR_BG),new ColorUIResource(AquanauticTheme.COLOR_BG),new ColorUIResource(AquanauticTheme.COLOR_BG)}),

      "SplitPane.dividerFocusColor",bgColor,
      "SplitPane.background",bgColor,
      "SplitPane.centerOneTouchButtons",Boolean.TRUE
      };

    table.putDefaults(newValues);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean getSupportsWindowDecorations() {
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isNativeLookAndFeel() {
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isSupportedLookAndFeel() {
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setCurrentTheme(MetalTheme theme) {
    }


  }