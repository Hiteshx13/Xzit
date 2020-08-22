package com.xzit.app.retrofit;

import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.login.masterdata.MasterDataResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<LoginResponse> callLogin(@FieldMap Map<String, String> postData);


    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<LoginResponse> callChangePassword(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<MasterDataResponse> callMasterData(@FieldMap Map<String, String> postData);

}