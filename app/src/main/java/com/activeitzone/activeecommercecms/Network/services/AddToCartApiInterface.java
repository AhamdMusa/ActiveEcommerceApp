package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.AddToCartResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddToCartApiInterface {
    @Headers("Content-Type: application/json")
    @POST("carts/add")
    Call<AddToCartResponse> sendAddToCartRequest(@Header("Authorization") String authHeader, @Body JsonObject jsonObject);
}
