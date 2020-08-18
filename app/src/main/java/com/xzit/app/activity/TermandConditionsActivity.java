package com.xzit.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityTermandConditionsBinding;

public class TermandConditionsActivity extends AppCompatActivity {

    private ActivityTermandConditionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_termand_conditions);

        binding.imgbackscreen.setOnClickListener(view -> finish());
    }
}
