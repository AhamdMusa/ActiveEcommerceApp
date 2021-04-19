package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activeitzone.activeecommercecms.Network.response.RegistrationResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.RegisterPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.RegisterView;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private TextView input_name, input_email, input_password, input_confirm_password;
    private Button btn_signUp;
    private Boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeActionBar();
        setTitle("My Account");

        initviews();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = true;

                if(input_name.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_name_layout);
                    til.setError("Name is required");
                    isValid = false;
                }

                if(input_email.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_email_layout);
                    til.setError("Email is required");
                    isValid = false;
                }

                if(input_password.getText().toString().length() <= 5){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_password_layout);
                    til.setError("Password is required and must be 6 characters at least");
                    isValid = false;
                }

                //Log.d("Test", String.valueOf(input_password.getText()));

                if(!input_confirm_password.getText().toString().equals(input_password.getText().toString())){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_confirm_password_layout);
                    til.setError("Confirm password mismatch with password");
                    isValid = false;
                }

                if (isValid){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", input_name.getText().toString());
                    jsonObject.addProperty("email", input_email.getText().toString());
                    jsonObject.addProperty("password", input_password.getText().toString());
                    jsonObject.addProperty("passowrd_confirmation", input_confirm_password.getText().toString());

                    new RegisterPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), RegisterActivity.this).validateRegistration(jsonObject);
                }
            }
        });
    }

    private void initviews(){
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_confirm_password = findViewById(R.id.input_confirm_password);
        btn_signUp = findViewById(R.id.btn_signUp);
    }

    @Override
    public void setRegistrationResponse(RegistrationResponse registrationResponse) {
        CustomToast.showToast(this, registrationResponse.getMessage(), R.color.colorSuccess);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
