package com.xzit.app.activity.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xzit.app.R;
import com.xzit.app.activity.editprofile.EditProfileActivity;
import com.xzit.app.activity.forgotpassword.ForgotPasswordActivity;
import com.xzit.app.databinding.ActivityLoginBinding;
import com.xzit.app.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {



    private ActivitySignUpBinding activitySignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();



    }

    private void initialization() {
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
    }

    private void listener() {
        activitySignUpBinding.textsignin.setOnClickListener(this);
        activitySignUpBinding.imgFacebook.setOnClickListener(this);

        activitySignUpBinding.imgbackscreen.setOnClickListener(this);
        activitySignUpBinding.imgGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.txtsignup:
                Intent intent=new Intent(this, SignUpActivity.class);
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
