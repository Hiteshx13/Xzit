package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityAweSomeBinding;

public class AweSomeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAweSomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_awe_some);
        binding.btnDiscover.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDiscover:
                Intent intentDashboard = new Intent(this, DashboardActivity.class);
                startActivity(intentDashboard);
                finish();
                break;
        }
    }
}
