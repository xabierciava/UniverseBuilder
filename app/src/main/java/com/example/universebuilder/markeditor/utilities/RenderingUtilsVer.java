package com.example.universebuilder.markeditor.utilities;


import com.example.universebuilder.markeditor.MarkDVisor;
import com.example.universebuilder.markeditor.datatype.DraftDataItemModel;
import com.example.universebuilder.markeditor.models.DraftModel;

import java.util.ArrayList;

import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.BLOCKQUOTE;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H1;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H2;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H3;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H4;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.H5;
import static com.example.universebuilder.markeditor.Styles.TextComponentStyle.NORMAL;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_OL;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_PLAIN;
import static com.example.universebuilder.markeditor.components.TextComponentItem.MODE_UL;

public class RenderingUtilsVer {
    private MarkDVisor markDVisor;

    public void setEditor(MarkDVisor markDVisor) {
        this.markDVisor = markDVisor;
    }

    public void render(ArrayList<DraftDataItemModel> contents) {
        //visit each item type
        for (int i = 0; i < contents.size(); i++) {
            DraftDataItemModel item = contents.get(i);
            //identify item of data
            if (item.getItemType() == DraftModel.ITEM_TYPE_TEXT) {
                //identify mode of text item
                switch (item.getMode()) {
                    case MODE_PLAIN:
                        //includes NORMAL, H1-H5, Blockquote
                        renderPlainData(item);
                        break;
                    case MODE_OL:
                        //renders orderedList
                        renderOrderedList(item);
                        break;
                    case MODE_UL:
                        //renders unorderedList
                        renderUnOrderedList(item);
                        break;
                    default:
                        //default goes to normal text
                        renderPlainData(item);
                }
            } else if (item.getItemType() == DraftModel.ITEM_TYPE_HR) {
                renderHR();
            } else if (item.getItemType() == DraftModel.ITEM_TYPE_IMAGE) {
                renderImage(item);
            }
        }
    }

    /**
     * Sets mode to plain and insert a a text component.
     *
     * @param item model of text data item
     */
    private void renderPlainData(DraftDataItemModel item) {
        markDVisor.setCurrentInputMode(MODE_PLAIN);
        switch (item.getStyle()) {
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case BLOCKQUOTE:
                markDVisor.addTextComponent(getInsertIndex(), item.getContent());
                markDVisor.setHeading(item.getStyle());
                break;
            default:
                markDVisor.addTextComponent(getInsertIndex(), item.getContent());
                markDVisor.setHeading(NORMAL);
        }
    }

    /**
     * Sets mode to ordered-list and insert a a text component.
     *
     * @param item model of text data item.
     */
    private void renderOrderedList(DraftDataItemModel item) {
        markDVisor.setCurrentInputMode(MODE_OL);
        markDVisor.addTextComponent(getInsertIndex(), item.getContent());
    }

    /**
     * Sets mode to unordered-list and insert a a text component.
     *
     * @param item model of text data item.
     */
    private void renderUnOrderedList(DraftDataItemModel item) {
        markDVisor.setCurrentInputMode(MODE_UL);
        markDVisor.addTextComponent(getInsertIndex(), item.getContent());
    }

    /**
     * Adds Horizontal line.
     */
    private void renderHR() {
        markDVisor.insertHorizontalDivider(false);
    }

    /**
     * @param item model of image item.
     *             Inserts image.
     *             Sets caption
     */
    private void renderImage(DraftDataItemModel item) {
        markDVisor.insertImage(getInsertIndex(),item.getDownloadUrl(), true, item.getCaption());
    }

    /**
     * Since childs are going to be arranged in linear fashion, child count can act as insert index.
     *
     * @return insert index.
     */
    private int getInsertIndex() {
        int index = markDVisor.getChildCount();
        return index;
    }
}
