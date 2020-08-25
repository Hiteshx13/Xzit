package com.xzit.app.retrofit;

import com.squareup.okhttp.RequestBody;
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse;
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse;
import com.xzit.app.retrofit.model.response.editprofile.EditProfileResponse;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse;
import com.xzit.app.retrofit.model.response.preference.PreferenceResponse;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;

import java.util.Map;

import okhttp3.MultipartBody;
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
    Call<EditProfileResponse> callEditProfile(@FieldMap Map<String, String> postData);

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

    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(

            @Part("postData[requestCase]") RequestBody signup,
            @Part("postData[userType]") RequestBody BUSINESS,
            @Part("postData[businessName]") RequestBody businessName,
            @Part("postData[email]") RequestBody email,
            @Part("postData[password]") RequestBody password,
            @Part("postData[confirmPass]") RequestBody confPassword,
            @Part("postData[userName]") RequestBody userName,
            @Part("postData[title]") RequestBody title,
            @Part("postData[category]") RequestBody category,
            @Part("postData[phone]") RequestBody phone,
            @Part("postData[website]") RequestBody website,
            @Part("postData[businessHours]") RequestBody businessHours,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(@Header("Authorization") String authorization,
                                                @Part("file\"; filename=\"image.png\" ") RequestBody file,
                                                @Part("FirstName") RequestBody fname,
                                                @Part("Id") RequestBody id);
}