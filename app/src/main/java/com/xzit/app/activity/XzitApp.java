package com.xzit.app.activity;

import android.app.Application;
import android.content.Context;

import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.utils.AppPreference;

public class XzitApp extends Application {

    public static AppPreference preference;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        preference = new AppPreference();
    }

    public static LoginData getLoginUserData() {
        return preference.getUserData();
    }

    public static String getAuthToken() {
        return preference.getAuthToken();
    }
}
