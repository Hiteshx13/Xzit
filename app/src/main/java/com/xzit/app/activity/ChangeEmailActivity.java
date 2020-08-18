package com.xzit.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityChangeEmailBinding;
import com.xzit.app.databinding.ActivityForgotPasswordBinding;

public class ChangeEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityChangeEmailBinding activityChangeEmailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();



    }

    private void initialization() {
        activityChangeEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_email);
    }

    private void listener() {

        activityChangeEmailBinding.imgbackscreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {

            case R.id.imgbackscreen:
                finish();
                break;
        }
    }
}
