package com.xzit.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySelectSignupBinding;
import com.xzit.app.utils.AppUtilsKt;

public class SelectSignUpActivity extends AppCompatActivity implements View.OnClickListener {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ActivitySelectSignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_signup);
        listener();
    }

    private void listener() {

        binding.btnMerchant.setOnClickListener(this);
        binding.btnUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnMerchant:
                startActivity(SignUpActivity.getIntent(this, AppUtilsKt.SIGNUP_TYPE_MERCHENT));
                break;
            case R.id.btnUser:
                startActivity(SignUpActivity.getIntent(this, AppUtilsKt.SIGNUP_TYPE_USER));
                break;
        }
    }
}
