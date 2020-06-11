package com.xzit.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.xzit.app.activity.addpayment.AddPaymentctivity;
import com.xzit.app.activity.awesome.AweSomeActivity;
import com.xzit.app.activity.countryphonenumber.CountryPhoneNumberActivity;
import com.xzit.app.activity.faq.FAQActivity;
import com.xzit.app.activity.spashscreen.SplashScreen;
import com.xzit.app.activity.termsandconditions.TermandConditionsActivity;
import com.xzit.app.activity.venuepreference.VenuePreferenceActivity;
import com.xzit.app.databinding.ActivityDashboardBinding;
import com.xzit.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.btnSplash.setOnClickListener(view -> startActivity(new Intent(this, SplashScreen.class)));
        activityMainBinding.btnaddpayment.setOnClickListener(view -> startActivity(new Intent(this, AddPaymentctivity.class)));
        activityMainBinding.btnawesome.setOnClickListener(view -> startActivity(new Intent(this, AweSomeActivity.class)));
        activityMainBinding.btncountryphone.setOnClickListener(view -> startActivity(new Intent(this, CountryPhoneNumberActivity.class)));
        activityMainBinding.btnfaq.setOnClickListener(view -> startActivity(new Intent(this, FAQActivity.class)));
        activityMainBinding.btntermandconditions.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
        activityMainBinding.btnvenuepreference.setOnClickListener(view -> startActivity(new Intent(this, VenuePreferenceActivity.class)));

    }
}
