package com.extentia.cartentia.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.extentia.cartentia.models.PlaceeOrderRequest;
import com.extentia.cartentia.models.Product;
import com.extentia.cartentia.presenter.MyCartPresenter;
import com.extentia.cartentia.view.activity.BaseActivity;
import com.extentia.cartentia.view.interfaces.MyCartView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class MyCartFragment extends Fragment implements MyCartView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private View rootView;
    private RecyclerView myCartRecyclerView;
    private MyCartPresenter myCartPresenter;
    private MyCartProductListAdapter myCartProductListAdapter;
    private Context context;
    private Button placeOrderButton;
    private double totalAmount = 0.0;
    private GoogleApiClient mGoogleApiClient;
    private Location lastLocation;


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
        if (PreferenceManager.getRole().equalsIgnoreCase(getString(R.string.user_role_txt))) {
            placeOrderButton = (Button) rootView.findViewById(R.id.placeOrderBtn);
            placeOrderButton.setVisibility(View.VISIBLE);
            placeOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartResponses != null && !cartResponses.isEmpty()) {
                        showPlaceOrderConfirmationDialog();
                    } else {
                        Toast.makeText(context, getString(R.string.empty_cart_txt), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void showPlaceOrderConfirmationDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Place Order?")
                .setMessage("Are you sure you want to place the order?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        placeOrder();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void placeOrder() {
        CustomProgressDialog.startProgressDialog(context);
        PlaceeOrderRequest placeeOrderRequest = new PlaceeOrderRequest();
        placeeOrderRequest.setGroupID(PreferenceManager.getGroupID());
        placeeOrderRequest.setUserID(PreferenceManager.getUserID());
        placeeOrderRequest.setLoclatlong("" + lastLocation.getLatitude() + "," + lastLocation.getLongitude());
        placeeOrderRequest.setStatusID("55e280f3f16422c805aaadb7");
        ArrayList<Product> products = new ArrayList<>();
        for (MyCartResponse myCartResponse : cartResponses) {
            Product product = myCartResponse.getProductID();
            products.add(product);
        }
        placeeOrderRequest.setProducts(products);
        myCartPresenter.placeOrder(placeeOrderRequest);

    }

    private void fetchMyCart() {
        CustomProgressDialog.startProgressDialog(getActivity());
        myCartPresenter.fetchMyCart();
    }

    private ArrayList<MyCartResponse> cartResponses;

    @Override
    public void displayCart(ArrayList<MyCartResponse> cartRespons) {
        this.cartResponses = cartRespons;
        myCartProductListAdapter = new MyCartProductListAdapter(getActivity(), cartRespons, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myCartRecyclerView.setLayoutManager(layoutManager);
        myCartRecyclerView.setAdapter(myCartProductListAdapter);
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
        rootView.findViewById(R.id.totalAmt).setVisibility(View.VISIBLE);
    }

    @Override
    public void displayCartError() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, "Your cart is not available! Please try later.", Toast.LENGTH_LONG).show();
        rootView.findViewById(R.id.totalAmt).setVisibility(View.GONE);
    }

    @Override
    public void displayPlaceOrderSuccess() {

    }

    @Override
    public void displayPlaceOrderError() {

    }

    public static Fragment getInstance() {
        MyCartFragment myCartFragment = new MyCartFragment();
        return myCartFragment;
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myCartPresenter.onDestroy();
    }
}
