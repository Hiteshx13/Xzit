package com.xzit.app.retrofit.model.response.userlisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListingData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
}
