package com.extentia.cartentia.presenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.LoginData;
import com.extentia.cartentia.presenters.BasePresenter;
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
        Request request = new JsonRequestHandler(Request.Method.GET, url, null, LoginData.class, new Response.Listener<LoginData>() {
            @Override
            public void onResponse(LoginData response) {
                if (loginView != null && response != null && response.getData() != null && response.getData().get(0) != null) {
                    PreferenceManager.setGroupID(response.getData().get(0).getGroupID());
                    PreferenceManager.setName(response.getData().get(0).getName());
                    PreferenceManager.setUserID(response.getData().get(0).get_id());
                    PreferenceManager.setUsername(response.getData().get(0).getUsername());
                    PreferenceManager.setRole(response.getData().get(0).getRole().getName());

                    loginView.onLoginSuccuss();
                }
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

