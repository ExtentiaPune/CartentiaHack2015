package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.extentia.cartentia.R;
import com.extentia.cartentia.adapters.OrderHistoryAdapter;
import com.extentia.cartentia.views.activities.DashboardWearActivity;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment implements WearableListView.ClickListener {

    private WearableListView orderHistoryList;
    private OrderHistoryAdapter listAdapter;
    private View rootView;
    private static final String PARAM_ORDER_ID = "OrderId";

    private static ArrayList<Integer> listItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_history, container, false);
        initScreen();
        return rootView;
    }

    private void initScreen() {
        listAdapter = new OrderHistoryAdapter(getActivity(), listItems);
        orderHistoryList = (WearableListView) rootView.findViewById(R.id.listView1);
        orderHistoryList.setAdapter(listAdapter);
        orderHistoryList.setClickListener(OrderHistoryFragment.this);
    }

    static {
        listItems = new ArrayList<Integer>();
        listItems.add(R.drawable.icn_dashboard_cart);
        listItems.add(R.drawable.icn_dashboard_scan);
        listItems.add(R.drawable.icn_dashboard_history);
        listItems.add(R.drawable.icn_dashboard_cart);
        listItems.add(R.drawable.icn_dashboard_history);
        listItems.add(R.drawable.icn_dashboard_scan);
        listItems.add(R.drawable.icn_dashboard_cart);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        navigateToCart(viewHolder.getPosition() + "");
    }

    private void navigateToCart(String orderId) {
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_ORDER_ID, orderId);
        cartFragment.setArguments(bundle);
        ((DashboardWearActivity) getActivity()).moveToScreen(cartFragment);
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

}
