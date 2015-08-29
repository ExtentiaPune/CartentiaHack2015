package com.extentia.cartentia.dataprovider;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class VolleyManager {

    private RequestQueue requestQueue;
    public static VolleyManager INSTANCE;

    public static synchronized VolleyManager getInstance() {

        if (INSTANCE != null)
            return INSTANCE;
        return INSTANCE = new VolleyManager();
    }

    public RequestQueue initRequestQueue(final Context context) {
        requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    private <T> void addRequestToQueue(Request<T> request, String requestTag) {
        if (requestTag != null)
            request.setTag(requestTag);
        requestQueue.add(request);
    }

}
