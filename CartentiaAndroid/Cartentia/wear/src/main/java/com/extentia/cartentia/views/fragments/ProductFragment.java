package com.extentia.cartentia.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.extentia.cartentia.R;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.models.MyCartResponse;

/**
 * Created by Extentia on 8/29/2015.
 */
public class ProductFragment extends Fragment {

    private View rootView;
//    private MyCartResponse myCartResponse;
//
//    public ProductFragment(MyCartResponse myCartResponse) {
//        this.myCartResponse = myCartResponse;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product, container, false);

//        Bundle bundle = getArguments();
//        int col = 1;
//        if (bundle != null) {
//            col = bundle.getInt("Col", 1);
//        }

        MyCartResponse cartResponse = new MyCartResponse();

        TextView productDetailsTextView = (TextView) rootView.findViewById(R.id.productDetailsPrizeTV);
        String details = "Rate: " + getString(R.string.Rs) + cartResponse.getProductID().getPrice()
                + "  |  Qty: " + cartResponse.getProductID().getDefaultQty();
        productDetailsTextView.setText(details);

        TextView priceTextView = (TextView) rootView.findViewById(R.id.productTotalPrizeTV);
        String totalCost = "Total Cost: " + getString(R.string.Rs);

        ImageView productImageView = (ImageView) rootView.findViewById(R.id.productImageView);
        Picasso.with(getActivity()).load(Constants.Url.IMAGE_HOST_URL + cartResponse.getProductID().getProductImage()).into(productImageView);

//        ImageView productIcon = (ImageView) rootView.findViewById(R.id.productIcon);

        return rootView;
    }
}
