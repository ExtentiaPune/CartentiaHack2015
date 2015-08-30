package com.extentia.cartentia.models;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class AddCartRequest {
    private String userID;
    private String groupID;
    private String productID;
    private int ID;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }


}
