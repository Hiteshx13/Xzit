package com.xzit.app.activity.termsandconditions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityTermandConditionsBinding;
import com.xzit.app.databinding.ActivityVenuePreferenceBinding;

public class TermandConditionsActivity extends AppCompatActivity {

    private ActivityTermandConditionsBinding activityTermandConditionsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTermandConditionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_termand_conditions);

        activityTermandConditionsBinding.imgbackscreen.setOnClickListener(view -> finish());
    }
}
