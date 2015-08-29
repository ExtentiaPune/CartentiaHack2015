package com.extentia.cartentia.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.extentia.cartentia.R;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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
                    Intent dashboardIntent = new Intent(this, DashboardActivity.class);
                    startActivity(dashboardIntent);
                    finish();
                }
                break;
        }


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

}
