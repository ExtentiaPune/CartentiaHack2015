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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initScreen();
        return rootView;
    }

    private void initScreen() {
        final GridViewPager productPager = (GridViewPager) rootView.findViewById(R.id.productPager);
        productPager.setAdapter(new ProductsPagerAdapter(getActivity(), getActivity().getFragmentManager()));
    }

}
