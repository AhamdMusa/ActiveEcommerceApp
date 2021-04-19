package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.PurchaseHistoryDetailsResponse;
import com.activeitzone.activeecommercecms.Network.services.PurchaseHistoryDetailsApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.PurchaseHistoryDetailsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryDetailsInteractorImpl extends AbstractInteractor {
    private PurchaseHistoryDetailsInteractor.CallBack mCallback;
    private PurchaseHistoryDetailsApiInterface apiService;
    private String url;
    private String token;

    public PurchaseHistoryDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread, PurchaseHistoryDetailsInteractor.CallBack callBack, String url, String token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
        this.token = "Bearer "+token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(PurchaseHistoryDetailsApiInterface.class);
        Call<PurchaseHistoryDetailsResponse> call = apiService.getOrderItems(token, url);

        call.enqueue(new Callback<PurchaseHistoryDetailsResponse>() {
            @Override
            public void onResponse(Call<PurchaseHistoryDetailsResponse> call, Response<PurchaseHistoryDetailsResponse> response) {
                try {
                    mCallback.onOrderDetailsLoaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PurchaseHistoryDetailsResponse> call, Throwable t) {
                mCallback.onOrderDetailsLoadError();
            }
        });

    }
}
