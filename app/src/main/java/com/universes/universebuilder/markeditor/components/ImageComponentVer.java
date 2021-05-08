package com.universes.universebuilder.markeditor.components;

import android.content.Context;

public class ImageComponentVer {
    private Context context;

    public ImageComponentVer(Context context) {
        this.context = context;
    }

    public ImageComponentItemVer getNewImageComponentItemVer() {
        return new ImageComponentItemVer(context);
    }
}
