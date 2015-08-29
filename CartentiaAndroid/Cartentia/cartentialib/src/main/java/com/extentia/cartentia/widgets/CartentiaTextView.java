package com.extentia.cartentia.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Extentia on 8/29/2015.
 */
public class CartentiaTextView extends TextView {

    private static final String FONT_NAME = "roboto.ttf";

    public CartentiaTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
        this.setTypeface(face);
    }

    public CartentiaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
        this.setTypeface(face);
    }

    public CartentiaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
