package com.extentia.cartentia.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.extentia.cartentia.CartentiaApplication;
import com.extentia.cartentia.R;
import com.extentia.cartentia.adapter.MyCartProductListAdapter;
import com.extentia.cartentia.common.CustomProgressDialog;
import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.models.MyCartResponse;
import com.extentia.cartentia.presenter.MyCartPresenter;
import com.extentia.cartentia.view.activity.BaseActivity;
import com.extentia.cartentia.view.interfaces.MyCartView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class MyCartFragment extends Fragment implements MyCartView {

    private View rootView;
    private RecyclerView myCartRecyclerView;
    private MyCartPresenter myCartPresenter;
    private MyCartProductListAdapter myCartProductListAdapter;
    private Context context;
    private Button placeOrderButton;
    private double totalAmount = 0.0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_mycart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        fetchMyCart();
    }


    private void initView() {
        context = getActivity();
        ((BaseActivity) getActivity()).setTitle(PreferenceManager.getName() + "'s Cart");
        myCartRecyclerView = (RecyclerView) rootView.findViewById(R.id.myCart);
        myCartPresenter = new MyCartPresenter(this);
        if (PreferenceManager.getRole().equalsIgnoreCase("Primary")) {
            placeOrderButton = (Button) rootView.findViewById(R.id.placeOrderBtn);
            placeOrderButton.setVisibility(View.VISIBLE);
            placeOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void fetchMyCart() {
        CustomProgressDialog.startProgressDialog(getActivity());
        myCartPresenter.fetchMyCart();
    }


    @Override
    public void displayCart(ArrayList<MyCartResponse> cartRespons) {
        myCartProductListAdapter = new MyCartProductListAdapter(getActivity(), cartRespons, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myCartRecyclerView.setLayoutManager(layoutManager);
        myCartRecyclerView.setAdapter(myCartProductListAdapter);
        myCartRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.GRAY)
                        .size(1)
                        .margin(getResources().getDimensionPixelSize(R.dimen.list_leftmargin),
                                getResources().getDimensionPixelSize(R.dimen.list_rightmargin))
                        .build());
        CustomProgressDialog.stopProgressDialog(getActivity());
        addProducts(cartRespons);
    }


    public void addProducts(ArrayList<MyCartResponse> cartRespons) {
        totalAmount = 0.0;
        for (MyCartResponse myCartResponse : cartRespons) {
            CartentiaApplication.MY_CART_PRODUCTS.add(myCartResponse.getProductID().get_id());
            String quantity = myCartResponse.getProductID().getDefaultQty().toString().replaceAll("[^0-9]", "");
            String price = myCartResponse.getProductID().getPrice().toString().replaceAll("[^0-9]", "");
            double amount = Double.valueOf(quantity) * Double.valueOf(price);
            totalAmount = totalAmount + amount;
        }
        ((TextView) rootView.findViewById(R.id.totalAmt)).setText("" + totalAmount + " Rs.");
    }

    @Override
    public void displayCartError() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, "Your cart is not available! Please try later.", Toast.LENGTH_LONG).show();
    }

    public static Fragment getInstance() {
        MyCartFragment myCartFragment = new MyCartFragment();
        return myCartFragment;
    }
}
