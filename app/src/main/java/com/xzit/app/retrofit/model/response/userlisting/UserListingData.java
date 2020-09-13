package com.xzit.app.retrofit.model.response.userlisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListingData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("businessname")
    @Expose
    private String businessname;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
