package com.extentia.cartentia.models;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/29/2015.
 */
public class ProductRow {

    ArrayList<ProductPage> productPages = new ArrayList<ProductPage>();

    public void addPages(ProductPage page) {
        productPages.add(page);
    }

    public ProductPage getPages(int index) {
        return productPages.get(index);
    }

    public int size() {
        return productPages.size();
    }
}

