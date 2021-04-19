package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.ShippingResponse;
import com.activeitzone.activeecommercecms.Network.response.WalletBalanceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WalletBalanceApiInterface {
    @GET("wallet/balance/{id}")
    Call<WalletBalanceResponse> getWalletbalance(@Header("Authorization") String authHeader, @Path("id") int id);
}
