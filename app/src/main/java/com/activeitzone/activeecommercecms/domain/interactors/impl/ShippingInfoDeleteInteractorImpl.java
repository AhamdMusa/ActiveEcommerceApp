package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.ShippingInfoResponse;
import com.activeitzone.activeecommercecms.Network.services.ShippingInfoCreateApiInterface;
import com.activeitzone.activeecommercecms.Network.services.ShippingInfoDeleteApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ShippingInfoCreateInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ShippingInfoDeleteInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingInfoDeleteInteractorImpl extends AbstractInteractor {
    private ShippingInfoDeleteInteractor.CallBack mCallback;
    private ShippingInfoDeleteApiInterface apiService;
    private int address_id;
    private String auth_token;

    public ShippingInfoDeleteInteractorImpl(Executor threadExecutor, MainThread mainThread, ShippingInfoDeleteInteractor.CallBack callBack, int address_id, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.address_id = address_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ShippingInfoDeleteApiInterface.class);
        Call<ShippingInfoResponse> call = apiService.deleteShippingAddress(auth_token, address_id);
        call.enqueue(new Callback<ShippingInfoResponse>() {
            @Override
            public void onResponse(Call<ShippingInfoResponse> call, Response<ShippingInfoResponse> response) {
                try {
                    mCallback.onShippingInfoDeleted(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ShippingInfoResponse> call, Throwable t) {
                Log.d("Test", t.getMessage());
                mCallback.onShippingInfoDeleteError();
            }
        });

    }
}
