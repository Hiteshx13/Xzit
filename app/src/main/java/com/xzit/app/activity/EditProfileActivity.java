package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.activity.ChangePasswordActivity;
import com.xzit.app.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityEditProfileBinding activityEditProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
    }

    private void listener() {

        activityEditProfileBinding.imgbackscreen.setOnClickListener(this);
        activityEditProfileBinding.tvResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.tvResetPassword:

                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
