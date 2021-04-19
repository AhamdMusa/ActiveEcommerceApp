package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeitzone.activeecommercecms.Models.UserBid;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MyBidClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyBidsAdapter extends RecyclerView.Adapter<MyBidsAdapter.ViewHolder> {
    private List<UserBid> userBids;
    private LayoutInflater mInflater;
    private Context context;
    private MyBidClickListener myBidClickListener;

    // data is passed into the constructor
    public MyBidsAdapter(Context context, List<UserBid> userBids, MyBidClickListener myBidClickListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.userBids = userBids;
        this.myBidClickListener = myBidClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mybid_product_box, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull MyBidsAdapter.ViewHolder holder, int position) {
        holder.bind(userBids.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return userBids.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, bid_amount, bid_time, highest_bid, place_bid;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            bid_amount = itemView.findViewById(R.id.bid_amount);
            bid_time = itemView.findViewById(R.id.bid_time);
            highest_bid = itemView.findViewById(R.id.highest_bid);
            place_bid = itemView.findViewById(R.id.place_bid);
        }

        public void bind(UserBid userBid) {
            name.setText(userBid.getProduct());
            bid_amount.setText(AppConfig.convertPrice(context, userBid.getAmount()));
            bid_time.setText(userBid.getDate());
            highest_bid.setText(AppConfig.convertPrice(context, userBid.getHighestBid()));

            if (userBid.getHighestBid() == userBid.getAmount()){
                highest_bid.setBackgroundColor(Color.GRAY);
            }

            if (userBid.getBiddable()){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myBidClickListener.onMyBidItemClicked(userBid);
                    }
                });
            }
            else {
                highest_bid.setBackgroundColor(Color.GRAY);
                place_bid.setVisibility(View.GONE);
            }
        }
    }
}
