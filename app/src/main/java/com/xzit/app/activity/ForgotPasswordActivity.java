package com.xzit.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityForgotPasswordBinding activityForgotPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        activityForgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
    }

    private void listener() {

        activityForgotPasswordBinding.imgbackscreen.setOnClickListener(this);
        activityForgotPasswordBinding.btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.btnSignIn:
                finish();
                break;
        }
    }
}
