package com.xzit.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityAddPaymentctivityBinding;

public class AddPaymentctivity extends AppCompatActivity {

    private ActivityAddPaymentctivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_paymentctivity);

        binding.imgbackscreen.setOnClickListener(view -> finish());
    }
}
