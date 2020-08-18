package com.xzit.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityFaqBinding;

public class FAQActivity extends AppCompatActivity {

    private ActivityFaqBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq);
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnOk.setOnClickListener(view -> finish());
    }
}
