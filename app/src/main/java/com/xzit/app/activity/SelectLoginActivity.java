package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySelectLoginBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.LoginRepository;
import com.xzit.app.repository.MasterDataRepository;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.login.Pref;
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse;
import com.xzit.app.utils.AppPreference;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.Arrays;
import java.util.HashMap;

import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;

public class SelectLoginActivity extends BaseActivity implements View.OnClickListener {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ActivitySelectLoginBinding binding;
    CallbackManager callbackManager;
    MasterDataRepository repository;
    private LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_login);
        repository = new MasterDataRepository();
        loginRepository = new LoginRepository();
        callbackManager = CallbackManager.Factory.create();

        binding.btnFacebook.setPermissions(Arrays.asList("email"));

        listener();
        setObserver();
        callMasterData();

    }

    void callMasterData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("postData[requestCase]", "masterdatalist");
        repository.callMasterData(mContext, params);
    }

    private void listener() {

        binding.btnSignUpWithEmail.setOnClickListener(this);
        binding.txtsignIn.setOnClickListener(this);
        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        // Callback registration
        binding.btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("", "");
            }

            @Override
            public void onCancel() {
                Log.e("", "");
            }

            @Override
            public void onError(FacebookException exception) {

                HashMap<String, String> map = new HashMap<>();
                map.put("postData[requestCase]", "login");
                map.put("postData[authuid]", "1234");
                map.put("postData[authType]", "FACEBOOK");
                loginRepository.callLogin(mContext, map);

                Log.e("", "");
            }
        });
    }

    private void setObserver() {
        repository.getResponseData().observe(this, new Observer<MasterDataResponse>() {
            @Override
            public void onChanged(MasterDataResponse response) {
                if (response.getStatus() == RESP_API_SUCCESS) {
                    AppPreference preference = new AppPreference();
                    preference.saveMasterData(mContext, response);

                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });

        loginRepository.getLoginData().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse response) {
                if (response != null && response.getStatus() == RESP_API_SUCCESS) {
                    preference.saveLoginData(mContext, response);
                    Pref pref = response.getResponse().get(0).getPref();
                    Intent intentDashboard = new Intent(mContext, PreferenceMusicActivity.class);
                    if (pref.getFood().size() > 0 || pref.getMusic().size() > 0 || pref.getVenue().size() > 0) {
                        intentDashboard = new Intent(mContext, DashboardActivity.class);
                    }
                    startActivity(intentDashboard);
                    finish();

                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), false, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignUpWithEmail:
                Intent intent = new Intent(SelectLoginActivity.this, SelectSignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFacebook:
                break;
            case R.id.txtsignIn:
                Intent intentSignIn = new Intent(SelectLoginActivity.this, LoginActivity.class);
                startActivity(intentSignIn);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
