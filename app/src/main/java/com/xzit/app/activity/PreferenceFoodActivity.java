package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.adapter.PreferenceFoodAdapter;
import com.xzit.app.databinding.ActivityPreferenceFoodBinding;
import com.xzit.app.retrofit.model.response.login.masterdata.FOODTYPE;

import java.util.List;

public class PreferenceFoodActivity extends BaseActivity {

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ActivityPreferenceFoodBinding binding;
    private RecyclerView.Adapter mAdapter;
    private List<FOODTYPE> listFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference_food);
        binding.rrFoodPreference.setHasFixedSize(true);
        //  activityVenuePreferenceBinding.rrVenuePreference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);


        binding.rrFoodPreference.setLayoutManager(recyclerViewLayoutManager);

        listFood = preference.getMasterData(mContext).getResponse().getFOOD_TYPE();
        mAdapter = new PreferenceFoodAdapter(mContext, R.layout.row_foodpreference,listFood );

        binding.rrFoodPreference.setAdapter(mAdapter);
        binding.rrFoodPreference.setAdapter(mAdapter);

        binding.imgbackscreen.setOnClickListener(view -> finish());
    }

    private void listener() {
        binding.imgbackscreen.setOnClickListener(view -> finish());
        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, AweSomeActivity.class);
            startActivity(intent);
        });
    }

    public class MyAdapterFoodPreference extends RecyclerView.Adapter<MyAdapterFoodPreference.ViewHolder> {
        private List<String> values;
        private FoodPrefListener listener;

        public MyAdapterFoodPreference(List<String> myDataset, FoodPrefListener listener) {
            values = myDataset;
            this.listener = listener;
        }

        @Override
        public MyAdapterFoodPreference.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.row_foodpreference, parent, false);

            MyAdapterFoodPreference.ViewHolder vh = new MyAdapterFoodPreference.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyAdapterFoodPreference.ViewHolder holder, final int position) {

            final String name = values.get(position);


        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View layout;
            RelativeLayout rlRoot;

            public ViewHolder(View v) {
                super(v);
                layout = v;

                rlRoot = layout.findViewById(R.id.rlRoot);
                rlRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick();
                    }
                });

            }
        }

    }

    interface FoodPrefListener {
        void onClick();
    }
}
