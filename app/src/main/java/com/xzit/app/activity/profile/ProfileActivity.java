package com.xzit.app.activity.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityProfileBinding activityProfileBinding;
    private RecyclerView.Adapter mAdapter, MyAdapterVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }


    private void initialization() {
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        music();

        venue();
    }

    private void listener() {

        activityProfileBinding.imgbackscreen.setOnClickListener(this);
    }

    private void music() {


        activityProfileBinding.rrmusic.setHasFixedSize(true);
        activityProfileBinding.rrmusic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        mAdapter = new MyAdapter(input);
        activityProfileBinding.rrmusic.setAdapter(mAdapter);
    }

    private void venue() {


        activityProfileBinding.rrvenue.setHasFixedSize(true);
        activityProfileBinding.rrvenue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        MyAdapterVenue = new MyAdapterVenue(input);
        activityProfileBinding.rrvenue.setAdapter(MyAdapterVenue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> values;

        public MyAdapter(List<String> myDataset) {
            values = myDataset;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.music_row_item, parent, false);

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

    public class MyAdapterVenue extends RecyclerView.Adapter<MyAdapterVenue.ViewHolder> {
        private List<String> values;

        public MyAdapterVenue(List<String> myDataset) {
            values = myDataset;
        }

        @Override
        public MyAdapterVenue.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.venue_item_view, parent, false);

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
