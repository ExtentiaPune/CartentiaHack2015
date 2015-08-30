package com.extentia.cartentia.models;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/30/2015.
 */
public class OrderHistoryResponse {

    private ArrayList<OrderHistory> data;

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
