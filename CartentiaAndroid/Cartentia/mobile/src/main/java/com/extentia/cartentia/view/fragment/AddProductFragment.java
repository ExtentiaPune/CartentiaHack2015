package com.extentia.cartentia.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.extentia.cartentia.R;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.common.CustomProgressDialog;
import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.models.Product;
import com.extentia.cartentia.presenter.AddProductPresenter;
import com.extentia.cartentia.view.activity.BaseActivity;
import com.extentia.cartentia.view.interfaces.AddProductView;
import com.extentia.cartentia.widgets.HorizontalListView;
import com.squareup.picasso.Picasso;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class AddProductFragment extends Fragment implements AddProductView {

    private View rootView;
    private Button viewcartButton;
    private TextView addCartTextView;
    private ImageView productImage;
    private HorizontalListView suggestionProductList;
    private AddProductPresenter addProductPresenter;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public static AddProductFragment getInstance(String productId) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        AddProductFragment addProductFragment = new AddProductFragment();
        addProductFragment.setArguments(bundle);
        return addProductFragment;
    }

    private void initView() {
        ((BaseActivity)getActivity()).setTitle("Added to Cart");
        addProductPresenter = new AddProductPresenter(this);
        context = getActivity();
        viewcartButton = (Button) rootView.findViewById(R.id.viewcartBtn);
        viewcartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).loadMyCartFragment();


            }
        });
        addCartTextView = (TextView) rootView.findViewById(R.id.addedCartTextView);
        productImage = (ImageView) rootView.findViewById(R.id.productImage);
        suggestionProductList = (HorizontalListView) rootView.findViewById(R.id.suggestionProductListView);
        CustomProgressDialog.startProgressDialog(getActivity());
        addProductPresenter.fetchProduct(getArguments().getString("productId"));
    }

    @Override
    public void displayProduct(Product product) {
        if (product != null) {
            Picasso.with(getActivity()).load(Constants.Url.IMAGE_HOST_URL + product.getProductImage().replace("/images", "images/mobile")).into(productImage);
            SpannableString spannablecontent = new SpannableString(product.getProductName() + " "+getString(R.string.addproduct_txt));
            spannablecontent.setSpan(new StyleSpan(Typeface.BOLD),
                    0, product.getProductName().length(), 0);
            addCartTextView.setText(spannablecontent);
        }
        CustomProgressDialog.stopProgressDialog(context);


    }

    @Override
    public void displayNoProductFoundError() {
        CustomProgressDialog.stopProgressDialog(context);
        Toast.makeText(context, "Product not found! Please try later.", Toast.LENGTH_LONG).show();
    }
}
