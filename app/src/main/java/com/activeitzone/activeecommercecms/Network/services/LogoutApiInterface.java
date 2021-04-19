package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface LogoutApiInterface {
    @Headers("Content-Type: application/json")
    @GET("auth/logout")
    Call<LogoutResponse> sendLogoutRequest(@Header("Authorization") String authHeader);
}
