package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.adapter.PreferenceMusicAdapter;
import com.xzit.app.databinding.ActivityPreferenceMusicBinding;
import com.xzit.app.retrofit.model.response.login.masterdata.MUSICTYPE;

import java.util.List;

public class PreferenceMusicActivity extends BaseActivity {

    private ActivityPreferenceMusicBinding binding;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private List<MUSICTYPE> listMusic;

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

        listMusic = preference.getMasterData(mContext).getResponse().getMUSIC_TYPE();
        binding.rrVenuePreference.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new PreferenceMusicAdapter(mContext, R.layout.row_musicprefernce, listMusic);

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
