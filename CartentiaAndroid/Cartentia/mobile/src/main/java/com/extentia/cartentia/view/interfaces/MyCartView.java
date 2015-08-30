package com.extentia.cartentia.view.interfaces;

import com.extentia.cartentia.models.MyCartResponse;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public interface MyCartView {

    void displayCart(ArrayList<MyCartResponse> cartRespons);

    void displayCartError();

    void displayPlaceOrderSuccess();

    void displayPlaceOrderError();

}
