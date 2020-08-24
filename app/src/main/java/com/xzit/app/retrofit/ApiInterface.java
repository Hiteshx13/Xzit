package com.xzit.app.retrofit;

import com.squareup.okhttp.RequestBody;
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse;
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse;
import com.xzit.app.retrofit.model.response.preference.PreferenceResponse;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<LoginResponse> callLogin(@FieldMap Map<String, String> postData);


    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<ChangePasswordResponse> callChangePassword(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<ForgotPasswordResponse> callForgotPassword(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<MasterDataResponse> callMasterData(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<PreferenceResponse> callPreference(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(@FieldMap Map<String, String> postData,
                                                @Part("attachmentName\"; filename=\"image.png\" ") RequestBody file);

    /* map.put("postData[requestCase]", "signup");
            map.put("postData[userType]", "BUSINESS");
            map.put("postData[businessName]", signUpRequest.getBusinessName());
            map.put("postData[email]", signUpRequest.getEmail());
            map.put("postData[password]", signUpRequest.getPassword());
            map.put("postData[confirmPass]", signUpRequest.getConfPassword());
            map.put("postData[userName]", strUserName);
            map.put("postData[title]", strTelephone);
            map.put("postData[category]", strCategory);
            map.put("postData[phone]", strTelephone);
            map.put("postData[website]", strWebsite);
            map.put("postData[businessHours]", strBusinessHours);*/
    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(@Header("Authorization") String authorization,
                                                @Part("file\"; filename=\"image.png\" ") RequestBody file,
                                                @Part("FirstName") RequestBody fname,
                                                @Part("Id") RequestBody id);
}