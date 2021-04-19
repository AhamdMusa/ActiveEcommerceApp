package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.ProductDetialsResponse;
import com.activeitzone.activeecommercecms.Network.services.ProductDetailsApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ProductDetailsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsInteractorImpl extends AbstractInteractor {
    private ProductDetailsInteractor.CallBack mCallback;
    private ProductDetailsApiInterface apiService;
    private String url;

    public ProductDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread, ProductDetailsInteractor.CallBack callBack, String url) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ProductDetailsApiInterface.class);
        Call<ProductDetialsResponse> call = apiService.getProductDetails(url);

        call.enqueue(new Callback<ProductDetialsResponse>() {
            @Override
            public void onResponse(Call<ProductDetialsResponse> call, Response<ProductDetialsResponse> response) {
                try {
                    //Log.d("Mehedi", response.toString());
                    mCallback.onProductDetailsDownloaded(response.body().getData().get(0));
                } catch (Exception e) {
                    //Log.d("Mehedi", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductDetialsResponse> call, Throwable t) {
                //Log.d("Mehedi", t.getLocalizedMessage());
                mCallback.onProductDetailsDownloadError();
            }
        });

    }
}
