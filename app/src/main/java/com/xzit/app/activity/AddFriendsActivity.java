package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityAddFriendsBinding;

import static com.xzit.app.utils.AppUtilsKt.DASHBOARD_TAB;

public class AddFriendsActivity extends AppCompatActivity {

    private ActivityAddFriendsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friends);


        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(DASHBOARD_TAB, 1);
            startActivity(intent);
        });
    }
//        binding.llTnc.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
//        binding.btnawesome.setOnClickListener(view -> startActivity(new Intent(this, AweSomeActivity.class)));
//        binding.btncountryphone.setOnClickListener(view -> startActivity(new Intent(this, CountryPhoneNumberActivity.class)));
//        binding.btnfaq.setOnClickListener(view -> startActivity(new Intent(this, FAQActivity.class)));
//        binding.btntermandconditions.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
//        binding.btnvenuepreference.setOnClickListener(view -> startActivity(new Intent(this, VenuePreferenceActivity.class)));
//        binding. btnfoodpreference.setOnClickListener(view -> startActivity(new Intent(this, FoodPreferenceActivity.class)));
//        binding.btnmusicpreference.setOnClickListener(view -> startActivity(new Intent(this, MusicPreferenceActivity.class)));
//        binding.btngenderbirthdate.setOnClickListener(view -> startActivity(new Intent(this, GenderBirthdateActivity.class)));

}
