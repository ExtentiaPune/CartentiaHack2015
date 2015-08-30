package com.extentia.cartentia.view.interfaces;

import com.extentia.cartentia.models.Product;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public interface AddProductView {

    void displayProduct(Product product);

    void displayNoProductFoundError();


}
