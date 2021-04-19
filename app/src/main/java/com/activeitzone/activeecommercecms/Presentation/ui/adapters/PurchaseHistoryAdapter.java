package com.activeitzone.activeecommercecms.Presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.PurchaseHistory;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.PurchaseHistoryCliclListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Utils.AppConfig;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {

    private List<PurchaseHistory> purchaseHistories;
    private LayoutInflater mInflater;
    private Context context;
    private PurchaseHistoryCliclListener purchaseHistoryCliclListener;

    // data is passed into the constructor
    public PurchaseHistoryAdapter(Context context, List<PurchaseHistory> purchaseHistories, PurchaseHistoryCliclListener purchaseHistoryCliclListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.purchaseHistories = purchaseHistories;
        this.purchaseHistoryCliclListener = purchaseHistoryCliclListener;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.purchase_history_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(purchaseHistories.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return purchaseHistories.size();
    }

    // convenience method for getting data at click position
    public PurchaseHistory getItem(int id) {
        return purchaseHistories.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_code, order_date, order_amount, payment_status;

        ViewHolder(View itemView) {
            super(itemView);
            order_code = itemView.findViewById(R.id.order_code);
            order_date = itemView.findViewById(R.id.order_date);
            payment_status = itemView.findViewById(R.id.payment_status);
            order_amount = itemView.findViewById(R.id.order_amount);
        }

        public void bind(PurchaseHistory purchaseHistory) {
            order_code.setText(purchaseHistory.getCode());
            order_date.setText("Date - "+purchaseHistory.getDate());
            payment_status.setText("Payment Status - "+ StringUtils.capitalize(purchaseHistory.getPaymentStatus()));
            order_amount.setText(AppConfig.convertPrice(context, purchaseHistory.getGrandTotal()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    purchaseHistoryCliclListener.onPurchaseHistoryItemClick(purchaseHistory);
                }
            });
        }
    }
}

