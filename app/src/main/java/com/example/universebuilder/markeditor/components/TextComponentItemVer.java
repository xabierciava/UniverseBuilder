package com.example.universebuilder.markeditor.components;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.universebuilder.R;
import com.example.universebuilder.markeditor.models.ComponentTag;
import com.example.universebuilder.markeditor.models.TextComponentModel;

public class TextComponentItemVer extends FrameLayout {
    public static final int MODE_PLAIN = 0;
    public static final int MODE_UL = 1;
    public static final int MODE_OL = 2;

    public static final String UL_BULLET = "\u2022";
    TextView indicatorTv;
    TextView textView;
    private int mEditorMode;
    private String indicatorText;

    public TextComponentItemVer(@NonNull Context context) {
        super(context);
        init(context, MODE_PLAIN);
    }

    private void init(Context context, int mode) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_component_item_ver, this);
        indicatorTv = view.findViewById(R.id.indicator_ver);
        textView = view.findViewById(R.id.text_ver);
        this.mEditorMode = mode;
        setMode(mode);
    }

    public TextComponentItemVer(@NonNull Context context, int mode) {
        super(context);
        init(context, mode);
    }

    public TextComponentItemVer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, MODE_PLAIN);
    }

    public void setHintText(String hint) {
        textView.setHint(hint);
    }

    public void setText(String content) {
        textView.setText(content);
    }

    public void setText(SpannableString content) {
        textView.setText(content);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    public TextView getInputBox() {
        return textView;
    }

    public int getMode() {
        return mEditorMode;
    }

    public void setMode(int mode) {
        this.mEditorMode = mode;
        if (mode == MODE_PLAIN) {
            indicatorTv.setVisibility(GONE);
            textView.setBackgroundResource(R.drawable.text_input_bg);
        } else if (mode == MODE_UL) {
            indicatorTv.setText(UL_BULLET);
            indicatorTv.setVisibility(VISIBLE);
            textView.setBackgroundResource(R.drawable.text_input_bg);
        } else if (mode == MODE_OL) {
            indicatorTv.setVisibility(VISIBLE);
            textView.setBackgroundResource(R.drawable.text_input_bg);
        }
    }

    public int getTextHeadingStyle() {
        ComponentTag componentTag = (ComponentTag) getTag();
        //check heading
        return ((TextComponentModel) componentTag.getComponent()).getHeadingStyle();
    }

    public void setIndicator(String bullet) {
        indicatorTv.setText(bullet);
        this.indicatorText = bullet;
    }

    public String getIndicatorText() {
        return indicatorText;
    }

    public String getContent() {
        return textView.getText().toString();
    }
}
