package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.VenueModel;
import com.xzit.app.adapter.PreferenceAdapter;
import com.xzit.app.databinding.ActivityPreferenceMusicBinding;

import java.util.ArrayList;
import java.util.List;

public class PreferenceMusicActivity extends AppCompatActivity {

    private ActivityPreferenceMusicBinding binding;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference_music);
        binding.rrVenuePreference.setHasFixedSize(true);
        //  activityMusicPreferenceBinding.rrVenuePreference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        List<VenueModel> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add(new VenueModel("Test" + i, false));
        }
        binding.rrVenuePreference.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new PreferenceAdapter(R.layout.row_musicprefernce, input);

        binding.rrVenuePreference.setAdapter(mAdapter);

    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, PreferenceVenueActivity.class);
            startActivity(intent);
        });
    }


    interface MusicPrefListener {
        void onClick();
    }
}
