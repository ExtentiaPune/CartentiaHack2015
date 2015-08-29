package com.extentia.cartentia.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.extentia.cartentia.R;
import com.extentia.cartentia.adapter.MyCartProductListAdapter;
import com.extentia.cartentia.common.CustomProgressDialog;
import com.extentia.cartentia.models.MyCartResponse;
import com.extentia.cartentia.presenter.MyCartPresenter;
import com.extentia.cartentia.view.interfaces.MyCartView;

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
        myCartRecyclerView = (RecyclerView) rootView.findViewById(R.id.myCart);
        myCartPresenter = new MyCartPresenter(this);
    }

    private void fetchMyCart() {
        CustomProgressDialog.startProgressDialog(getActivity());
        myCartPresenter.fetchMyCart();
    }


    @Override
    public void displayCart(ArrayList<MyCartResponse> cartRespons) {
        myCartProductListAdapter = new MyCartProductListAdapter(getActivity(), cartRespons);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myCartRecyclerView.setLayoutManager(layoutManager);
        myCartRecyclerView.setAdapter(myCartProductListAdapter);
        CustomProgressDialog.stopProgressDialog(getActivity());
    }

    @Override
    public void displayCartError() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, "Your cart is not available! Please try later.", Toast.LENGTH_LONG).show();
    }
}
