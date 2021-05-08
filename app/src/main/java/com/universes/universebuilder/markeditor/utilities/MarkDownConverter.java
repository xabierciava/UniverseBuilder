package com.universes.universebuilder.markeditor.utilities;

import android.view.View;

import com.universes.universebuilder.markeditor.MarkDEditor;
import com.universes.universebuilder.markeditor.components.HorizontalDividerComponentItem;
import com.universes.universebuilder.markeditor.components.ImageComponentItem;
import com.universes.universebuilder.markeditor.components.TextComponentItem;
import com.universes.universebuilder.markeditor.models.ComponentTag;
import com.universes.universebuilder.markeditor.models.TextComponentModel;

import java.util.ArrayList;
import java.util.List;

import static com.universes.universebuilder.markeditor.components.TextComponentItem.MODE_OL;
import static com.universes.universebuilder.markeditor.components.TextComponentItem.MODE_PLAIN;
import static com.universes.universebuilder.markeditor.components.TextComponentItem.MODE_UL;

public class MarkDownConverter {
  private StringBuilder stringBuilder;
  private List<String> images;
  private boolean dataProcessed;

  public MarkDownConverter() {
    stringBuilder = new StringBuilder();
    images = new ArrayList<>();
    dataProcessed = false;
  }

  public MarkDownConverter processData(MarkDEditor markDEditor) {
    int childCount = markDEditor.getChildCount();
    View view;
    int textStyle;
    ComponentTag componentTag;
    for (int i = 0; i < childCount; i++) {
      view = markDEditor.getChildAt(i);
      if (view instanceof TextComponentItem) {
        //check mode
        int mode = ((TextComponentItem) view).getMode();
        if (mode == MODE_PLAIN) {
          //check for styles {H1-H5 Blockquote Normal}
          componentTag = (ComponentTag) view.getTag();
          textStyle = ((TextComponentModel) componentTag.getComponent()).getHeadingStyle();
          stringBuilder.append(MarkDownFormat.getTextFormat(textStyle, ((TextComponentItem) view).getContent()));
        } else if (mode == MODE_UL) {
          stringBuilder.append(MarkDownFormat.getULFormat(((TextComponentItem) view).getContent()));
        } else if (mode == MODE_OL) {
          stringBuilder.append(MarkDownFormat.getOLFormat(
           ((TextComponentItem) view).getIndicatorText(),
           ((TextComponentItem) view).getContent()
          ));
        }
      } else if (view instanceof HorizontalDividerComponentItem) {
        stringBuilder.append(MarkDownFormat.getLineFormat());
      } else if (view instanceof ImageComponentItem) {
        stringBuilder.append(MarkDownFormat.getImageFormat(((ImageComponentItem) view).getDownloadUrl()));
        images.add(((ImageComponentItem) view).getDownloadUrl());
        stringBuilder.append(MarkDownFormat.getCaptionFormat(((ImageComponentItem) view).getCaption()));
      }
    }
    dataProcessed = true;
    return this;
  }

  /**
   * @return flag whether views are processed or not.
   */
  public boolean isDataProcessed() {
    return dataProcessed;
  }

  /**
   * @return markdown format of data.
   */
  public String getMarkDown() {
    return stringBuilder.toString();
  }

  /**
   * @return list of inserted images.
   */
  public List<String> getImages() {
    return images;
  }
}
