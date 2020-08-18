package com.xzit.app.retrofit;

import com.xzit.app.retrofit.model.request.LoginRequest;
import com.xzit.app.retrofit.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("requestCase=login")
    Call<LoginResponse> callLogin(@Body LoginRequest model );
}