package com.extentia.cartentia.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.extentia.cartentia.R;
import com.extentia.cartentia.receivers.CartentiaNotificationReceiver;


/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class StubBroadcastActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setAction("com.extentia.cartentia.SHOW_NOTIFICATION");
        i.putExtra(CartentiaNotificationReceiver.CONTENT_KEY, getString(R.string.title));
        sendBroadcast(i);
        finish();
    }
}
