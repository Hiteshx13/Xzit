package com.xzit.app.retrofit;

import com.xzit.app.retrofit.model.response.createevent.CreateEventResponse;
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse;
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse;
import com.xzit.app.retrofit.model.response.dashboard.FCMTokenResponse;
import com.xzit.app.retrofit.model.response.editprofile.EditProfileResponse;
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponse;
import com.xzit.app.retrofit.model.response.eventinvitation.EventInvitationResponse;
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationReceived;
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationSent;
import com.xzit.app.retrofit.model.response.friendlist.FriendListResponse;
import com.xzit.app.retrofit.model.response.friendrequest.AcceptRejectFriendRequestResponse;
import com.xzit.app.retrofit.model.response.friendrequest.BlockUnblockUserResponse;
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestResponse;
import com.xzit.app.retrofit.model.response.friendrequest.ReportUsertResponse;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse;
import com.xzit.app.retrofit.model.response.preference.PreferenceResponse;
import com.xzit.app.retrofit.model.response.profile.UserProfileResponse;
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse;
import com.xzit.app.retrofit.model.response.userlisting.SendFriendRequestResponse;
import com.xzit.app.retrofit.model.response.userlisting.UserListingResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<LoginResponse> callLogin(@FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<FCMTokenResponse> callUpdateFCMToken(@FieldMap Map<String, String> postData);


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

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<UserListingResponse> callUserListing(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<UserProfileResponse> callUserProfile(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<BlockUnblockUserResponse> callBlockUser(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<FriendRequestResponse> callFriendRequest(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<ReportUsertResponse> callReportUser(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<FriendListResponse> callFriendList(
            @FieldMap Map<String, String> postData);

 @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<EventInvitationResponse> callEventInvitation(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<AcceptRejectFriendRequestResponse> callAcceptRejectRequest(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<SendFriendRequestResponse> callSendFriendRequest(
            @FieldMap Map<String, String> postData);

    @FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<CreateEventResponse> callCreateEvent(
            @FieldMap Map<String, String> postData);

@FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<EventListingResponse> callEventListing(
            @FieldMap Map<String, String> postData);
@FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<EventInvitationSent> callAllSendInvitationByUser(
            @FieldMap Map<String, String> postData);

@FormUrlEncoded
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<EventInvitationReceived> callReceivedEventInvitation(
            @FieldMap Map<String, String> postData);


    /* map["postData[requestCase]"] = getRequestBody("createEvent")
                map["postData[eventType]"] = getRequestBody("Casual")
                map["postData[clientId]"] = getRequestBody(userData.clientId)
                map["postData[userId]"] = getRequestBody(userData.userId)*/

    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<CreateEventResponse> callCreateEventImage(
            @Part("postData[requestCase]") RequestBody requestCase ,
            @Part("postData[clientId]") RequestBody clientId ,
            @Part("postData[userId]") RequestBody userId ,
            @Part("postData[eventTitleName]") RequestBody eventTitleName ,
            @Part("postData[eventDescDetail]") RequestBody eventDescDetail ,

            @Part  MultipartBody.Part   image
    );

    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<CreateEventResponse> callCreateEventImage(
            @PartMap HashMap<String, RequestBody> requestCase ,
            @Part  MultipartBody.Part   image
    );

    /*  type:RequestBody,title:RequestBody,desc:RequestBody, image: MultipartBody.Part*/



//    @Multipart
//    @POST("/xzitAdmin/xzitProject/getData.php")
//    Call<RegistrationResponse> callRegisterData(
//
//
////            @Part("postData[requestCase]") RequestBody signup,
////            @Part("postData[userType]") RequestBody BUSINESS,
////            @Part("postData[businessName]") RequestBody businessName,
////            @Part("postData[email]") RequestBody email,
////            @Part("postData[password]") RequestBody password,
////            @Part("postData[confirmPass]") RequestBody confPassword,
////            @Part("postData[userName]") RequestBody userName,
////            @Part("postData[title]") RequestBody title,
////            @Part("postData[category]") RequestBody category,
////            @Part("postData[phone]") RequestBody phone,
////            @Part("postData[website]") RequestBody website,
////            @Part("postData[businessHours]") RequestBody businessHours,
//            @Part MultipartBody.Part image);


    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(
            @PartMap HashMap<String, okhttp3.RequestBody> params,
            @Part MultipartBody.Part image
    );


    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> callRegisterData(@Header("Authorization") String authorization,
                                                @Part("file\"; filename=\"image.png\" ") RequestBody file,
                                                @Part("FirstName") RequestBody fname,
                                                @Part("Id") RequestBody id);

    @Multipart
    @POST("/xzitAdmin/xzitProject/getData.php")
    Call<RegistrationResponse> apiRegister(@PartMap HashMap<String, RequestBody> params);
}