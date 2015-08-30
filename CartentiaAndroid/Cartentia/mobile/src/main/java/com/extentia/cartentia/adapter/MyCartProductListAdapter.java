package com.extentia.cartentia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.extentia.cartentia.R;
import com.extentia.cartentia.common.Constants;
import com.extentia.cartentia.models.MyCartResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhijeet.Bhosale on 8/29/2015.
 */
public class MyCartProductListAdapter extends RecyclerView.Adapter<MyCartProductListAdapter.ViewHolder> {

    private ArrayList<MyCartResponse> myCartRespons;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private ImageView productImage;
        private TextView totalAmt;
        private TextView description;
        private TextView rate;
        private ImageView productInc;
        private ImageView productDec;
        private TextView quantity;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            productImage = (ImageView) view.findViewById(R.id.productImage);
            productName = (TextView) view.findViewById(R.id.productName);
            description = (TextView) view.findViewById(R.id.productDescription);
            rate = (TextView) view.findViewById(R.id.productRate);
            productDec = (ImageView) view.findViewById(R.id.productDecrease);
            productInc = (ImageView) view.findViewById(R.id.productIncrease);
            quantity = (TextView) view.findViewById(R.id.quantity);
            totalAmt = (TextView) view.findViewById(R.id.totalAmt);

        }


    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public MyCartProductListAdapter(Context context, ArrayList<MyCartResponse> dataSet) {
        myCartRespons = myCartRespons;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mycart_product_list_item, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.productName.setText(myCartRespons.get(position).getProductID().getProductName());
        viewHolder.description.setText(myCartRespons.get(position).getProductID().getProductDescription());
        viewHolder.rate.setText(myCartRespons.get(position).getProductID().getPrice());
        viewHolder.totalAmt.setText(String.valueOf(Double.valueOf(myCartRespons.get(position).getProductID().getPrice()) * Double.valueOf(myCartRespons.get(position).getProductID().getDefaultQty())));
        viewHolder.quantity.setText(myCartRespons.get(position).getProductID().getDefaultQty());
        Picasso.with(context).load(Constants.Url.IMAGE_HOST_URL + myCartRespons.get(position).getProductID().getProductImage());
    }


    @Override
    public int getItemCount() {
        return myCartRespons != null ? myCartRespons.size() : 0;
    }

}