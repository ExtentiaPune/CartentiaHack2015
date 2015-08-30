package com.extentia.cartentia.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class Role {
    private String _id;
    @SerializedName("Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


}
