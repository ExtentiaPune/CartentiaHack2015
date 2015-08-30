package com.extentia.cartentia.view.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.extentia.cartentia.CartentiaApplication;
import com.extentia.cartentia.R;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.common.PreferenceManager;
import com.extentia.cartentia.dataprovider.JsonRequestHandler;
import com.extentia.cartentia.dataprovider.VolleyManager;
import com.extentia.cartentia.models.AddCartRequest;
import com.extentia.cartentia.models.AddMyCartResponse;
import com.extentia.cartentia.view.fragment.AddProductFragment;
import com.extentia.cartentia.view.fragment.MyCartFragment;
import com.google.gson.Gson;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class BaseActivity extends AppCompatActivity {


    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    private NdefMessage[] ndefMessages;
    private NfcAdapter nfcAdapter;
    private PendingIntent nfcPendingIntent;
    private IntentFilter[] intentFiltersArray;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initToolbar();
        initNavDrawer();
        initNFC();
        setUpNFCIntentFilters();

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setHomeUpEnabled(false);


    }

    public void setTitle(String title) {
        ((TextView) toolbar.findViewById(R.id.toolbarTitle)).setText(title);
    }

    private void initNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    } else
                        drawerLayout.openDrawer(Gravity.LEFT);


                }
                return false;
            }

        };
        drawerLayout.setDrawerListener(drawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().findItem(R.id.my_cart).setTitle("" + PreferenceManager.getName() + "'s Cart");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.my_cart:

                        loadMyCartFragment();
                        break;
                    case R.id.order_history:
                        break;
                    case R.id.about_us:
                        break;
                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }


        });
        drawerToggle.syncState();
    }

    public void loadMyCartFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (!(fragment instanceof MyCartFragment)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.executePendingTransactions();
            Fragment addProductFragment = MyCartFragment.getInstance();
            fragmentManager.beginTransaction().add(R.id.frame, addProductFragment, MyCartFragment.class.getSimpleName()).addToBackStack(null).commit();
        }
    }

    private void logout() {
        PreferenceManager.setUserID(null);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finishAffinity();
    }

    private void setHomeUpEnabled(boolean homeUpEnabled) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray, null);
        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void initNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, getString(R.string.nfc_feature_not_supported_txt), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, getString(R.string.nfc_disabled_txt), Toast.LENGTH_LONG).show();
        } else {

        }
    }

    private void setUpNFCIntentFilters() {

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                ndefMessages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    ndefMessages[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (ndefMessages != null && ndefMessages.length > 0) {
                String productId = new String(ndefMessages[0].getRecords()[0].getPayload());
                productId = productId != null ? productId.replace("en", "") : "Invalid Product tag";
                // Toast.makeText(getBaseContext(), body != null ? body.replace("en", "") : "Invalid Product tag", Toast.LENGTH_LONG).show();
                productId = productId.replace(String.valueOf(productId.charAt(0)), "");
                if (!CartentiaApplication.MY_CART_PRODUCTS.contains(productId)) {
                    addProductToCart(productId);
                    loadAddProductFragment(productId);
                } else {
                    Toast.makeText(getBaseContext(), "Product is already added into your cart", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(), " Empty Product tag", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addProductToCart(String productId) {

        CartentiaApplication.MY_CART_PRODUCTS.add(productId);
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setUserID(PreferenceManager.getUserID());
        addCartRequest.setProductID(productId);
        addCartRequest.setGroupID(PreferenceManager.getGroupID());
        addCartRequest.setID(1);
        String jsnRequest = new Gson().toJson(addCartRequest);
        Request request = new JsonRequestHandler(Request.Method.POST, Constants.Url.ADD_CART, jsnRequest, AddMyCartResponse.class, new Response.Listener<AddMyCartResponse>() {
            @Override
            public void onResponse(AddMyCartResponse response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyManager.getInstance().addRequestToQueue(request, "AddCart");
    }

    private void loadAddProductFragment(String productId) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.executePendingTransactions();
        Fragment addProductFragment = AddProductFragment.getInstance(productId.toString());
        fragmentManager.beginTransaction().add(R.id.frame, addProductFragment, AddProductFragment.class.getSimpleName()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            if (nfcAdapter != null)
                nfcAdapter.disableForegroundDispatch(this);
        }

    }


}
