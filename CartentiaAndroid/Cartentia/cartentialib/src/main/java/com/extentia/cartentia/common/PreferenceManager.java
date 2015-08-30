package com.extentia.cartentia.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class PreferenceManager {
    private static final String CARTENTIA_SHAREDPREFERENCE = "cartentiadata";
    private static final String USER_ID = "userID";
    private static final String GROUP_ID = "groupID";
    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String ROLE = "role";

    private static SharedPreferences sharedpreferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public static void initPreferenceManager(final Context context) {
        PreferenceManager.context = context;
        sharedpreferences = context.getSharedPreferences(CARTENTIA_SHAREDPREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.apply();
    }

    public static void setUserID(String userID) {
        editor.putString(USER_ID, userID);
        editor.apply();
    }

    public static String getUserID() {
        return sharedpreferences.getString(USER_ID, "");
    }

    public static void setUsername(String userName) {
        editor.putString(USERNAME, userName);
        editor.apply();
    }

    public static String getUsername() {
        return sharedpreferences.getString(USERNAME, "");
    }

    public static void setGroupID(String groupID) {
        editor.putString(GROUP_ID, groupID);
        editor.apply();
    }

    public static String getGroupID() {
        return sharedpreferences.getString(GROUP_ID, "");
    }

    public static void setName(String name) {
        editor.putString(NAME, name);
        editor.apply();
    }

    public static String getName() {
        return sharedpreferences.getString(NAME, "");
    }

    public static void setRole(String name) {
        editor.putString(ROLE, name);
        editor.apply();
    }

    public static String getRole() {
        return sharedpreferences.getString(ROLE, "");
    }

}
