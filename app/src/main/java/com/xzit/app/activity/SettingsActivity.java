package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySettingsBinding;

import static com.xzit.app.activity.XzitApp.preference;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        binding.llChangePass.setOnClickListener(view -> startActivity(new Intent(this, ChangePasswordActivity.class)));
        binding.llTnc.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
        binding.llPrivacy.setOnClickListener(view -> startActivity(new Intent(this, PrivacyPolicyActivity.class)));
        binding.llPayment.setOnClickListener(view -> startActivity(new Intent(this, PaymentActivity.class)));
        binding.llFaq.setOnClickListener(view -> startActivity(new Intent(this, FAQActivity.class)));
        binding.llNotification.setOnClickListener(view -> startActivity(new Intent(this, NotificationsActivity.class)));
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.ivLogout.setOnClickListener(view ->
        {
            preference.logoutUser();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
//        binding.btnawesome.setOnClickListener(view -> startActivity(new Intent(this, AweSomeActivity.class)));
//        binding.btncountryphone.setOnClickListener(view -> startActivity(new Intent(this, CountryPhoneNumberActivity.class)));
//        binding.btnfaq.setOnClickListener(view -> startActivity(new Intent(this, FAQActivity.class)));
//        binding.btntermandconditions.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
//        binding.btnvenuepreference.setOnClickListener(view -> startActivity(new Intent(this, VenuePreferenceActivity.class)));
//        binding. btnfoodpreference.setOnClickListener(view -> startActivity(new Intent(this, FoodPreferenceActivity.class)));
//        binding.btnmusicpreference.setOnClickListener(view -> startActivity(new Intent(this, MusicPreferenceActivity.class)));
//        binding.btngenderbirthdate.setOnClickListener(view -> startActivity(new Intent(this, GenderBirthdateActivity.class)));
    }
}
