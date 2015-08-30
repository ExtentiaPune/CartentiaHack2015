package com.extentia.cartentia;

import android.app.Application;

import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.dataprovider.VolleyManager;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class CartentiaApplication extends Application {

    public static ArrayList<String> MY_CART_PRODUCTS;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance().initRequestQueue(getApplicationContext());
        PreferenceManager.initPreferenceManager(getApplicationContext());
        MY_CART_PRODUCTS = new ArrayList<>();
    }
}
