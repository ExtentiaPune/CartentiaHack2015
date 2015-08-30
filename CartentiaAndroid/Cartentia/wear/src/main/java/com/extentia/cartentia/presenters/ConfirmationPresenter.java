package com.extentia.cartentia.presenters;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.OrderHistory;
import com.extentia.cartentia.models.OrderHistoryResponse;
import com.extentia.cartentia.views.interfaces.ConfirmationView;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/30/2015.
 */
public class ConfirmationPresenter implements BasePresenter {

    private ConfirmationView confirmationView;

    public ConfirmationPresenter(ConfirmationView confirmationView) {
        this.confirmationView = confirmationView;
    }

    public void placeOrder() {
        String url = String.format(Constants.Url.GET_ORDER_HISTORY_URL, "55e1a3cfc3d7a2d01b7d44ca", "55e1a3cec3d7a2d01b7d44c9");
        Request request = new JsonRequestHandler(Request.Method.GET, url, null, OrderHistoryResponse.class, new Response.Listener<OrderHistoryResponse>() {
            @Override
            public void onResponse(OrderHistoryResponse response) {
                Log.d("Cartentia", "API : placeOrder " + response);
                if (confirmationView != null)
                    confirmationView.onOrderPlaceSuccuss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Cartentia", "API : placeOrder onErrorResponse " + error);
                if (confirmationView != null)
                    confirmationView.onOrderPlaceError();
            }
        });

        VolleyManager.getInstance().addRequestToQueue(request, "PlaceOrder");

    }


    @Override
    public void onDestroy() {

    }

}
