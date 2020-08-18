package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySignInBinding;
import com.xzit.app.repository.LoginRepository;
import com.xzit.app.retrofit.model.request.LoginRequest;
import com.xzit.app.retrofit.model.response.LoginResponse;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignInBinding binding;
    private LoginRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        repository = new LoginRepository();
    }

    private void listener() {
        binding.txtsignup.setOnClickListener(this);
        binding.imgbackscreen.setOnClickListener(this);
        binding.btnSignIn.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                Intent intentDashboard = new Intent(this, DashboardActivity.class);
                startActivity(intentDashboard);
                //callLogin();

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
        }
    }


    void callLogin() {
        repository.callLogin(new LoginRequest( "test23@gmail.com", "1234"));
//        repository.loginData.observe(this, new Observer<LoginResponse>() {
//            @Override
//            public void onChanged(LoginResponse loginResponse) {
//
//            }
//        });
    }

}
