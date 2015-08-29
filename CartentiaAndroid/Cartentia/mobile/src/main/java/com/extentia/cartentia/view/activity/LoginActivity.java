package com.extentia.cartentia.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.extentia.cartentia.R;
import com.extentia.cartentia.common.CustomProgressDialog;
import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.presenter.LoginPresenter;
import com.extentia.cartentia.view.interfaces.LoginView;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(PreferenceManager.getUserID())) {
            navigateToDashBoardActivity();
            return;
        }
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();

    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
    }


    private void initView() {
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
    }

    public void onClick(View onClickView) {
        switch (onClickView.getId()) {
            case R.id.forgotPassword:
                Toast.makeText(getBaseContext(), "Not implemented! Coming soon.", Toast.LENGTH_LONG).show();

                break;
            case R.id.login:
                if (isValidInput()) {
                    CustomProgressDialog.startProgressDialog(this);
                    loginPresenter.doLogin(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                }
                break;
        }


    }

    private void navigateToDashBoardActivity() {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);
        finish();
    }

    private boolean isValidInput() {
        if (TextUtils.isEmpty(usernameEditText.getText().toString().trim())) {
            usernameEditText.setFocusable(true);
            usernameEditText.setCursorVisible(true);
            usernameEditText.setError(getString(R.string.empty_username_txt));
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
            passwordEditText.setFocusable(true);
            passwordEditText.setCursorVisible(true);
            passwordEditText.setError(getString(R.string.empty_password_txt));
            return false;
        }
        return true;
    }

    @Override
    public void onLoginSuccuss() {
        CustomProgressDialog.stopProgressDialog(this);
        Toast.makeText(getBaseContext(), getString(R.string.login_success_txt), Toast.LENGTH_LONG).show();
        navigateToDashBoardActivity();
    }

    @Override
    public void onLoginError() {
        CustomProgressDialog.stopProgressDialog(this);
        Toast.makeText(getBaseContext(), getString(R.string.login_failed_txt), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}
