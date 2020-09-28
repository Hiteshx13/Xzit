package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityCreatEventBinding;

import java.util.Calendar;
import java.util.Date;

import static com.wisnu.datetimerangepickerandroid.CalendarPickerView.SelectionMode.RANGE;

public class CreatEventActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityCreatEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();

    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creat_event);
        //CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

//        binding.calendarView.init(today, nextYear.getTime())
//                .inMode(RANGE)
//                .withSelectedDate(today);

    }

    private void listener() {

        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                Intent intentSettings = new Intent(this, AddFriendsActivity.class);
                startActivity(intentSettings);
                break;
        }
    }

}
