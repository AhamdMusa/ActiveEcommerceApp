package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.OrderDetail;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private List<OrderDetail> orderDetails;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public OrderDetailsAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.orderDetails = orderDetails;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_details_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orderDetails.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    // convenience method for getting data at click position
    public OrderDetail getItem(int id) {
        return orderDetails.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_variant, product_qty, product_price, delivery_status;

        ViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_variant = itemView.findViewById(R.id.product_variant);
            product_qty = itemView.findViewById(R.id.product_qty);
            product_price = itemView.findViewById(R.id.product_price);
            delivery_status = itemView.findViewById(R.id.delivery_status);
        }

        public void bind(OrderDetail orderDetail) {
            product_name.setText(orderDetail.getProduct());
            product_variant.setText(orderDetail.getVariation());
            product_qty.setText(orderDetail.getQuantity().toString());
            product_price.setText(AppConfig.convertPrice(context, orderDetail.getPrice()));
            delivery_status.setText(orderDetail.getDeliveryStatus().toUpperCase());
        }
    }
}

