package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.CartModel;
import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.CartItemListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ViewHolder> {
    private List<ShippingAddress> shippingAddresses;
    private LayoutInflater mInflater;
    private Context context;
    private ShippingAddressListener shippingAddressListener;

    public Context getContext(){
        return this.context;
    }

    // data is passed into the constructor
    public ShippingAddressAdapter(Context context, List<ShippingAddress> shippingAddresses, ShippingAddressListener shippingAddressListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.shippingAddresses = shippingAddresses;
        this.shippingAddressListener = shippingAddressListener;
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
        holder.bind(shippingAddresses.get(position));
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
        Toolbar toolbar;

        ViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            postal_code = itemView.findViewById(R.id.postal_code);
            country = itemView.findViewById(R.id.country);
            phone = itemView.findViewById(R.id.phone);
            toolbar = itemView.findViewById(R.id.shipping_card_toolbar);
        }

        public void bind(ShippingAddress shippingAddress) {
            address.setText(shippingAddress.getAddress());
            city.setText(shippingAddress.getCity());
            postal_code.setText(shippingAddress.getPostalCode());
            country.setText(shippingAddress.getCountry());
            phone.setText(shippingAddress.getPhone());

            toolbar.inflateMenu(R.menu.shipping_address_menu);

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.address_delete:
                            shippingAddressListener.onItemDeleteClick(shippingAddress);
                    }
                    return false;
                }
            });
        }
    }
}

