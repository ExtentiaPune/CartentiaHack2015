package com.extentia.cartentia.models;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class MyCartResponse {
    private String _id;
    private String userID;
    private Product productID;
    private String groupID;

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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


}
