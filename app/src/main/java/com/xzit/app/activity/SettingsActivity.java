package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.databinding.ActivitySettingsBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.DashboardRepository;
import com.xzit.app.retrofit.model.response.dashboard.FCMTokenResponse;
import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.HashMap;

import static com.xzit.app.activity.XzitApp.getLoginUserData;
import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS2;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    private DashboardRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository=new DashboardRepository();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        binding.llChangePass.setOnClickListener(view -> startActivity(new Intent(this, ChangePasswordActivity.class)));
        binding.llTnc.setOnClickListener(view -> startActivity(new Intent(this, TermandConditionsActivity.class)));
        binding.llPrivacy.setOnClickListener(view -> startActivity(new Intent(this, PrivacyPolicyActivity.class)));
        binding.llPayment.setOnClickListener(view -> startActivity(new Intent(this, PaymentActivity.class)));
        binding.llFaq.setOnClickListener(view -> startActivity(new Intent(this, FAQActivity.class)));
        binding.llNotification.setOnClickListener(view -> startActivity(new Intent(this, NotificationsActivity.class)));
        binding.imgbackscreen.setOnClickListener(view -> finish());

        repository.getFcmTokenResponse().observe(this, new Observer<FCMTokenResponse>() {
            @Override
            public void onChanged(FCMTokenResponse response) {
                if (response != null && response.getStatus() == RESP_API_SUCCESS || response.getStatus() == RESP_API_SUCCESS2) {
                    preference.logoutUser();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), true, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });

        binding.ivLogout.setOnClickListener(view ->
        {
            LoginData userdata = getLoginUserData();
            HashMap<String, String> map = new HashMap<>();
            map.put("postData[requestCase]", "fcmdeviceTokenUpdate");
            map.put("postData[userId]", userdata.getUserId());
            map.put("postData[fcmToken]", "zzzz");
            map.put("postData[deviceType]", "ANDROID");
            repository.callApi(mContext, map);

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
