package com.example.universebuilder.markeditor.components;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.universebuilder.R;
import com.example.universebuilder.markeditor.models.ComponentTag;
import com.example.universebuilder.markeditor.models.ImageComponentModel;
import com.example.universebuilder.markeditor.utilities.ImageHelper;

import api.ImageUploader;


public class ImageComponentItemVer extends FrameLayout {
    ImageView imageView;
    TextView captionEt;
    ProgressBar imageUploadProgressBar;
    ImageView retryUpload;
    TextView statusMessage;
    ImageView removeImageButton;



    private String downloadUrl;
    private String caption;
    private Context mContext;

    public ImageComponentItemVer(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        downloadUrl = null;
        View view = LayoutInflater.from(context).inflate(R.layout.image_component_item_ver, this);
        imageView = view.findViewById(R.id.image_ver);
        captionEt = view.findViewById(R.id.caption_ver);
    }



    public void setImageInformation(String filePath, String caption) {
        setCaption(caption);
        loadImage(filePath);
    }

    private int getSelfIndex() {
        ComponentTag tag = (ComponentTag) getTag();
        return tag.getComponentIndex();
    }

    private void loadImage(String filePath) {
        ImageHelper.load(mContext, imageView, filePath);
    }



    private void hideExtraInfroWithDelay() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statusMessage.setVisibility(GONE);
                removeImageButton.setVisibility(GONE);
                retryUpload.setVisibility(GONE);
                imageUploadProgressBar.setVisibility(GONE);
            }
        }, 2000);
    }





    public ImageComponentItemVer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        ComponentTag tag = (ComponentTag) getTag();
        ((ImageComponentModel) tag.getComponent()).setUrl(downloadUrl);
    }

    public void setFocus() {
        imageView.setEnabled(true);
        captionEt.requestFocus();
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        ComponentTag tag = (ComponentTag) getTag();
        ((ImageComponentModel) tag.getComponent()).setCaption(caption);
    }


}
