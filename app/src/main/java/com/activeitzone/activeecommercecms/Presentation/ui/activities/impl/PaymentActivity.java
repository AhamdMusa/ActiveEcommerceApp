package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.CouponResponse;
import com.activeitzone.activeecommercecms.Network.response.OrderResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.PaymentPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.PaymentView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.PaymentSelectAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.PaymentSelectListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends BaseActivity implements PaymentSelectListener, PaymentView, PaymentMethodNonceCreatedListener, BraintreeErrorListener {
    private String payment_method;
    private Button place_order;
    private BraintreeFragment mBraintreeFragment;
    private EditText coupon_code;
    private Button apply_coupon;
    private Double total, shipping, tax, coupon_discount = 0.0;
    private String shipping_address;
    private TextView total_amount;
    private ProgressDialog progressDialog;
    private AuthResponse authResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        total = getIntent().getDoubleExtra("total", 0.0);
        shipping = getIntent().getDoubleExtra("shipping", 0.0);
        tax = getIntent().getDoubleExtra("tax", 0.0);
        shipping_address = getIntent().getStringExtra("shipping_address");

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");

        initializeActionBar();
        setTitle("Checkout");
        setPaymentOptions();

        progressDialog = new ProgressDialog(this);
        place_order = findViewById(R.id.place_order);
        coupon_code = findViewById(R.id.coupon_code);
        apply_coupon = findViewById(R.id.apply_coupon);
        total_amount = findViewById(R.id.total_amount);

        total_amount.setText(AppConfig.convertPrice(this, total));

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment_method != null){
                    if (payment_method.equals("paypal")){
                        setupBraintreeAndStartExpressCheckout();
                    }
                    else if (payment_method.equals("card")){
                        Intent intent = new Intent(PaymentActivity.this, StripePaymentActivity.class);
                        intent.putExtra("total", total);
                        intent.putExtra("shipping", shipping);
                        intent.putExtra("tax", tax);
                        intent.putExtra("coupon_discount", coupon_discount);
                        startActivityForResult(intent, 500);
                    }
                    else if(payment_method.equals("cod")){
                        checkout_done(null);
                    }
                    else if(payment_method.equals("wallet")){
                        checkout_done(null);
                    }
                }
                else {
                    CustomToast.showToast(PaymentActivity.this, "Please select a payment method", R.color.colorWarning);
                }
            }
        });

        apply_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = coupon_code.getText().toString();
                if(code.length() > 0){
                    AuthResponse authResponse = new UserPrefs(PaymentActivity.this).getAuthPreferenceObjectJson("auth_response");
                    if(authResponse != null && authResponse.getUser() != null){
                        new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), PaymentActivity.this).applyCoupon(authResponse.getUser().getId(), code, authResponse.getAccessToken());
                    }
                    else {

                    }
                }
                else {
                    CustomToast.showToast(PaymentActivity.this, "Please enter coupon code first", R.color.colorWarning);
                }
            }
        });
    }

    private void setPaymentOptions(){
        List<PaymentModel> paymentModels = new ArrayList<>();
        if (AppConfig.BRAINTREE_KEY.length() > 0){
            paymentModels.add(new PaymentModel(R.drawable.paypal_logo_png_7, "paypal", "Checkout with PayPal"));
        }
        if (AppConfig.STRIPE_KEY.length() > 0){
            paymentModels.add(new PaymentModel(R.drawable.cardpayment, "card", "Checkout with Card"));
        }
        if (AppConfig.CASH_ON_DELIVERY) {
            paymentModels.add(new PaymentModel(R.drawable.cod, "cod", "Cash on Delivery"));
        }
        if (AppConfig.WALLET_USE) {
            paymentModels.add(new PaymentModel(R.drawable.wallet_select, "wallet", "Wallet Balance"));
        }

        RecyclerView recyclerView = findViewById(R.id.payment_select_list);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        PaymentSelectAdapter adapter = new PaymentSelectAdapter(this, paymentModels, this);
        recyclerView.setAdapter(adapter);
    }

    public void setupBraintreeAndStartExpressCheckout() {
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, AppConfig.BRAINTREE_KEY);
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
            Log.d("Braintree", e.getMessage());
        }

        PayPalRequest request = new PayPalRequest(String.valueOf(total))
                .currencyCode("USD")
                .intent(PayPalRequest.INTENT_AUTHORIZE);

        PayPal.requestOneTimePayment(mBraintreeFragment, request);
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        // Send nonce to server
        String nonce = paymentMethodNonce.getNonce();
        checkout_done(nonce);
    }

    @Override
    public void onError(Exception error) {
        Log.d("PayPal", error.getMessage());
        if (error instanceof ErrorWithResponse) {
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError cardErrors = errorWithResponse.errorFor("creditCard");
            if (cardErrors != null) {
                // There is an issue with the credit card.
                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
                if (expirationMonthError != null) {
                    // There is an issue with the expiration month.
                    //setErrorMessage(expirationMonthError.getMessage());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String paymentIntentID = data.getStringExtra("paymentIntentID");
            checkout_done(paymentIntentID);
        }
        if (resultCode == Activity.RESULT_CANCELED) {

        }

    }

    private void checkout_done(String paymentID){
        progressDialog.setMessage("Checkout is processing. Please wait.");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("shipping_address", shipping_address);
        jsonObject.addProperty("payment_type", payment_method);
        jsonObject.addProperty("payment_status", payment_method == "cod" ? "unpaid" : "paid");
        jsonObject.addProperty("user_id", authResponse.getUser().getId());
        jsonObject.addProperty("grand_total", total);
        jsonObject.addProperty("coupon_discount", coupon_discount);
        jsonObject.addProperty("coupon_code", "");

        if (payment_method.equals("paypal")){
            jsonObject.addProperty("nonce", paymentID);
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).submitPaypalOrder(authResponse.getAccessToken(), jsonObject);
        }
        else if(payment_method.equals("card")){
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).submitOrder(authResponse.getAccessToken(), jsonObject);
        }
        else if (payment_method.equals("wallet")){
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).submitWalletOrder(authResponse.getAccessToken(), jsonObject);
        }
        else if (payment_method.equals("cod")){
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).submitCODOrder(authResponse.getAccessToken(), jsonObject);
        }
    }

    @Override
    public void onPaymentItemClick(PaymentModel paymentModel) {
        payment_method = paymentModel.getPayment_method();
    }

    @Override
    public void onCouponApplied(CouponResponse couponResponse) {
        if (couponResponse.getSuccess()){
            CustomToast.showToast(this, couponResponse.getMessage(), R.color.colorSuccess);
            coupon_discount = couponResponse.getDiscount();
            total -= coupon_discount;
            total_amount.setText(AppConfig.convertPrice(this, total));
        }
        else {
            CustomToast.showToast(this, couponResponse.getMessage(), R.color.colorWarning);
        }
    }

    @Override
    public void onOrderSubmitted(OrderResponse orderResponse) {
        progressDialog.dismiss();
        if (orderResponse.getSuccess()){
            //CustomToast.showToast(this, orderResponse.getMessage(), R.color.colorSuccess);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("message", orderResponse.getMessage());
            intent.putExtra("position", "cart");
            startActivity(intent);
            finish();
        }
        else {
            CustomToast.showToast(this, orderResponse.getMessage(), R.color.colorDanger);
        }
    }

    public class PaymentModel{
        int drawable;
        String payment_method, payment_text;

        public PaymentModel(int drawable, String payment_method, String payment_text) {
            this.drawable = drawable;
            this.payment_method = payment_method;
            this.payment_text = payment_text;
        }

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getPayment_text() {
            return payment_text;
        }

        public void setPayment_text(String payment_text) {
            this.payment_text = payment_text;
        }
    }
}
