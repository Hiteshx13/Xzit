package com.xzit.app.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandrlab.widgets.MetalRecyclerViewPager;
import com.xzit.app.R;
import com.xzit.app.activity.changeemail.ChangeEmailActivity;
import com.xzit.app.activity.changepassword.ChangePasswordActivity;
import com.xzit.app.activity.createevent.CreatEventActivity;
import com.xzit.app.activity.profile.ProfileActivity;
import com.xzit.app.activity.searchfirend.SearchFirendActivity;
import com.xzit.app.databinding.ActivityDashboardBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityDashboardBinding activityDashboardBinding;
    private RecyclerView.Adapter mAdapter, MyAdapterCategory, MyAdapterHotspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();

        listener();


    }

    private void initialization() {
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        activityDashboardBinding.rrMystory.setHasFixedSize(true);
        activityDashboardBinding.rrMystory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        mAdapter = new MyAdapter(input);
        activityDashboardBinding.rrMystory.setAdapter(mAdapter);


        Category();

        hotspot();

        showing();

    }

    private void showing() {
        DisplayMetrics metrics = getDisplayMetrics();
        List<String> metalList = Arrays.asList("\\m/", "\\m/", "\\m/");
        FullMetalAdapter fullMetalAdapter = new FullMetalAdapter(metrics, metalList);

        MetalRecyclerViewPager viewPager = (MetalRecyclerViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(fullMetalAdapter);
    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }

    private void Category() {

        activityDashboardBinding.rrCategories.setHasFixedSize(true);
        activityDashboardBinding.rrCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        MyAdapterCategory = new MyAdapterCategory(input);
        activityDashboardBinding.rrCategories.setAdapter(MyAdapterCategory);
    }

    private void hotspot() {
        activityDashboardBinding.rrHotSport.setHasFixedSize(true);
        activityDashboardBinding.rrHotSport.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }
        MyAdapterHotspot = new MyAdapterHotspot(input);
        activityDashboardBinding.rrHotSport.setAdapter(MyAdapterHotspot);
    }

    private void listener() {

        activityDashboardBinding.imghome.setOnClickListener(this);
        activityDashboardBinding.imgmiscellaneous.setOnClickListener(this);
        activityDashboardBinding.imgChat.setOnClickListener(this);
        activityDashboardBinding.imgplan.setOnClickListener(this);
        activityDashboardBinding.imgCamera.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgbackscreen:
                finish();
                break;
            case R.id.imgmiscellaneous:
                Intent intentChangePassword = new Intent(this, ChangePasswordActivity.class);
                startActivity(intentChangePassword);
                break;
            case R.id.imgChat:
                Intent intentChangeEmail = new Intent(this, ChangeEmailActivity.class);
                startActivity(intentChangeEmail);
                break;
            case R.id.imgplan:
                Intent intentChangeSearchfriend = new Intent(this, SearchFirendActivity.class);
                startActivity(intentChangeSearchfriend);
                break;
            case R.id.imghome:
                Intent intentCreateEvent = new Intent(this, CreatEventActivity.class);
                startActivity(intentCreateEvent);
                break;
            case R.id.img_camera:
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                startActivity(intentProfile);
                break;


        }
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
                    inflater.inflate(R.layout.category_item_view, parent, false);

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
            public ImageView imgViewStory;

            public ViewHolder(View v) {
                super(v);
                layout = v;
                imgViewStory = v.findViewById(R.id.imgAdd);

            }
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
                    inflater.inflate(R.layout.add_story_item_view, parent, false);

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

    public class MyAdapterHotspot extends RecyclerView.Adapter<MyAdapterHotspot.ViewHolder> {
        private List<String> values;

        public MyAdapterHotspot(List<String> myDataset) {
            values = myDataset;
        }

        @Override
        public MyAdapterHotspot.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.hotspot_item_view, parent, false);

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
            public ImageView imgViewStory;

            public ViewHolder(View v) {
                super(v);
                layout = v;
                imgViewStory = v.findViewById(R.id.imgAdd);

            }
        }

    }


}
