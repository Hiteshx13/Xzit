package com.xzit.app.activity.faq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityFaqBinding;
import com.xzit.app.databinding.ActivityVenuePreferenceBinding;

public class FAQActivity extends AppCompatActivity {

    private ActivityFaqBinding activityFaqBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        activityFaqBinding = DataBindingUtil.setContentView(this, R.layout.activity_faq);
        activityFaqBinding.imgbackscreen.setOnClickListener(view -> finish());
    }
}
