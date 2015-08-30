package com.extentia.cartentia.dataprovider;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class JsonPostRequestHandler<T> extends JsonRequest<T> {

    private Response.Listener successListener;
    private Response.ErrorListener errorListener;
    private String jsonRequest;
    private Class<T> responseClass;

    private static final String CHARSET = "utf-8";


    public JsonPostRequestHandler(int methodType, String url, String jsonRequest, Class<T> responseClass, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        super(methodType, url, jsonRequest, successListener, errorListener);
        this.successListener = successListener;
        this.errorListener = errorListener;
        this.jsonRequest = jsonRequest;
        this.responseClass = responseClass;

    }


    @Override
    public String getPostBodyContentType() {
        return super.getPostBodyContentType();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonResponse = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            if (!TextUtils.isEmpty(jsonResponse) && jsonResponse.startsWith("["))
                jsonResponse = "{ \"data\" : " + jsonResponse + "}";
            else
                jsonResponse = "{ \"data\" : [ " + jsonResponse + " ]}";

            T result = new GsonBuilder().create().fromJson(jsonResponse, responseClass);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            errorListener.onErrorResponse(new ParseError(e));
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (successListener != null) {
            successListener.onResponse(response);
        }
    }
}
