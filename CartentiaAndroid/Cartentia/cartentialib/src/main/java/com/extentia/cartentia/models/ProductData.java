package com.extentia.cartentia.models;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class ProductData {
    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }

    private ArrayList<Product> data;
}
