package com.universes.universebuilder.markeditor.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.universes.universebuilder.R;

public class HorizontalDividerComponentItem extends FrameLayout {
  public HorizontalDividerComponentItem(@NonNull Context context) {
    super(context);
    init(context);
  }

  public HorizontalDividerComponentItem(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context){
    LayoutInflater.from(context).inflate(R.layout.horizontal_divider_item_view,this);
  }
}
