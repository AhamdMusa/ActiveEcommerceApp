package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressSelectListener;
import com.activeitzone.activeecommercecms.R;

import java.util.List;

public class ShippingAddressSelectAdapter extends RecyclerView.Adapter<ShippingAddressSelectAdapter.ViewHolder> {
    private List<ShippingAddress> shippingAddresses;
    private LayoutInflater mInflater;
    private Context context;
    private ShippingAddressSelectListener shippingAddressSelectListener;
    private int row_index = -1;

    public Context getContext(){
        return this.context;
    }

    // data is passed into the constructor
    public ShippingAddressSelectAdapter(Context context, List<ShippingAddress> shippingAddresses, ShippingAddressSelectListener shippingAddressSelectListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.shippingAddresses = shippingAddresses;
        this.shippingAddressSelectListener = shippingAddressSelectListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shipping_address_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(shippingAddresses.get(position), position);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return shippingAddresses.size();
    }

    // convenience method for getting data at click position
    public ShippingAddress getItem(int id) {
        return shippingAddresses.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView city;
        TextView postal_code;
        TextView country;
        TextView phone;
        LinearLayout shipping_address_layout;

        ViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            postal_code = itemView.findViewById(R.id.postal_code);
            country = itemView.findViewById(R.id.country);
            phone = itemView.findViewById(R.id.phone);
            shipping_address_layout = itemView.findViewById(R.id.shipping_address_layout);
        }

        public void bind(ShippingAddress shippingAddress, int position) {
            address.setText(shippingAddress.getAddress());
            city.setText(shippingAddress.getCity());
            postal_code.setText(shippingAddress.getPostalCode());
            country.setText(shippingAddress.getCountry());
            phone.setText(shippingAddress.getPhone());

            if(row_index == position){
                shipping_address_layout.setBackgroundResource(R.drawable.payment_card_selected_background);
            }
            else
            {
                shipping_address_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = position;
                    notifyDataSetChanged();
                    shippingAddressSelectListener.onShippingAddressItemClick(shippingAddress);
                }
            });
        }
    }
}

