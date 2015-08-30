package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;
import com.extentia.cartentia.presenters.ConfirmationPresenter;
import com.extentia.cartentia.views.interfaces.ConfirmationView;

public class ConfirmationFragment extends Fragment implements ConfirmationView {

    private ConfirmationPresenter confirmationPresenter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_history, container, false);
        initPresenter();
        initScreen();
        return rootView;
    }

    private void initPresenter() {
        confirmationPresenter = new ConfirmationPresenter(this);
    }

    private void initScreen() {
        confirmationPresenter.placeOrder();
    }

    @Override
    public void onOrderPlaceSuccuss() {

    }

    @Override
    public void onOrderPlaceError() {

    }
}
