package com.universes.universebuilder.markeditor.utilities;

import com.universes.universebuilder.markeditor.models.ComponentTag;

public class ComponentMetadataHelper {
  public static ComponentTag getNewComponentTag(int index) {
    ComponentTag componentTag = new ComponentTag();
    componentTag.setComponentIndex(index);
    return componentTag;
  }
}
