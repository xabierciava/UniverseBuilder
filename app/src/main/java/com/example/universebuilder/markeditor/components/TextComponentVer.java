package com.example.universebuilder.markeditor.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.universebuilder.R;
import com.example.universebuilder.markeditor.models.ComponentTag;
import com.example.universebuilder.markeditor.models.TextComponentModel;
import com.example.universebuilder.markeditor.utilities.FontSize;

import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.BLOCKQUOTE;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H1;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H5;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.NORMAL;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_PLAIN;

public class TextComponentVer {
    private final Context mContext;
    private Resources r;
    private boolean spaceExist;

    public TextComponentVer(Context context) {
        this.mContext = context;
        r = mContext.getResources();
    }

    /**
     * Method to create new instance according to mode provided.
     * Mode can be [PLAIN, UL, OL]
     * @param mode mode of new TextComponent.
     * @return new instance of TextComponent.
     */
    public TextComponentItemVer newTextComponent(final int mode) {
        final TextComponentItemVer customInput = new TextComponentItemVer(mContext, mode);
        final TextView et = customInput.getInputBox();

        return customInput;
    }

    private boolean isSpaceCharacter(char ch) {
        return ch == ' ';
    }

    /**
     * updates view with latest style info.
     * @param view to be updated.
     */
    public void updateComponent(View view) {
        ComponentTag componentTag = (ComponentTag) view.getTag();
        //get heading
        int style = ((TextComponentModel) componentTag.getComponent()).getHeadingStyle();
        TextComponentItemVer textComponentItemVer = (TextComponentItemVer) view;
        textComponentItemVer.getInputBox().setTextSize(FontSize.getFontSize(style));
        //get mode
        int mode = textComponentItemVer.getMode();

        if (style >= H1 && style <= H5) {
            ((TextComponentItemVer) view).getInputBox().setTypeface(null, Typeface.BOLD);
            (((TextComponentItemVer) view).getInputBox()).setBackgroundResource(R.drawable.text_input_bg);
            ((TextComponentItemVer) view).getInputBox().setPadding(
                    dpToPx(16),//left
                    dpToPx(8),//top
                    dpToPx(16),//right
                    dpToPx(8)//bottom
            );
            ((TextComponentItemVer) view).getInputBox().setLineSpacing(2f,1.1f);
        }

        if (style == NORMAL) {
            ((TextComponentItemVer) view).getInputBox().setTypeface(null, Typeface.NORMAL);
            (((TextComponentItemVer) view).getInputBox()).setBackgroundResource(R.drawable.text_input_bg);
            if (mode == MODE_PLAIN) {
                ((TextComponentItemVer) view).getInputBox().setPadding(
                        dpToPx(16),//left
                        dpToPx(4),//top
                        dpToPx(16),//right
                        dpToPx(4)//bottom
                );
            } else {
                ((TextComponentItemVer) view).getInputBox().setPadding(
                        dpToPx(4),//left
                        dpToPx(4),//top
                        dpToPx(16),//right
                        dpToPx(4)//bottom
                );
            }
            ((TextComponentItemVer) view).getInputBox().setLineSpacing(2f,1.1f);
        }

        if (style == BLOCKQUOTE) {
            ((TextComponentItemVer) view).getInputBox().setTypeface(null, Typeface.ITALIC);
            (((TextComponentItemVer) view).getInputBox()).setBackgroundResource(R.drawable.blockquote_component_bg);
            ((TextComponentItemVer) view).getInputBox().setPadding(
                    dpToPx(16),//left
                    dpToPx(2),//top
                    dpToPx(16),//right
                    dpToPx(2)//bottom
            );
            ((TextComponentItemVer) view).getInputBox().setLineSpacing(2f,1.2f);
        }
    }

    /**
     * Convert dp to px value.
     * @param dp value
     * @return pixel value of given dp.
     */
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, r.getDisplayMetrics());
    }
}
