package com.extentia.cartentia.view.activity;

import android.os.Bundle;


public class DashboardActivity extends BaseActivity {


    private String screenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMyCartFragment();
    }

}
