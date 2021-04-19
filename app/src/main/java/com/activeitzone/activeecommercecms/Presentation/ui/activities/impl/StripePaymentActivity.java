package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.StripeClientSecretResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.PaymentPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.StripePaymentView;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;

public class StripePaymentActivity extends BaseActivity implements StripePaymentView {
    private Stripe stripe;
    private static ProgressDialog progressDialog;
    private String paymentIntentClientSecret;
    private double total, shipping, tax, coupon_discount;
    private TextView tvSub_total, tvTax, tvShipping, tvCoupon_discount, tvGrand_total;
    private AuthResponse authResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);

        total = getIntent().getDoubleExtra("total", 0.0);
        shipping = getIntent().getDoubleExtra("shipping", 0.0);
        tax = getIntent().getDoubleExtra("tax", 0.0);
        coupon_discount = getIntent().getDoubleExtra("coupon_discount", 0.0);

        initializeActionBar();
        setTitle("Card Payment");

        initviews();

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");

        PaymentConfiguration.init(getApplicationContext(), AppConfig.STRIPE_KEY);

        startCheckout();
    }

    private void initviews(){
        progressDialog = new ProgressDialog(this);
        tvSub_total = findViewById(R.id.sub_total);
        tvTax = findViewById(R.id.tax);
        tvShipping = findViewById(R.id.shipping);
        tvCoupon_discount = findViewById(R.id.coupon_discount);
        tvGrand_total = findViewById(R.id.grand_total);

        tvSub_total.setText(String.valueOf(total-tax-shipping-coupon_discount));
        tvTax.setText(String.valueOf(tax));
        tvShipping.setText(String.valueOf(shipping));
        tvCoupon_discount.setText(String.valueOf(coupon_discount));
        tvGrand_total.setText(String.valueOf(total));
    }

    private void startCheckout() {
        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {

            progressDialog.setMessage("Please wait. Your transaction is processing.");
            progressDialog.show();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("grand_total", total);
            jsonObject.addProperty("coupon_discount", coupon_discount);
            jsonObject.addProperty("currency", "usd");

            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), StripePaymentActivity.this).submitStripeRequest(authResponse.getAccessToken(), jsonObject);
        });
    }

    // ...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    @Override
    public void onClientSecretReceived(StripeClientSecretResponse stripeClientSecretResponse) {
        if (stripeClientSecretResponse.getSuccess()){
            paymentIntentClientSecret = stripeClientSecretResponse.getClient_secret();
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());
                stripe.confirmPayment(this, confirmParams);
            }
        }
        else {
            CustomToast.showToast(this, stripeClientSecretResponse.getMessage(), R.color.colorDanger);
        }
    }

    // ...

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<StripePaymentActivity> activityRef;

        PaymentResultCallback(@NonNull StripePaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripePaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                progressDialog.dismiss();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("paymentIntentID", paymentIntent.getId());
                activity.setResult(Activity.RESULT_OK, returnIntent);
                activity.finish();

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                progressDialog.dismiss();
                CustomToast.showToast(activity, "Payment failed", R.color.colorDanger);
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripePaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method

        }
    }
}
