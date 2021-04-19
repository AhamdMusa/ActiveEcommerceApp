package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.CouponResponse;
import com.activeitzone.activeecommercecms.Network.services.CouponApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.CouponInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponInteractorImpl extends AbstractInteractor {
    private CouponInteractor.CallBack mCallback;
    private CouponApiInterface apiService;
    private int user_id;
    private String coupon_code;
    private String auth_token;

    public CouponInteractorImpl(Executor threadExecutor, MainThread mainThread, CouponInteractor.CallBack callBack, int user_id, String coupon_code, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.coupon_code = coupon_code;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(CouponApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", user_id);
        jsonObject.addProperty("code", coupon_code);

        Call<CouponResponse> call = apiService.getCouponResponse(auth_token, jsonObject);

        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onCouponApplied(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onCouponAppliedError();
            }
        });

    }
}
