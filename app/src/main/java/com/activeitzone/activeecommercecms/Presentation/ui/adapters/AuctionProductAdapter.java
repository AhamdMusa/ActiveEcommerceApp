package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.AuctionClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class AuctionProductAdapter extends RecyclerView.Adapter<AuctionProductAdapter.ViewHolder> {
    private List<AuctionProduct> mProducts;
    private LayoutInflater mInflater;
    private Context context;
    private AuctionClickListener auctionClickListener;

    // data is passed into the constructor
    public AuctionProductAdapter(Context context, List<AuctionProduct> mProducts, AuctionClickListener auctionClickListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mProducts = mProducts;
        this.auctionClickListener = auctionClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.auction_product_box, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull AuctionProductAdapter.ViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView current_bid_amount, total_bids;
        TextView name;
        CountdownView mCvCountdownView;
        Button bid_now_button;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            current_bid_amount = itemView.findViewById(R.id.current_bid_amount);
            total_bids = itemView.findViewById(R.id.total_bids);
            name = itemView.findViewById(R.id.product_name);
            mCvCountdownView = (CountdownView) itemView.findViewById(R.id.countdown);
            bid_now_button = itemView.findViewById(R.id.bid_now_button);
        }

        public void bind(AuctionProduct auctionProduct) {
            Glide.with(context).load(AppConfig.ASSET_URL + auctionProduct.getImage()).into(image);
            current_bid_amount.setText(AppConfig.convertPrice(context, auctionProduct.getCurrentPrice()));
            total_bids.setText(auctionProduct.getBidsCount()+" Bids");
            name.setText(auctionProduct.getName());
            mCvCountdownView.start(auctionProduct.getEndDate());

            bid_now_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auctionClickListener.onAuctionItemClick(auctionProduct);
                }
            });
        }
    }
}
