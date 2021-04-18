package com.example.universebuilder.markeditor.utilities;

import com.example.universebuilder.markeditor.models.ComponentTag;

public class ComponentMetadataHelper {
  public static ComponentTag getNewComponentTag(int index) {
    ComponentTag componentTag = new ComponentTag();
    componentTag.setComponentIndex(index);
    return componentTag;
  }
}
