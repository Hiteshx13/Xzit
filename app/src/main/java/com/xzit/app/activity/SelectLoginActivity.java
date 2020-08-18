package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySelectLoginBinding;

public class SelectLoginActivity extends AppCompatActivity implements View.OnClickListener {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ActivitySelectLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_login);
        listener();
    }

    private void listener() {

        binding.btnSignUpWithEmail.setOnClickListener(this);
        binding.txtsignIn.setOnClickListener(this);
        binding.btnFacebook.setOnClickListener(this);
        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
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
                Intent intentSignIn = new Intent(SelectLoginActivity.this, SignInActivity.class);
                startActivity(intentSignIn);
                break;
        }
    }
}
