package com.extentia.cartentia.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.extentia.cartentia.R;
import com.extentia.cartentia.models.ProductPage;
import com.extentia.cartentia.models.ProductRow;
import com.extentia.cartentia.views.fragments.ProductFragment;

import java.util.ArrayList;


public class ProductsPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private ArrayList<ProductRow> pages;

    public ProductsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        initPages();
    }

    private void initPages() {
        pages = new ArrayList<ProductRow>();

        ProductRow row1 = new ProductRow();
        row1.addPages(new ProductPage("01", "Product 1", R.drawable.ic_full_sad, android.R.drawable.screen_background_light_transparent));
        row1.addPages(new ProductPage("02", "Product 2", R.drawable.ic_full_sad, R.drawable.bg_cart_screen));
        row1.addPages(new ProductPage("03", "Product 3", R.drawable.ic_full_sad, R.drawable.bg_cart_screen));
        row1.addPages(new ProductPage("04", "Product 4", R.drawable.ic_full_sad, R.drawable.bg_cart_screen));
        pages.add(row1);
    }


    @Override
    public Fragment getFragment(int row, int col) {

        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Col", col);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Drawable getBackgroundForPage(int row, int col) {
        ProductPage page = (pages.get(row)).getPages(col);
        return mContext.getResources().getDrawable(android.R.color.transparent);
    }

    @Override
    public int getRowCount() {
        return pages.size();
    }

    @Override
    public int getColumnCount(int row) {
        return pages.get(row).size();
    }
}