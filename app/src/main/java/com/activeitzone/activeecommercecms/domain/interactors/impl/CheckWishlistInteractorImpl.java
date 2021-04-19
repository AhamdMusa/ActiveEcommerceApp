package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.CheckWishlistResponse;
import com.activeitzone.activeecommercecms.Network.services.CheckWishlistApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.CheckWishlistInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckWishlistInteractorImpl extends AbstractInteractor {
    private CheckWishlistInteractor.CallBack mCallback;
    private CheckWishlistApiInterface apiService;
    private int user_id;
    private int product_id;
    private String auth_token;

    public CheckWishlistInteractorImpl(Executor threadExecutor, MainThread mainThread, CheckWishlistInteractor.CallBack callBack, String auth_token, int user_id, int product_id) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.product_id = product_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(CheckWishlistApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", user_id);
        jsonObject.addProperty("product_id", product_id);

        Call<CheckWishlistResponse> call = apiService.checkWishlistRequest(auth_token, jsonObject);

        call.enqueue(new Callback<CheckWishlistResponse>() {
            @Override
            public void onResponse(Call<CheckWishlistResponse> call, Response<CheckWishlistResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onWishlistChecked(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckWishlistResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onWishlistCheckedError();
            }
        });

    }
}
