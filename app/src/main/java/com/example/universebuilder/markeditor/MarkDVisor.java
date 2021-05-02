package com.example.universebuilder.markeditor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.example.universebuilder.markeditor.components.HorizontalDividerComponent;
import com.example.universebuilder.markeditor.components.HorizontalDividerComponentItem;
import com.example.universebuilder.markeditor.components.ImageComponent;
import com.example.universebuilder.markeditor.components.ImageComponentItem;
import com.example.universebuilder.markeditor.components.ImageComponentItemVer;
import com.example.universebuilder.markeditor.components.ImageComponentVer;
import com.example.universebuilder.markeditor.components.TextComponentItem;
import com.example.universebuilder.markeditor.components.TextComponentItemVer;
import com.example.universebuilder.markeditor.components.TextComponentVer;
import com.example.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.example.universebuilder.markeditor.models.ComponentTag;
import com.example.universebuilder.markeditor.models.DraftModel;
import com.example.universebuilder.markeditor.models.ImageComponentModel;
import com.example.universebuilder.markeditor.models.TextComponentModel;
import com.example.universebuilder.markeditor.utilities.ComponentMetadataHelper;
import com.example.universebuilder.markeditor.utilities.DraftManager;
import com.example.universebuilder.markeditor.utilities.MarkDownConverter;
import com.example.universebuilder.markeditor.utilities.RenderingUtilsVer;

import java.util.ArrayList;

import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.BLOCKQUOTE;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.NORMAL;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_OL;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_PLAIN;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_UL;

public class MarkDVisor extends MarkDCore{
    private static String serverUrl;
    private View _activeView;
    private Context mContext;
    private DraftManager draftManager;
    private TextComponentVer __textComponentVer;
    private ImageComponentVer __imageComponentVer;
    private HorizontalDividerComponent __horizontalComponent;
    private int currentInputMode;
    private RenderingUtilsVer renderingUtilsVer;
    private MarkDEditor.EditorFocusReporter editorFocusReporter;
    private String startHintText;
    private int defaultHeadingType = NORMAL;
    private boolean isFreshEditor;
    private DraftModel oldDraft;

