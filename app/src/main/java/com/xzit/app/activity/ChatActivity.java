package com.xzit.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);


        binding.ivBack.setOnClickListener(view -> {
            finish();
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
