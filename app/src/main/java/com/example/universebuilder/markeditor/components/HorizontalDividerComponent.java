package com.example.universebuilder.markeditor.components;

import android.content.Context;

public class HorizontalDividerComponent {
  Context context;

  public HorizontalDividerComponent(Context context) {
    this.context = context;
  }

  public HorizontalDividerComponentItem getNewHorizontalComponentItem() {
    return new HorizontalDividerComponentItem(context);
  }
}
