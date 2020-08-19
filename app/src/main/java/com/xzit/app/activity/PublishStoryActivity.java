package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityPublishStoryBinding;

public class PublishStoryActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityPublishStoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish_story);
    }

    private void listener() {

        binding.ivBack.setOnClickListener(this);
        binding.ivAddText.setOnClickListener(this);
        binding.ivPencil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                finish();
                break;
            case R.id.ivCamera:

                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ivGallery:

                break;
        }
    }
}
