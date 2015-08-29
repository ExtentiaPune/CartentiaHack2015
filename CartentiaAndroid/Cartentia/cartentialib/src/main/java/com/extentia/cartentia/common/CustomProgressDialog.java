package com.extentia.cartentia.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class CustomProgressDialog {

    private static ProgressDialog progressDialog;

    public static void startProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

    }

    public static void stopProgressDialog(Context context) {
        if (progressDialog != null)
            progressDialog.dismiss();

    }
}
