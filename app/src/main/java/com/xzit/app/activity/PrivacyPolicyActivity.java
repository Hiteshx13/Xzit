package com.xzit.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityPrivacyPolicyBinding;
import com.xzit.app.databinding.ActivityTermandConditionsBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);

        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnOk.setOnClickListener(view -> finish());
    }
}
