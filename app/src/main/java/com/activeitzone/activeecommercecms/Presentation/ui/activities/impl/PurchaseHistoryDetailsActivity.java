package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.OrderDetail;
import com.activeitzone.activeecommercecms.Models.PurchaseHistory;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.PurchaseHistoryDetailsPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.PurchaseHistoryDetailsView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.OrderDetailsAdapter;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PurchaseHistoryDetailsActivity extends BaseActivity implements PurchaseHistoryDetailsView {
    private AuthResponse authResponse;
    private PurchaseHistory mPurchaseHistory;
    private TextView order_code, shipping_method, order_date, payment_method, payment_status, total_amount, shipping_address,
            sub_total, tax, shipping_cost, coupon_discount, grand_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history_details);

        initializeActionBar();
        setTitle("Order Details");
        initviews();
        mPurchaseHistory = (PurchaseHistory) getIntent().getSerializableExtra("purchase_history");
        setDetails();

        authResponse = new UserPrefs(getApplicationContext()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            new PurchaseHistoryDetailsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getOrderDetails(authResponse.getAccessToken(), mPurchaseHistory.getLinks().getDetails());
        }
    }

    private void initviews(){
        order_code = findViewById(R.id.order_code);
        shipping_method = findViewById(R.id.shipping_method);
        order_date = findViewById(R.id.order_date);
        payment_method = findViewById(R.id.payment_method);
        payment_status = findViewById(R.id.payment_status);
        total_amount = findViewById(R.id.total_amount);
        shipping_address = findViewById(R.id.shipping_address);

        sub_total = findViewById(R.id.sub_total);
        tax = findViewById(R.id.tax);
        shipping_cost = findViewById(R.id.shipping_cost);
        coupon_discount = findViewById(R.id.coupon_discount);
        grand_total = findViewById(R.id.grand_total);
    }

    private void setDetails(){
        order_code.setText(mPurchaseHistory.getCode());
        shipping_method.setText("Flat Shipping Rate");
        order_date.setText(mPurchaseHistory.getDate());
        payment_method.setText(StringUtils.capitalize(mPurchaseHistory.getPaymentType()));
        payment_status.setText(StringUtils.capitalize(mPurchaseHistory.getPaymentStatus()));
        total_amount.setText(AppConfig.convertPrice(this, mPurchaseHistory.getGrandTotal()));
        shipping_address.setText(mPurchaseHistory.getShippingAddress().getAddress() +", "+mPurchaseHistory.getShippingAddress().getCity()+", "+mPurchaseHistory.getShippingAddress().getCountry());

        sub_total.setText(AppConfig.convertPrice(this, mPurchaseHistory.getSubtotal()));
        tax.setText(AppConfig.convertPrice(this, mPurchaseHistory.getTax()));
        shipping_cost.setText(AppConfig.convertPrice(this, mPurchaseHistory.getShippingCost()));
        coupon_discount.setText(AppConfig.convertPrice(this, mPurchaseHistory.getCouponDiscount()));
        grand_total.setText(AppConfig.convertPrice(this, mPurchaseHistory.getGrandTotal()));
    }

    @Override
    public void onOrderDetailsLoaded(List<OrderDetail> orderDetailList) {
        RecyclerView recyclerView = findViewById(R.id.order_details_list);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        OrderDetailsAdapter adapter = new OrderDetailsAdapter(getApplicationContext(), orderDetailList);
        recyclerView.setAdapter(adapter);
    }
}
