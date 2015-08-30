package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;
import com.extentia.cartentia.views.activities.DashboardWearActivity;

/**
 * Created by Extentia on 8/29/2015.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initScreen();
        return rootView;
    }

    private void initScreen() {
        rootView.findViewById(R.id.cartTextView).setOnClickListener(this);
        rootView.findViewById(R.id.orderHistoryTextView).setOnClickListener(this);
        rootView.findViewById(R.id.scanProductTextView).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cartTextView:
                navigateToCart();
                break;

            case R.id.orderHistoryTextView:
                navigateToOrderHistory();
                break;

            case R.id.scanProductTextView:
                navigateToCart();
                break;

        }
    }

    private void navigateToCart() {
        ((DashboardWearActivity) getActivity()).moveToScreen(new CartFragment());
    }

    private void navigateToOrderHistory() {
    }

}
