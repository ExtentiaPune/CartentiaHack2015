package com.extentia.cartentia.models;

/**
 * Created by Extentia on 8/29/2015.
 */
public class ProductPage {
    public String mTitle;
    public String mText;
    public int mIconId;
    public int mBackgroundId;

    public ProductPage(String title, String text, int iconId, int backgroundId) {
        this.mTitle = title;
        this.mText = text;
        this.mIconId = iconId;
        this.mBackgroundId = backgroundId;
    }


}
