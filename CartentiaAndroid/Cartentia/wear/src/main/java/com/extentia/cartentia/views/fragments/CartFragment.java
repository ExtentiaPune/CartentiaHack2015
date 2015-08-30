package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.wearable.view.GridViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;
import com.extentia.cartentia.adapters.ProductsPagerAdapter;

/**
 * Created by Extentia on 8/29/2015.
 */
public class CartFragment extends Fragment {

    private View rootView;
    private static final String PARAM_ORDER_ID = "OrderId";
    private static final String PARAM_IS_FROM_ORDERS = "IsFromOrders";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initScreen();
        return rootView;
    }

    private void initScreen() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getBoolean(PARAM_IS_FROM_ORDERS)) {


        } else {

        }
        final GridViewPager productPager = (GridViewPager) rootView.findViewById(R.id.productPager);
        productPager.setAdapter(new ProductsPagerAdapter(getActivity(), getActivity().getFragmentManager()));
    }

}
