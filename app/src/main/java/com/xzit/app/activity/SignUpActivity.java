package com.xzit.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignUpBinding;
import com.xzit.app.utils.AppUtilsKt;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent getIntent(Context context, String type) {
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.putExtra(AppUtilsKt.PARAM_SIGNUP_TYPE, type);
        return intent;
    }

    private ActivitySignUpBinding binding;
    private String SIGNUP_TYPE;
    private int GENDER_SELECTED = 1;

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
        SIGNUP_TYPE = getIntent().getStringExtra(AppUtilsKt.PARAM_SIGNUP_TYPE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
    }

    private void listener() {
        binding.btnnext.setOnClickListener(this);
        binding.textsignin.setOnClickListener(this);
        binding.imgFacebook.setOnClickListener(this);

        binding.imgbackscreen.setOnClickListener(this);
        binding.imgGoogle.setOnClickListener(this);
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
                    Intent intentNext = new Intent(this, SignUpNextActivity.class);
                    startActivity(intentNext);
                } else if (SIGNUP_TYPE.equals(AppUtilsKt.SIGNUP_TYPE_USER)) {
                    Intent intent=new Intent(this, PreferenceMusicActivity.class);
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
                Intent intentSignIn = new Intent(SignUpActivity.this, SignInActivity.class);
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
        }
    }

}
