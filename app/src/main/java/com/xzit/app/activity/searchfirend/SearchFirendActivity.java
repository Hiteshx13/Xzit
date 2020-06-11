package com.xzit.app.activity.searchfirend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.activity.dashboard.DashboardActivity;
import com.xzit.app.databinding.ActivitySearchFirendBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFirendActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivitySearchFirendBinding activitySearchFirendBinding;
    private RecyclerView.Adapter mAdapter, MyAdapterCategory, MyAdapterHotspot, MyAdapterFirend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }


    private void initialization() {
        activitySearchFirendBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_firend);

        friendSearchList();
    }

    private void listener() {

        activitySearchFirendBinding.imgbackscreen.setOnClickListener(this);
    }

    private void friendSearchList() {


        activitySearchFirendBinding.rrFriend.setHasFixedSize(true);
        activitySearchFirendBinding.rrFriend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        mAdapter = new MyAdapter(input);
        activitySearchFirendBinding.rrFriend.setAdapter(mAdapter);
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
                    inflater.inflate(R.layout.friend_item_view, parent, false);

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

            ViewHolder(View v) {
                super(v);
                layout = v;


            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
        }
    }


}
