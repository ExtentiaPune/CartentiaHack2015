package com.extentia.cartentia;

import android.app.Application;

import com.extentia.cartentia.dataprovider.VolleyManager;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class CartentiaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance().initRequestQueue(getApplicationContext());
    }
}
