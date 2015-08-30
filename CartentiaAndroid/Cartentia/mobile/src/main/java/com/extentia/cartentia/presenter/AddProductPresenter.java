package com.extentia.cartentia.presenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.ProductData;
import com.extentia.cartentia.presenters.BasePresenter;
import com.extentia.cartentia.view.interfaces.AddProductView;

/**
 * Created by Abhijeet.Bhosale on 8/30/2015.
 */
public class AddProductPresenter implements BasePresenter {

    private AddProductView addProductView;


    public AddProductPresenter(AddProductView addProductView) {
        this.addProductView = addProductView;
    }

    public void fetchProduct(final String productId) {
        String url = Constants.Url.GET_PRODUCT_URL + productId;
        Request request = new JsonRequestHandler(Request.Method.GET, url, null, ProductData.class, new Response.Listener<ProductData>() {
            @Override
            public void onResponse(ProductData product) {
                if (addProductView != null && product != null && product.getData() != null && !product.getData().isEmpty())
                    addProductView.displayProduct(product.getData().get(0));
                else {
                    if (addProductView != null)
                        addProductView.displayNoProductFoundError();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (addProductView != null)
                    addProductView.displayNoProductFoundError();
            }
        });
        VolleyManager.getInstance().addRequestToQueue(request, "AddProduct");
    }


    @Override
    public void onDestroy() {

    }
}
