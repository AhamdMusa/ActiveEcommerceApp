package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.UserBid;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.MybidsPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.MybidView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.MyBidsAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.MyBidClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.RecyclerViewMargin;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MyBidsActivity extends BaseActivity implements MybidView, MyBidClickListener {

    private AuthResponse authResponse;
    private MybidsPresenter mybidsPresenter;
    private ProgressBar progressBar;
    private TextView mybids_empty_text;
    private BottomSheetDialog dialog;
    private ProgressDialog progressDialog;
    private List<UserBid> mUserBids = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyBidsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);

        initializeActionBar();
        setTitle("My Bids");

        progressBar = findViewById(R.id.item_progress_bar);
        mybids_empty_text = findViewById(R.id.mybids_empty_text);
        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.my_bids_list);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MyBidsActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new MyBidsAdapter(getApplicationContext(), mUserBids, this);
        RecyclerViewMargin decoration = new RecyclerViewMargin(AppConfig.convertDpToPx(this,10), 1);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        mybidsPresenter = new MybidsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        authResponse = new UserPrefs(getApplicationContext()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            mybidsPresenter.getUserBidsItems(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        else {
            //startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
            //getActivity().finish();
        }
    }

    @Override
    public void setUserBids(List<UserBid> userBids) {
        progressBar.setVisibility(View.GONE);
        if (userBids.size() > 0){
            mUserBids.clear();
            mUserBids.addAll(userBids);
            adapter.notifyDataSetChanged();
        }
        else {
            mybids_empty_text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAuctionBidSubmitted(AuctionBidResponse auctionBidResponse) {
        progressDialog.dismiss();
        CustomToast.showToast(this, auctionBidResponse.getMessage(), R.color.colorSuccess);
        progressBar.setVisibility(View.VISIBLE);
        mybidsPresenter.getUserBidsItems(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void onMyBidItemClicked(UserBid userBid) {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog_auction, null);
        ImageView image = view.findViewById(R.id.product_image);
        TextView current_bid_amount = view.findViewById(R.id.current_bid_amount);
        TextView total_bids = view.findViewById(R.id.total_bids);
        TextView name = view.findViewById(R.id.product_name);
        EditText bid_amount = view.findViewById(R.id.bid_amount);
        Button place_bid = view.findViewById(R.id.place_bid);

        Glide.with(this).load(AppConfig.ASSET_URL + userBid.getAuctionProduct().getData().get(0).getImage()).into(image);
        current_bid_amount.setText(AppConfig.convertPrice(this, userBid.getHighestBid()));
        total_bids.setText(userBid.getAuctionProduct().getData().get(0).getBidsCount()+" Bids");
        name.setText(userBid.getProduct());


        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();

        place_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = (bid_amount.getText().length() > 0) ? Double.valueOf(bid_amount.getText().toString()) : 0.0;
                if (amount > userBid.getHighestBid()){
                    authResponse = new UserPrefs(MyBidsActivity.this).getAuthPreferenceObjectJson("auth_response");
                    if(authResponse != null && authResponse.getUser() != null){
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("auction_product_id", userBid.getAuctionProductId());
                        jsonObject.addProperty("user_id", authResponse.getUser().getId());
                        jsonObject.addProperty("amount", amount);

                        dialog.hide();
                        progressDialog.setMessage("Your bid is being submitted. Please wait.");
                        progressDialog.show();
                        mybidsPresenter.submitBid(jsonObject, authResponse.getAccessToken());
                    }
                    else {
                        startActivityForResult(new Intent(MyBidsActivity.this, LoginActivity.class), 100);
                    }
                }
                else {
                    CustomToast.showToast(MyBidsActivity.this, "Your bidding amount must be greater than the current bid", R.color.colorWarning);
                    bid_amount.requestFocus();
                }
            }
        });
    }
}
