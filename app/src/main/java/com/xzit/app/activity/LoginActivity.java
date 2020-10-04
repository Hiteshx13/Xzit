package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignInBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.LoginRepository;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.login.Pref;
import com.xzit.app.utils.AppPreference;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.HashMap;
import java.util.concurrent.Executor;

import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignInBinding binding;
    private LoginRepository repository;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private boolean isShowPassword=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        listener();
        setObserver();

        if (new AppPreference().isLoggedIn()) {

            BiometricManager biometricManager = BiometricManager.from(this);
            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:

                    performBiometricAuthentication();
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Log.e("MY_APP_TAG", "No biometric features available on this device.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Log.e("MY_APP_TAG", "The user hasn't associated " +
                            "any biometric credentials with their account.");
                    break;
            }
        }
    }

    void performBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.app_name))
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        repository = new LoginRepository();


    }

    private void listener() {
        binding.txtsignup.setOnClickListener(this);
        binding.imgbackscreen.setOnClickListener(this);
        binding.btnSignIn.setOnClickListener(this);
        binding.btnGoogle.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);
        binding.etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.etPassword.getRight())) {
                        isShowPassword=!isShowPassword;
                        if(isShowPassword){
                            binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext,R.drawable.icn_password_hide), null);
                        }else{
                            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext,R.drawable.icn_password_show), null);
                            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setObserver() {
        repository.getLoginData().observe(this, new Observer<LoginResponse>() {
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
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), true, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                callLogin(false, "");

                break;
            case R.id.txtsignup:
                Intent intent = new Intent(this, SelectSignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.txtForgotPassword:
                Intent intentForgotPass = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgotPass);
                break;
            case R.id.btnGoogle:
                Intent signInIntent = getMGoogleSignInClient().getSignInIntent();
                startActivityForResult(signInIntent, AppUtilsKt.REQ_LOGIN_WITH_GMAIL);
                break;

            case R.id.btnFacebook:
                break;
        }
    }


    void callLogin(boolean isSocial, String authType) {

        String strEmail = binding.etEmail.getText().toString().trim();
        String strPassword = binding.etPassword.getText().toString().trim();


        HashMap<String, String> map = new HashMap<>();
        map.put("postData[requestCase]", "login");
        if (isSocial) {
            map.put("postData[authuid]", "1234");
            map.put("postData[authType]", authType);
            repository.callApi(mContext, map);
        } else {
            if (strEmail.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_email));
            } else if (!AppUtilsKt.isEmailValid(strEmail)) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
            } else if (strPassword.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_enter_password));
            } else {
                map.put("postData[username]", strEmail);
                map.put("postData[password]", strPassword);
                repository.callApi(mContext, map);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppUtilsKt.REQ_LOGIN_WITH_GMAIL) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    void updateUI(GoogleSignInAccount account) {
        Log.w("TAG", "signInResult:failed code=");
        callLogin(true, "GMAIL");
    }

}
