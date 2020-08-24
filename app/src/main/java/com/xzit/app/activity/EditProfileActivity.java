package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityEditProfileBinding;
import com.xzit.app.retrofit.model.response.login.LoginResponse;

import static com.xzit.app.activity.XzitApp.preference;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {


    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        LoginResponse userdata = preference.getUserData(mContext);
        binding.tvName.setText(userdata.getResponse().get(0).getUsername());
        binding.etFirstName.setText(userdata.getResponse().get(0).getUsername());
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
    }

    private void listener() {

        binding.imgbackscreen.setOnClickListener(this);
        binding.tvResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.tvResetPassword:

                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
