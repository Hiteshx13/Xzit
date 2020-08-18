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
import com.xzit.app.databinding.ActivityPreferenceVenueBinding;

import java.util.ArrayList;
import java.util.List;

public class PreferenceVenueActivity extends AppCompatActivity {

    private ActivityPreferenceVenueBinding binding;
    private PreferenceAdapter mAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    boolean isLongClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference_venue);
        binding.rrVenuePreference.setHasFixedSize(true);
        //  activityVenuePreferenceBinding.rrVenuePreference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        List<VenueModel> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add(new VenueModel("Test" + i, false));
        }
        binding.rrVenuePreference.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new PreferenceAdapter(R.layout.row_venuepreference, input);
        binding.rrVenuePreference.setAdapter(mAdapter);

        binding.imgbackscreen.setOnClickListener(view -> finish());
    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, PreferenceFoodActivity.class);
            startActivity(intent);
        });
    }

    interface VenuePrefListener {
        void onClick();
    }
}
