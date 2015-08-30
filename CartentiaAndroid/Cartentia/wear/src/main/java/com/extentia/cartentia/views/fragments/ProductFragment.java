package com.extentia.cartentia.views.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;

/**
 * Created by Extentia on 8/29/2015.
 */
public class ProductFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

//        Bundle bundle = getArguments();
//        int col = 1;
//        if (bundle != null) {
//            col = bundle.getInt("Col", 1);
//        }
//
//        TextView productDetails = (TextView) rootView.findViewById(R.id.productDetails);
//        ImageView productIcon = (ImageView) rootView.findViewById(R.id.productIcon);


        return rootView;
    }
}
