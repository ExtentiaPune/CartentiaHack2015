package com.extentia.cartentia.views.interfaces;

import com.extentia.cartentia.models.OrderHistory;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/30/2015.
 */
public interface OrderHistoryView {

    void onOrderHistorySuccuss(ArrayList<OrderHistory> orderHistoryList);

    void onOrderHistoryError();
}
