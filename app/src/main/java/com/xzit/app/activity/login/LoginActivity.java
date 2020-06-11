package com.xzit.app.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xzit.app.R;
import com.xzit.app.activity.dashboard.DashboardActivity;
import com.xzit.app.activity.signup.SignUpActivity;
import com.xzit.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding activityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();



    }

    private void initialization() {
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    private void listener() {
        activityLoginBinding.txtsignup.setOnClickListener(this);
        activityLoginBinding.imgbackscreen.setOnClickListener(this);
        activityLoginBinding. btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnSignIn:
                Intent intentDashboard=new Intent(this, DashboardActivity.class);
                startActivity(intentDashboard);
                break;
            case R.id.txtsignup:
                Intent intent=new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.imgbackscreen:
                finish();
                break;
        }
    }
}
