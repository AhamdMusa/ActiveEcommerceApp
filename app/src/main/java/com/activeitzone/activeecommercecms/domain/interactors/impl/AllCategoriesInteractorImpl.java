package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.CategoryResponse;
import com.activeitzone.activeecommercecms.Network.services.AllCategoryApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.AllCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoriesInteractorImpl extends AbstractInteractor {
    private AllCategoryInteractor.CallBack mCallback;
    private AllCategoryApiInterface apiService;

    public AllCategoriesInteractorImpl(Executor threadExecutor, MainThread mainThread, AllCategoryInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(AllCategoryApiInterface.class);
        Call<CategoryResponse> call = apiService.getAllCategories();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                try {
                    mCallback.onAllCategoriesDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                mCallback.onAllCategoriesDownloadError();
            }
        });

    }
}
