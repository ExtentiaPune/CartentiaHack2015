package com.extentia.cartentia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


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