    public MarkDVisor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        draftManager = new DraftManager();
        bulletGroupModels = new ArrayList<>();
        currentInputMode = MODE_PLAIN;
        __textComponentVer = new TextComponentVer(context);
        __imageComponentVer = new ImageComponentVer(context);
        __horizontalComponent = new HorizontalDividerComponent(context);
    }



    /**
     * Inserts single text component
     */
    private void startFreshEditor() {
        //starts basic editor with single text component.
        this.isFreshEditor = true;
        addTextComponent(0);
        setHeading(defaultHeadingType);
    }

    /**
     * adds new TextComponent.
     *
     * @param insertIndex at which addition of new textcomponent take place.
     */
    private void addTextComponent(int insertIndex) {
        TextComponentItemVer textComponentItemVer = __textComponentVer.newTextComponent(currentInputMode);
        //prepare tag
        TextComponentModel textComponentModel = new TextComponentModel();
        if (insertIndex == 0) {
            if (startHintText != null && isFreshEditor) {
                textComponentItemVer.setHintText(startHintText);
            }
        }
        ComponentTag componentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        componentTag.setComponent(textComponentModel);
        textComponentItemVer.setTag(componentTag);
        addView(textComponentItemVer, insertIndex);
        __textComponentVer.updateComponent(textComponentItemVer);
        setFocus(textComponentItemVer);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();
    }

    /**
     * sets heading to text component
     *
     * @param heading number to be set
     */
    public void setHeading(int heading) {
        currentInputMode = MODE_PLAIN;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setHeadingStyle(heading);
            __textComponentVer.updateComponent(_activeView);
        }
        refreshViewOrder();
    }

    /**
     * @param view to be focused on.
     */
    private void setFocus(View view) {
        _activeView = view;
        if (_activeView instanceof TextComponentItem) {
            currentInputMode = ((TextComponentItem) _activeView).getMode();
            ((TextComponentItem) view).getInputBox().requestFocus();
            reportStylesOfFocusedView((TextComponentItem) view);
        }
    }

    /**
     * re-compute the indexes of view after a view is inserted/deleted.
     *
     * @param startIndex index after which re-computation will be done.
     */
    private void reComputeTagsAfter(int startIndex) {
        View _child;
        for (int i = startIndex; i < getChildCount(); i++) {
            _child = getChildAt(i);
            ComponentTag componentTag = (ComponentTag) _child.getTag();
            componentTag.setComponentIndex(i);
            _child.setTag(componentTag);
        }
    }

    /**
     * method to send callback for focussed view back to subscriber(if any).
     *
     * @param view newly focus view.
     */
    private void reportStylesOfFocusedView(TextComponentItem view) {
        if (editorFocusReporter != null) {
            editorFocusReporter.onFocusedViewHas(view.getMode(), view.getTextHeadingStyle());
        }
    }

    public void loadDraft(DraftModel draft) {
        oldDraft = draft;
        ArrayList<DraftDataItemModel> contents = draft.getItems();
        if (contents != null) {
            if (contents.size() > 0) {
                renderingUtilsVer = new RenderingUtilsVer();
                renderingUtilsVer.setEditor(this);
                renderingUtilsVer.render(contents);
            } else {
                startFreshEditor();
            }
        } else {
            startFreshEditor();
        }
    }

    /**
     * Sets current mode for insert.
     *
     * @param currentInputMode mode of insert.
     */
    public void setCurrentInputMode(int currentInputMode) {
        this.currentInputMode = currentInputMode;
    }

    /**
     * adds new TextComponent with pre-filled text.
     *
     * @param insertIndex at which addition of new textcomponent take place.
     */
    public void addTextComponent(int insertIndex, String content) {
        TextComponentItemVer textComponentItemVer = __textComponentVer.newTextComponent(currentInputMode);
        //prepare tag
        TextComponentModel textComponentModel = new TextComponentModel();
        ComponentTag componentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        componentTag.setComponent(textComponentModel);
        textComponentItemVer.setTag(componentTag);
        textComponentItemVer.setText(content);
        addView(textComponentItemVer, insertIndex);
        __textComponentVer.updateComponent(textComponentItemVer);
        setFocus(textComponentItemVer);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();
    }


    /**
     * This method searches whithin view group for a TextComponent which was
     * inserted prior to startIndex.
     *
     * @param starIndex index from which search starts.
     * @return index of LatestTextComponent before startIndex.
     */
    private int getLatestTextComponentIndexBefore(int starIndex) {
        View view = null;
        for (int i = starIndex; i >= 0; i--) {
            view = getChildAt(i);
            if (view instanceof TextComponentItem)
                return i;
        }
        return 0;
    }

    /**
     * overloaded method for focusing view, it puts the cursor at specified position.
     *
     * @param view to be focused on.
     */
    private void setFocus(View view, int cursorPos) {
        _activeView = view;
        view.requestFocus();
        if (view instanceof TextComponentItem) {
            InputMethodManager mgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            //move cursor
            ((TextComponentItem) view).getInputBox().setSelection(cursorPos);
            reportStylesOfFocusedView((TextComponentItem) view);
        }
    }

    private void setActiveView(View view) {
        _activeView = view;
    }

    /**
     * adds link.
     *
     * @param text link text
     * @param url  linking url.
     */
    public void addLink(String text, String url) {
        if (_activeView instanceof TextComponentItem) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(" <a href=\"")
                    .append(url)
                    .append("\">")
                    .append(text)
                    .append("</a> ");
            ((TextComponentItem) _activeView).getInputBox().append(stringBuilder.toString());
        }
    }

    /**
     * changes the current text into blockquote.
     */
    public void changeToBlockquote() {
        currentInputMode = MODE_PLAIN;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setHeadingStyle(BLOCKQUOTE);
            __textComponentVer.updateComponent(_activeView);
        }
        refreshViewOrder();
    }

    /**
     * change the current insert mode to Ordered List Mode.
     * Increasing numbers are used to denote each item.
     */
    public void changeToOLMode() {
        currentInputMode = MODE_OL;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setHeadingStyle(NORMAL);
            __textComponentVer.updateComponent(_activeView);
        }
        refreshViewOrder();
    }

    /**
     * change the current insert mode to UnOrdered List Mode.
     * Circular filled bullets are used to denote each item.
     */
    public void changeToULMode() {
        currentInputMode = MODE_UL;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setHeadingStyle(NORMAL);
            __textComponentVer.updateComponent(_activeView);
        }
        refreshViewOrder();
    }

    /**
     * This method gets the suitable insert index using
     * `checkInvalidateAndCalculateInsertIndex()` method.
     * Prepares the ImageComponent and inserts it.
     * Since the user might need to type further, it inserts new TextComponent below
     * it.
     *
     * @param filePath uri of image to be inserted.
     */
    public void insertImage(String filePath) {
        int insertIndex = checkInvalidateAndCalculateInsertIndex();
        ImageComponentItemVer imageComponentItemVer = __imageComponentVer.getNewImageComponentItemVer();
        //prepare tag
        ImageComponentModel imageComponentModel = new ImageComponentModel();
        ComponentTag imageComponentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        imageComponentTag.setComponent(imageComponentModel);
        imageComponentItemVer.setTag(imageComponentTag);
        imageComponentItemVer.setImageInformation(filePath, "");
        addView(imageComponentItemVer, insertIndex);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();
        //add another text component below image
        insertIndex++;
        currentInputMode = MODE_PLAIN;
        addTextComponent(insertIndex);
    }

    /**
     * This method checks the current active/focussed view.
     * If there is some text in it, then next insertion will take place below this
     * view.
     * Else the current focussed view will be removed and new view will inserted
     * at its position.
     *
     * @return index of next insert.
     */
    private int checkInvalidateAndCalculateInsertIndex() {
        if (_activeView == null)
            return 0;
        ComponentTag tag = (ComponentTag) _activeView.getTag();
        int activeIndex = tag.getComponentIndex();
        View view = getChildAt(activeIndex);
        //check for TextComponentItem
        if (view instanceof TextComponentItem) {
            //if active text component has some texts.
            if (((TextComponentItem) view).getInputBox().getText().length() > 0) {
                //insert below it
                return activeIndex + 1;
            } else {
                //remove current view
                removeViewAt(activeIndex);
                reComputeTagsAfter(activeIndex);
                refreshViewOrder();
                //insert at the current position.
                return activeIndex;
            }
        }
        return activeIndex + 1;
    }

    /**
     * This method gets the suitable insert index using
     * `checkInvalidateAndCalculateInsertIndex()` method.
     * Prepares the ImageComponent and inserts it.
     * loads already uploaded image and sets caption
     *
     * @param filePath uri of image to be inserted.
     */
    public void insertImage(int insertIndex, String filePath, boolean uploaded, String caption) {
        ImageComponentItemVer imageComponentItem = __imageComponentVer.getNewImageComponentItemVer();
        //prepare tag
        ImageComponentModel imageComponentModel = new ImageComponentModel();
        ComponentTag imageComponentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        imageComponentTag.setComponent(imageComponentModel);
        imageComponentItem.setTag(imageComponentTag);
        imageComponentItem.setImageInformation(filePath, caption);
        addView(imageComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
    }

    /**
     * @return index next to focussed view.
     */
    public int getNextIndex() {
        ComponentTag tag = (ComponentTag) _activeView.getTag();
        return tag.getComponentIndex() + 1;
    }

    /**
     * Inserts new horizontal ruler.
     * Adds new text components based on passed parameter.
     */
    public void insertHorizontalDivider(boolean insertNewTextComponentAfterThis) {
        int insertIndex = getNextIndex();
        HorizontalDividerComponentItem horizontalDividerComponentItem = __horizontalComponent.getNewHorizontalComponentItem();
        ComponentTag _hrTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        horizontalDividerComponentItem.setTag(_hrTag);
        addView(horizontalDividerComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
        //add another text component below image
        if (insertNewTextComponentAfterThis) {
            insertIndex++;
            currentInputMode = MODE_PLAIN;
            addTextComponent(insertIndex);
        } else {
            setFocus(horizontalDividerComponentItem);
        }
        refreshViewOrder();
    }
}
