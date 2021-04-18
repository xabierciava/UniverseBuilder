package com.example.universebuilder.markeditor.models;

import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.NORMAL;

public class TextComponentModel extends BaseComponentModel {
  private int headingStyle = NORMAL;

  public int getHeadingStyle() {
    return headingStyle;
  }

  public void setHeadingStyle(int headingStyle) {
    this.headingStyle = headingStyle;
  }

  @Override
  public String toString() {
    return "TextComponentModel{" +
     "headingStyle=" + headingStyle +
     '}';
  }
}
