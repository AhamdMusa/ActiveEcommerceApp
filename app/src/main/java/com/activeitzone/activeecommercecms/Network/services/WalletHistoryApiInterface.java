package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.WalletBalanceResponse;
import com.activeitzone.activeecommercecms.Network.response.WalletHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WalletHistoryApiInterface {
    @GET("wallet/history/{id}")
    Call<WalletHistoryResponse> getWalletHistory(@Header("Authorization") String authHeader, @Path("id") int id);
}
