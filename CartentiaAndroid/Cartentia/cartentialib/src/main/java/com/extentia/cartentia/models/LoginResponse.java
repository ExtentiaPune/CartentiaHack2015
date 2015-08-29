package com.extentia.cartentia.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class LoginResponse {

    private String _id;
    @SerializedName("ID")
    private int userId;
    private String name;
    private String username;
    private String password;
    private String role;
    private String groupID;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }


}
