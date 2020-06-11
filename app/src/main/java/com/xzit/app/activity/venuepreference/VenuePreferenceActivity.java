package com.xzit.app.activity.venuepreference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xzit.app.R;
import com.xzit.app.activity.dashboard.DashboardActivity;
import com.xzit.app.databinding.ActivityDashboardBinding;
import com.xzit.app.databinding.ActivityVenuePreferenceBinding;

import java.util.ArrayList;
import java.util.List;

public class VenuePreferenceActivity extends AppCompatActivity {

    private ActivityVenuePreferenceBinding activityVenuePreferenceBinding;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();
    }

    private void initialization() {
        activityVenuePreferenceBinding = DataBindingUtil.setContentView(this, R.layout.activity_venue_preference);
        activityVenuePreferenceBinding.rrVenuePreference.setHasFixedSize(true);
      //  activityVenuePreferenceBinding.rrVenuePreference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        activityVenuePreferenceBinding.rrVenuePreference.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new MyAdapterCategory(input);
        activityVenuePreferenceBinding.rrVenuePreference.setAdapter(mAdapter);

        activityVenuePreferenceBinding.imgbackscreen.setOnClickListener(view -> finish());
    }

    private void listener() {
        activityVenuePreferenceBinding.imgbackscreen.setOnClickListener(view -> finish());
    }
    public class MyAdapterCategory extends RecyclerView.Adapter<MyAdapterCategory.ViewHolder> {
        private List<String> values;

        public MyAdapterCategory(List<String> myDataset) {
            values = myDataset;
        }

        @Override
        public MyAdapterCategory.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                 int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.venuepreference_item_row, parent, false);

           ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final String name = values.get(position);


        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View layout;
            public ViewHolder(View v) {
                super(v);
                layout = v;


            }
        }

    }
}
