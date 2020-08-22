package com.xzit.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignUpBinding;
import com.xzit.app.repository.SignUpRepository;
import com.xzit.app.utils.AppUtilsKt;

import java.util.HashMap;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context, String type) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.putExtra(AppUtilsKt.PARAM_SIGNUP_TYPE, type);
        return intent;
    }

    private ActivitySignUpBinding binding;
    private String SIGNUP_TYPE;
    private int GENDER_SELECTED = 1;
    private SignUpRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        listener();
        if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_MERCHENT)) {
            binding.llGender.setVisibility(View.GONE);
            binding.etName.setHint(getString(R.string.businessname));
            binding.cvBirthDate.setVisibility(View.GONE);
        } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
            binding.llGender.setVisibility(View.VISIBLE);
            binding.btnMale.callOnClick();
            binding.etName.setHint(getString(R.string.username));
            binding.cvBirthDate.setVisibility(View.VISIBLE);
        }
    }

    private void initialization() {
        repository = new SignUpRepository();
        SIGNUP_TYPE = getIntent().getStringExtra(AppUtilsKt.PARAM_SIGNUP_TYPE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
    }

    private void listener() {
        binding.btnnext.setOnClickListener(this);
        binding.textsignin.setOnClickListener(this);
        binding.btnFacebook.setOnClickListener(this);

        binding.imgbackscreen.setOnClickListener(this);
        binding.btnGoogle.setOnClickListener(this);
        binding.btnMale.setOnClickListener(this);
        binding.btnFemale.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtsignup:


                break;
            case R.id.btnnext:
                if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_MERCHENT)) {
                    Intent intentNext = new Intent(this, RegistrationNextActivity.class);
                    startActivity(intentNext);
                } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
                    Intent intent = new Intent(this, PreferenceMusicActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.imgFacebook:
                Intent intentForgetpassword = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgetpassword);
                break;
            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.imgGoogle:
                Intent intentEditprofile = new Intent(this, EditProfileActivity.class);
                startActivity(intentEditprofile);
                break;
            case R.id.textsignin:
                Intent intentSignIn = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intentSignIn);
                break;
            case R.id.btnMale:
                binding.btnMale.setSelected(true);
                binding.btnFemale.setSelected(false);
                GENDER_SELECTED = 1;
                break;
            case R.id.btnFemale:
                binding.btnMale.setSelected(false);
                binding.btnFemale.setSelected(true);
                GENDER_SELECTED = 0;
                break;

            case R.id.btnGoogle:
                Intent signInIntent = getMGoogleSignInClient().getSignInIntent();
                startActivityForResult(signInIntent, AppUtilsKt.REQ_LOGIN_WITH_GMAIL);
                break;

            case R.id.btnFacebook:
                break;
        }
    }

    void callLogin() {

        /*{"requestCase":"signup","businessName":"Rock start Bar","email":"patil.deepak@outlook.com",
        "password":"1234","confirmPass":"1234","title":"Mr.",
        "description":"i am deepak patil","category":"BAR","phone":"8866539236",
        "website":"www.bar.com","businessHours":"10am to 15 pm",
        "userName" : "patil.deepak","userType":"NORMAL",
        "gender" : "MALE","authProvider" : "GMAIL","authUid" : 1234}*/

        String strEmail = binding.etEmail.getText().toString().trim();
        String strPassword = binding.etPassword.getText().toString().trim();

        if (strEmail.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_email));
        } else if (!AppUtilsKt.isEmailValid(strEmail)) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_valid_email));
        } else if (strPassword.isEmpty()) {
            AppUtilsKt.showToast(mContext, getString(R.string.please_enter_password));
        } else {

            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "signup");
            map.put("postData[email]", strEmail);
            map.put("postData[password]", strPassword);

            repository.callSignUp(mContext, map);
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
            updateUI(null);
        }
    }

    void updateUI(GoogleSignInAccount account) {
        Log.w("TAG", "signInResult:failed code=");
    }
}
