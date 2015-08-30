package com.extentia.cartentia.models;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class PlaceeOrderRequest {

    private String userID;
    private String statusID;
    private String groupID;
    private String loclatlong;
    private ArrayList<PlaceProduct> products;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getLoclatlong() {
        return loclatlong;
    }

    public void setLoclatlong(String loclatlong) {
        this.loclatlong = loclatlong;
    }

    public ArrayList<PlaceProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<PlaceProduct> products) {
        this.products = products;
    }


}
