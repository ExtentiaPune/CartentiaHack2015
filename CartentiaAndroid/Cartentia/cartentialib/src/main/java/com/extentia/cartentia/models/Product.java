package com.extentia.cartentia.models;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class Product {

    private String _id;
    private String productID;
    private String productName;
    private String productImage;
    private String productDescription;
    private String productCategory;
    private String defaultQty;
    private String price;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getDefaultQty() {
        return defaultQty;
    }

    public void setDefaultQty(String defaultQty) {
        this.defaultQty = defaultQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
