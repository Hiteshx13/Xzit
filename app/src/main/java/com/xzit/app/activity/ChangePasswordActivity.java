package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityChangePasswordBinding activityChangePasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        activityChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
    }

    private void listener() {

        activityChangePasswordBinding.imgbackscreen.setOnClickListener(this);
        activityChangePasswordBinding.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.btnSave:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
