package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityAddStoryBinding;

public class AddStoryActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityAddStoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_story);
    }

    private void listener() {

        binding.imgbackscreen.setOnClickListener(this);
        binding.ivCamera.setOnClickListener(this);
        binding.ivGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.ivCamera:

                Intent intent = new Intent(this, PublishStoryActivity.class);
                startActivity(intent);
                break;
            case R.id.ivGallery:

                break;
        }
    }
}
