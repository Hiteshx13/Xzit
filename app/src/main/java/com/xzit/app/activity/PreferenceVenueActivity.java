package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.adapter.PreferenceVenueAdapter;
import com.xzit.app.databinding.ActivityPreferenceVenueBinding;
import com.xzit.app.retrofit.model.response.login.masterdata.VENUETYPE;

import java.util.List;

public class PreferenceVenueActivity extends BaseActivity {

    private ActivityPreferenceVenueBinding binding;
    private PreferenceVenueAdapter mAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    boolean isLongClicked = false;
    private List<VENUETYPE> listVenue;

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


        listVenue = preference.getMasterData(mContext).getResponse().getVENUE_TYPE();

        binding.rrVenuePreference.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new PreferenceVenueAdapter(mContext, R.layout.row_venuepreference, listVenue);
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
