package com.extentia.cartentia.dataprovider;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class JsonRequestHandler<T> extends Request<T> {

    private Response.Listener successListener;
    private Response.ErrorListener errorListener;
    private Class<T> requestClass;
    private Class<T> responseClass;

    private static final String CHARSET = "utf-8";


    public JsonRequestHandler(int methodType, String url, Class<T> requestClass, Class<T> responseClass, Response.Listener<T> successListener, Response.ErrorListener<T> errorListener) {
        super(methodType, url, errorListener);
        this.successListener = successListener;
        this.errorListener = errorListener;
        this.requestClass = requestClass;
        this.responseClass = responseClass;

    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (requestClass != null) {
            String jsonRequest = new Gson().toJson(requestClass);
            try {
                return jsonRequest.getBytes(CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return super.getBody();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String jsonResponse=new String (networkResponse.data, HttpHeaderParser.parseCharset());
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (successListener != null) {
            successListener.onResponse(response);
        }


    }
}
