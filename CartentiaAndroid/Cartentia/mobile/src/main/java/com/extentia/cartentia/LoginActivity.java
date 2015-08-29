package com.extentia.cartentia;


import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/**
 * Created by Extentia on 8/29/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private NfcAdapter mNfcAdapter;

    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    private NdefMessage[] msgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        initView();
        handleIntent(getIntent());
    }

    private void initView() {

//        username = (EditText) findViewById(R.id.usernameEditText);
//        password = (EditText) findViewById(R.id.passwordEditText);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        } else {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mNfcAdapter.enableReaderMode(this, this, READER_FLAGS, null);
            }*/
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC Disable.", Toast.LENGTH_LONG).show();
        } else {

        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    public void onClick(View view) throws UnsupportedEncodingException {


    }

    private void handleIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }

            MifareClassic mifareClassic = MifareClassic.get(null);

            String body = new String(msgs[0].getRecords()[0].getPayload());
            Toast.makeText(getBaseContext(), body != null ? body.replace("en", "") : "Invalid Product tag", Toast.LENGTH_LONG).show();
        }
    }
}
