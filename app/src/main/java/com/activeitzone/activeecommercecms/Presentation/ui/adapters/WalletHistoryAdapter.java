package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.Wallet;
import com.activeitzone.activeecommercecms.Models.WishlistModel;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.WishlistProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    private List<Wallet> mWallets;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public WalletHistoryAdapter(Context context, List<Wallet> mWallets) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mWallets = mWallets;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.wallet_history_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mWallets.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mWallets.size();
    }

    // convenience method for getting data at click position
    public Wallet getItem(int id) {
        return mWallets.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount, method, approval;

        ViewHolder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            method = itemView.findViewById(R.id.payment_method);
            approval = itemView.findViewById(R.id.approval);
        }

        public void bind(Wallet wallet) {
            amount.setText(AppConfig.convertPrice(context, wallet.getAmount()));
            method.setText(wallet.getPaymentMethod());
            approval.setText(wallet.getApproval());
        }
    }
}

