package com.extentia.cartentia.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.extentia.cartentia.R;
import com.extentia.cartentia.views.fragments.SplashFragment;


public class DashboardWearActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        moveToScreen(new SplashFragment());
    }

    public void moveToScreen(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.screenContainer, fragment).addToBackStack(fragment.getClass().getName())
                .commit();
    }
}