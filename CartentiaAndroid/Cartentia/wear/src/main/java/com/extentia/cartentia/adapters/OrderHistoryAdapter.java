package com.extentia.cartentia.adapters;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.extentia.cartentia.R;

import java.util.ArrayList;

/**
 * Created by Extentia on 8/30/2015.
 */
public class OrderHistoryAdapter extends WearableListView.Adapter {

    private Context context;
    private ArrayList<Integer> list;

    public OrderHistoryAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new WearableListView.ViewHolder(new OrderView(context));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
        OrderView itemView = (OrderView) viewHolder.itemView;

        TextView txtView = (TextView) itemView.findViewById(R.id.orderIdTextView);
        txtView.setText(String.format("Item %d", i));

        Integer resourceId = list.get(i);
        CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.dateImageView);
        imgView.setImageResource(R.drawable.bg_date_icon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class OrderView extends FrameLayout implements WearableListView.Item {

    final CircledImageView dateImageView;
    final TextView orderIdTextView;
    private float scale;
    private final int normalCircleColor;
    private final int chosenCircleColor;
    float defaultCircleRadius;
    float selectedCircleRadius;

    public OrderView(Context context) {
        super(context);
        View.inflate(context, R.layout.orders_list_item_layout, this);
        dateImageView = (CircledImageView) findViewById(R.id.dateImageView);
        orderIdTextView = (TextView) findViewById(R.id.orderIdTextView);
        normalCircleColor = getResources().getColor(android.R.color.darker_gray);
        chosenCircleColor = getResources().getColor(android.R.color.holo_blue_dark);
        defaultCircleRadius = context.getResources().getDimension(R.dimen.default_settings_circle_radius);
        selectedCircleRadius = context.getResources().getDimension(R.dimen.selected_settings_circle_radius);
    }

    @Override
    public float getProximityMinValue() {
        return defaultCircleRadius;
    }

    @Override
    public float getProximityMaxValue() {
        return selectedCircleRadius;
    }

    @Override
    public float getCurrentProximityValue() {
        return scale;
    }

    @Override
    public void setScalingAnimatorValue(float value) {
        scale = value;
        dateImageView.setCircleRadius(scale);
        dateImageView.setCircleRadiusPressed(scale);
    }

    @Override
    public void onScaleUpStart() {
        dateImageView.setAlpha(1f);
        orderIdTextView.setAlpha(1f);
        dateImageView.setCircleColor(chosenCircleColor);
    }

    @Override
    public void onScaleDownStart() {
        dateImageView.setAlpha(0.5f);
        orderIdTextView.setAlpha(0.5f);
        dateImageView.setCircleColor(normalCircleColor);
    }
}
