package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.adapter.PreferenceFoodAdapter;
import com.xzit.app.databinding.ActivityPreferenceFoodBinding;
import com.xzit.app.listener.OnDialogClickListener;
import com.xzit.app.repository.PreferenceRepository;
import com.xzit.app.retrofit.model.response.login.LoginData;
import com.xzit.app.retrofit.model.response.login.LoginResponse;
import com.xzit.app.retrofit.model.response.masterdata.Subtype;
import com.xzit.app.retrofit.model.response.preference.PreferenceResponse;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.DialogUtilsKt;

import java.util.HashMap;
import java.util.List;

import static com.xzit.app.activity.XzitApp.getAuthToken;
import static com.xzit.app.activity.XzitApp.getLoginUserData;
import static com.xzit.app.activity.XzitApp.preference;
import static com.xzit.app.utils.AppUtilsKt.RESP_API_SUCCESS;

public class PreferenceFoodActivity extends BaseActivity {

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ActivityPreferenceFoodBinding binding;
    private PreferenceFoodAdapter mAdapter;
    private List<Subtype> listFood;
    private PreferenceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        listener();
        setObserver();
    }

    private void initialization() {
        repository = new PreferenceRepository();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference_food);
        binding.rrFoodPreference.setHasFixedSize(true);
        //  activityVenuePreferenceBinding.rrVenuePreference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);


        binding.rrFoodPreference.setLayoutManager(recyclerViewLayoutManager);

        listFood = preference.getMasterData(mContext).getResponse().get(2).getSubtype();
        mAdapter = new PreferenceFoodAdapter(mContext, R.layout.row_foodpreference, listFood);

        binding.rrFoodPreference.setAdapter(mAdapter);
        binding.rrFoodPreference.setAdapter(mAdapter);

        binding.imgbackscreen.setOnClickListener(view -> finish());
    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnNext.setOnClickListener(view -> {
            String selection = mAdapter.getSelection();
            if (selection.isEmpty()) {
                AppUtilsKt.showToast(mContext, getString(R.string.please_select_at_least_one_preference));
            } else {
                callPreference();
            }
        });
    }

    private void setObserver() {
        repository.getResponseData().observe(this, new Observer<PreferenceResponse>() {
            @Override
            public void onChanged(PreferenceResponse response) {

                if (response != null && response.getStatus() == RESP_API_SUCCESS) {
                    Intent intent = new Intent(mContext, AweSomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    DialogUtilsKt.showMessageDialog(mContext, response.getMessage(), true, new OnDialogClickListener() {
                        @Override
                        public void onButtonClicked(Boolean value) {

                        }
                    });
                }
            }
        });
    }

    void callPreference() {
        LoginData userdata = getLoginUserData();

        HashMap<String, String> map = new HashMap<>();
        map.put("postData[requestCase]", "addPreferenceToUser");
        map.put("postData[userId]", userdata.getUserId());
        map.put("postData[clientId]", userdata.getClientId());
        map.put("postData[prefType]", "FOOD");
        map.put("postData[prefArr]", mAdapter.getSelection());
        repository.callPreference(mContext, map, getAuthToken());

    }
}
