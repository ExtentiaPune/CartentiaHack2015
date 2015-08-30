package com.extentia.cartentia.views.interfaces;

import com.extentia.cartentia.models.MyCartResponse;

import java.util.ArrayList;


public interface MyCartView {

    void displayCart(ArrayList<MyCartResponse> cartRespons);

    void displayCartError();
}
