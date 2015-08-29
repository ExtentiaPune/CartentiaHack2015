package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;

/**
 * Created by Extentia on 8/29/2015.
 */
public class SplashFragment extends Fragment {

    private View rootView;
    private static int SPLASH_TIME_OUT = 2000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        initScreen();
        return rootView;
    }

    private void initScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    navigateToDashboard();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void navigateToDashboard() {

    }

}
