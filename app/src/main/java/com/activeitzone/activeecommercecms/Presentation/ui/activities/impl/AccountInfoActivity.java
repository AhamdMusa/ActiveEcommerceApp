package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Models.User;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.ProfileInfoUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.AccountInfoPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.AccountInfoView;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ShippingAddressAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ShippingAddressListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.List;

public class AccountInfoActivity extends BaseActivity implements AccountInfoView, ShippingAddressListener {
    private AuthResponse authResponse;
    private EditText input_name, input_email;
    private Button save_profile_info;
    private CardView card_new_address;
    AlertDialog.Builder builder;
    private AlertDialog alert;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        initializeActionBar();
        setTitle("Account Information");

        initviews();

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            setUpData();
            new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getShippingAddresses(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
    }

    private void initviews(){
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        save_profile_info = findViewById(R.id.save_profile_info);
        card_new_address = findViewById(R.id.card_new_address);
        builder = new AlertDialog.Builder(this);
        recyclerView = findViewById(R.id.rv_shipping_addresses);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getApplicationContext(), 10)) );

        card_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment the below code to Set the message and title from the strings.xml file
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.shipping_address_add, null);
                builder.setView(dialogLayout);

                //Setting message manually and performing action on button click
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText input_address = dialogLayout.findViewById(R.id.input_address);
                        EditText input_city = dialogLayout.findViewById(R.id.input_city);
                        EditText input_country = dialogLayout.findViewById(R.id.input_country);
                        EditText input_postal_code = dialogLayout.findViewById(R.id.input_postal_code);
                        EditText input_phone = dialogLayout.findViewById(R.id.input_phone);

                        Boolean isValid = true;

                        if(input_address.getText().toString().length() <= 0){
                            TextInputLayout til = dialogLayout.findViewById(R.id.input_address_layout);
                            til.setError("Address is required");
                            isValid = false;
                        }
                        if(input_city.getText().toString().length() <= 0){
                            TextInputLayout til = dialogLayout.findViewById(R.id.input_city_layout);
                            til.setError("City is required");
                            isValid = false;
                        }

                        if(input_postal_code.getText().toString().length() <= 0){
                            TextInputLayout til = dialogLayout.findViewById(R.id.input_postal_code_layout);
                            til.setError("Postal code is required");
                            isValid = false;
                        }

                        if(input_country.getText().toString().length() <= 0){
                            TextInputLayout til = dialogLayout.findViewById(R.id.input_country_layout);
                            til.setError("Country is required");
                            isValid = false;
                        }

                        if(input_phone.getText().toString().length() <= 0){
                            TextInputLayout til = dialogLayout.findViewById(R.id.input_phone_layout);
                            til.setError("Phone number is required");
                            isValid = false;
                        }

                        if (isValid){
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("address", input_address.getText().toString());
                            jsonObject.addProperty("city", input_city.getText().toString());
                            jsonObject.addProperty("country", input_country.getText().toString());
                            jsonObject.addProperty("phone", input_phone.getText().toString());
                            jsonObject.addProperty("postal_code", input_postal_code.getText().toString());
                            jsonObject.addProperty("user_id", authResponse.getUser().getId());

                            new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).addNewShippingRequest(jsonObject, authResponse.getAccessToken());

                            dialog.dismiss();
                        }
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                alert = builder.create();
                alert.setTitle("Shipping Information");
                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                });

                alert.show();
            }
        });
    }

    private void setUpData(){
        input_name.setText(authResponse.getUser().getName());
        input_email.setText(authResponse.getUser().getEmail());
        input_email.setEnabled(false);

        save_profile_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isValid = true;

                if(input_name.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_name_layout);
                    til.setError("Name is required");
                    isValid = false;
                }

                if (isValid){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", input_name.getText().toString());
                    jsonObject.addProperty("user_id", authResponse.getUser().getId());
                    new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).sendUpdateProfileRequest(jsonObject, authResponse.getAccessToken());
                }
            }
        });
    }

    @Override
    public void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse) {
        CustomToast.showToast(this, profileInfoUpdateResponse.getMessage(), R.color.colorSuccess);
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getUpdatedUserInfo(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        ShippingAddressAdapter adapter = new ShippingAddressAdapter(getApplicationContext(), shippingAddresses, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onShippingInfoCreated(ShippingInfoResponse shippingInfoResponse) {
        CustomToast.showToast(this, shippingInfoResponse.getMessage(), R.color.colorSuccess);
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getShippingAddresses(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void onShippingInfoDeleted(ShippingInfoResponse shippingInfoResponse) {
        CustomToast.showToast(this, shippingInfoResponse.getMessage(), R.color.colorSuccess);
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).getShippingAddresses(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void setUpdatedUserInfo(User user) {
        authResponse.setUser(user);
        UserPrefs userPrefs = new UserPrefs(getApplicationContext());
        userPrefs.setAuthPreferenceObject(authResponse, "auth_response");
    }

    @Override
    public void onItemDeleteClick(ShippingAddress shippingAddress) {
        new AccountInfoPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), AccountInfoActivity.this).deleteShippingAddress(shippingAddress.getId(), authResponse.getAccessToken());
    }
}
