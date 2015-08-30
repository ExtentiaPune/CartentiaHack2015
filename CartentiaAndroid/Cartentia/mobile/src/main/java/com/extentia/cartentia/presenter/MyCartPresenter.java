package com.extentia.cartentia.presenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.MyCartData;
import com.extentia.cartentia.models.PlaceeOrderRequest;
import com.extentia.cartentia.presenters.BasePresenter;
import com.extentia.cartentia.view.interfaces.MyCartView;
import com.google.gson.Gson;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class MyCartPresenter implements BasePresenter {

    private MyCartView myCartView;


    public MyCartPresenter(MyCartView myCartView) {
        this.myCartView = myCartView;
    }

    public void fetchMyCart() {
        String url = String.format(Constants.Url.GET_MY_CART, "55e1a3cfc3d7a2d01b7d44ca");
        Request request = new JsonRequestHandler(Request.Method.GET, url, null, MyCartData.class, new Response.Listener<MyCartData>() {

            @Override
            public void onResponse(MyCartData response) {
                if (myCartView != null)
                    myCartView.displayCart(response.getData());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myCartView != null)
                    myCartView.displayCartError();
            }
        });
        VolleyManager.getInstance().addRequestToQueue(request, "MyCart");

    }


    @Override
    public void onDestroy() {
        myCartView = null;
    }

    public void placeOrder(PlaceeOrderRequest placeeOrderRequest) {
        String url = Constants.Url.PLACE_ORDER;
        String jsonRequest = new Gson().toJson(placeeOrderRequest);
        Request request = new JsonRequestHandler(Request.Method.POST, url, jsonRequest, PlaceeOrderRequest.class, new Response.Listener<PlaceeOrderRequest>() {
            @Override
            public void onResponse(PlaceeOrderRequest response) {
                if (myCartView != null)
                    myCartView.displayPlaceOrderError();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myCartView != null)
                    myCartView.displayCartError();

            }
        });
        VolleyManager.getInstance().addRequestToQueue(request, "PlaceOrder");
    }


}
