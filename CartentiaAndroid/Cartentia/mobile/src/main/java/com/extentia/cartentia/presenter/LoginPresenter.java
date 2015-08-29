package com.extentia.cartentia.presenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.LoginResponse;
import com.extentia.cartentia.view.interfaces.LoginView;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class LoginPresenter implements BasePresenter {

    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void doLogin(String username, String password) {
        String url = String.format(Constants.Url.LOGIN_URL, username, password);
        Request request = new JsonRequestHandler(Request.Method.GET, url, null, LoginResponse.class, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (loginView != null)
                    loginView.onLoginSuccuss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (loginView != null)
                    loginView.onLoginError();
            }
        });
        VolleyManager.getInstance().addRequestToQueue(request, "Login");

    }

    @Override
    public void onDestroy() {
        loginView = null;
    }
}

