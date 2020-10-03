package com.xzit.app.retrofit;

import android.util.Log;

import com.xzit.app.activity.BaseActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.xzit.app.activity.XzitApp.getAuthToken;
import static com.xzit.app.activity.XzitApp.preference;


public class ApiClient extends BaseActivity {


    public static final String BASE_URL = "http://3.96.139.216";
    // http://3.96.139.216/xzitAdmin/xzitProjext/getData.php
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        //LoginResponse userdata = preference.getUserData(mContext);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder newRequest = chain.request().newBuilder();

                        newRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
                        if (preference.isLoggedIn() && preference.getUserData() != null) {
                            newRequest.addHeader("Authorization", "Bearer " + getAuthToken());
                            Log.e("#AUTH_TOKEN",""+getAuthToken());
                        }
                        return chain.proceed(newRequest.build());
                    }
                })

                .build();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiInterface() {
        return getClient().create(ApiInterface.class);
    }
}