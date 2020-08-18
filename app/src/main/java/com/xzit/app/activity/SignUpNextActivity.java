package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignUpNextBinding;

public class SignUpNextActivity extends AppCompatActivity implements View.OnClickListener {



    private ActivitySignUpNextBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();



    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_next);
    }

    private void listener() {
        binding.btnSignUp.setOnClickListener(this);
        binding.imgFacebook.setOnClickListener(this);

        binding.imgbackscreen.setOnClickListener(this);
        binding.imgGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnSignUp:
                Intent intent=new Intent(this, PreferenceMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.imgFacebook:
                Intent intentForgetpassword=new Intent(this, ForgotPasswordActivity.class);
                startActivity(intentForgetpassword);
                break;
            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.imgGoogle:
                Intent intentEditprofile=new Intent(this, EditProfileActivity.class);
                startActivity(intentEditprofile);
                break;
        }
    }
}
