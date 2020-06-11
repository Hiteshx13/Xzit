package com.xzit.app.activity.createevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityChangeEmailBinding;
import com.xzit.app.databinding.ActivityCreatEventBinding;

public class CreatEventActivity extends AppCompatActivity implements View.OnClickListener {



    private ActivityCreatEventBinding activityCreatEventBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();



    }

    private void initialization() {
        activityCreatEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_creat_event);
    }

    private void listener() {

        activityCreatEventBinding.imgbackscreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {

            case R.id.imgbackscreen:
                finish();
                break;
        }
    }

}
