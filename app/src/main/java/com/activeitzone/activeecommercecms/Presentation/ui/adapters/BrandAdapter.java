package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.BrandClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private List<Brand> mBrands;
    private LayoutInflater mInflater;
    private Context context;
    private BrandClickListener brandClickListener;

    // data is passed into the constructor
    public BrandAdapter(Context context, List<Brand> mBrands, BrandClickListener brandClickListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mBrands = mBrands;
        this.brandClickListener = brandClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.popular_brand_box, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.ViewHolder holder, int position) {
        holder.bind(mBrands.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mBrands.size();
    }

    // convenience method for getting data at click position
    public Brand getItem(int id) {
        return mBrands.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView discounted_price;
        TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.brand_logo);
        }

        public void bind(Brand brand) {
            Glide.with(context).load(AppConfig.ASSET_URL + brand.getLogo()).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    brandClickListener.onBrandItemClick(brand);
                }
            });
        }
    }
}
