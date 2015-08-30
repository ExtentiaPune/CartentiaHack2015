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
import com.extentia.cartentia.models.PlaceProduct;
import com.extentia.cartentia.models.PlaceeOrderRequest;
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
    private double totalAmount;
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
        buildGoogleApiClient();
    }

    private void showPlaceOrderConfirmationDialog() {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.place_order_confirmation_title_txt))
                .setMessage(context.getString(R.string.place_order_confirmation_dialog_message_txt))
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
        placeeOrderRequest.setLoclatlong(lastLocation!=null?""+lastLocation.getLatitude()+","+lastLocation.getLongitude():context.getString(R.string.default_latlon_txt));
        placeeOrderRequest.setStatusID(context.getString(R.string.order_status_txt));
        ArrayList<PlaceProduct> products = new ArrayList<>();
        for (MyCartResponse myCartResponse : cartResponses) {
            PlaceProduct product = new PlaceProduct();
            String numberOnly = myCartResponse.getProductID().getPrice().replaceAll("[^0-9]", "");
            product.setPrice(Integer.valueOf(numberOnly));
            String quantity = myCartResponse.getProductID().getDefaultQty().replaceAll("[^0-9]", "");
            product.setQuantity(Integer.valueOf(quantity));
            product.setProductID(myCartResponse.getProductID().get_id());
            products.add(product);
        }
        placeeOrderRequest.setProducts(products);
        myCartPresenter.placeOrder(placeeOrderRequest);

    }

    private void fetchMyCart() {
        CustomProgressDialog.startProgressDialog(context);
        myCartPresenter.fetchMyCart();
    }

    private ArrayList<MyCartResponse> cartResponses;

    @Override
    public void displayCart(ArrayList<MyCartResponse> cartRespons) {
        CustomProgressDialog.stopProgressDialog(context);
        if (cartRespons != null && !cartRespons.isEmpty()) {
            this.cartResponses = cartRespons;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            myCartRecyclerView.setLayoutManager(layoutManager);
            myCartProductListAdapter = new MyCartProductListAdapter(getActivity(), cartRespons, this);
            myCartRecyclerView.setAdapter(myCartProductListAdapter);

            addProducts(cartRespons);
        } else {
            Toast.makeText(context, R.string.cart_empty_txt, Toast.LENGTH_LONG).show();
            rootView.findViewById(R.id.totalAmt).setVisibility(View.GONE);
        }
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
        ((TextView) rootView.findViewById(R.id.totalAmt)).setText(getString(R.string.ruppee_symbol_txt) + " " + totalAmount);
        rootView.findViewById(R.id.totalAmt).setVisibility(View.VISIBLE);
    }

    @Override
    public void displayCartError() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, getString(R.string.cart_empty_txt), Toast.LENGTH_LONG).show();
        rootView.findViewById(R.id.totalAmt).setVisibility(View.GONE);
    }

    @Override
    public void displayPlaceOrderSuccess() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, R.string.placed_order_confirmation_txt, Toast.LENGTH_LONG).show();
        myCartProductListAdapter.setMyCartRespons(null);
        CartentiaApplication.MY_CART_PRODUCTS.clear();
        //fetchMyCart();
    }

    @Override
    public void displayPlaceOrderError() {
        CustomProgressDialog.stopProgressDialog(getActivity());
        Toast.makeText(context, R.string.server_error_txt, Toast.LENGTH_LONG).show();
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
