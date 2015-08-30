package com.extentia.cartentia.models;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class PlaceProduct {

    private int ID = 1;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String productID;
    private int quantity;
    private int price;
}
