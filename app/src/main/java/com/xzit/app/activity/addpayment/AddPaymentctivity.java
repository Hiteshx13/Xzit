package com.xzit.app.activity.addpayment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityAddPaymentctivityBinding;
import com.xzit.app.databinding.ActivityVenuePreferenceBinding;

public class AddPaymentctivity extends AppCompatActivity {

    private ActivityAddPaymentctivityBinding activityAddPaymentctivityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityAddPaymentctivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_paymentctivity);

        activityAddPaymentctivityBinding.imgbackscreen.setOnClickListener(view -> finish());
    }
}
