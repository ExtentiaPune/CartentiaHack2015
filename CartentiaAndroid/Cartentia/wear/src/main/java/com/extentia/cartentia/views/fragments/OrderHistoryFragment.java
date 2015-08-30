package com.extentia.cartentia.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.extentia.cartentia.R;
import com.extentia.cartentia.adapters.OrderHistoryAdapter;
import com.extentia.cartentia.models.OrderHistory;
import com.extentia.cartentia.presenters.OrderHistoryPresenter;
import com.extentia.cartentia.views.activities.DashboardWearActivity;
import com.extentia.cartentia.views.interfaces.OrderHistoryView;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment implements WearableListView.ClickListener, OrderHistoryView {

    private WearableListView orderHistoryList;
    private OrderHistoryAdapter listAdapter;
    private OrderHistoryPresenter orderHistoryPresenter;
    private View rootView;
    private static final String PARAM_ORDER_ID = "OrderId";
    private static final String PARAM_IS_FROM_ORDERS = "IsFromOrders";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_history, container, false);
        initPresenter();
        initScreen();
        return rootView;
    }

    private void initPresenter() {
        orderHistoryPresenter = new OrderHistoryPresenter(this);
    }

    private void initScreen() {
        orderHistoryPresenter.getOrderHistory();
    }


    @Override
    public void onOrderHistoryError() {
    }

    @Override
    public void onOrderHistorySuccuss(ArrayList<OrderHistory> dataList) {
        listAdapter = new OrderHistoryAdapter(getActivity(), dataList);
        orderHistoryList = (WearableListView) rootView.findViewById(R.id.listView1);
        orderHistoryList.setAdapter(listAdapter);
        orderHistoryList.setClickListener(OrderHistoryFragment.this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        navigateToCart(viewHolder.getPosition() + "");
    }

    private void navigateToCart(String orderId) {
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_ORDER_ID, orderId);
        bundle.putBoolean(PARAM_IS_FROM_ORDERS, true);
        cartFragment.setArguments(bundle);
        ((DashboardWearActivity) getActivity()).moveToScreen(cartFragment);
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

}
