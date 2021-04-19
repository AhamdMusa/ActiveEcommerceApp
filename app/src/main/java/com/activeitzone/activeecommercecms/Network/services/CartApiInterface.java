package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.CartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface CartApiInterface {
    @GET
    Call<CartResponse> getCartItems(@Header("Authorization") String authHeader, @Url String url);
}
