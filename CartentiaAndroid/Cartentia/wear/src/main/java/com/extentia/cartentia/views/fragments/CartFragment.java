package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.wearable.view.GridViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;
import com.extentia.cartentia.adapters.ProductsPagerAdapter;
import com.extentia.cartentia.models.MyCartResponse;
import com.extentia.cartentia.presenters.CartPresenter;
import com.extentia.cartentia.presenters.OrderHistoryPresenter;
import com.extentia.cartentia.views.activities.DashboardWearActivity;
import com.extentia.cartentia.views.interfaces.MyCartView;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/29/2015.
 */
public class CartFragment extends Fragment implements MyCartView {

    private View rootView;
    private static final String PARAM_ORDER_ID = "OrderId";
    private static final String PARAM_IS_FROM_ORDERS = "IsFromOrders";
    private CartPresenter cartPresenter;
    private GridViewPager productPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initPresenter();
        initScreen();
        return rootView;
    }

    private void initPresenter() {
        cartPresenter = new CartPresenter(this);
    }

    private void initScreen() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getBoolean(PARAM_IS_FROM_ORDERS)) {
            rootView.findViewById(R.id.placeOrderTextView).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.placeOrderTextView).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.placeOrderTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DashboardWearActivity) getActivity()).moveToScreen(new ConfirmationFragment());
                }
            });
        }
        productPager = (GridViewPager) rootView.findViewById(R.id.productPager);
        productPager.setAdapter(new ProductsPagerAdapter(getActivity(), getActivity().getFragmentManager()));
        cartPresenter.fetchMyCart();
    }

    @Override
    public void displayCart(ArrayList<MyCartResponse> cartRespons) {
        productPager = (GridViewPager) rootView.findViewById(R.id.productPager);
        productPager.setAdapter(new ProductsPagerAdapter(getActivity(), getActivity().getFragmentManager()));
    }

    @Override
    public void displayCartError() {

    }
}
