package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private List<String> values;

    public ProfileAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.music_row_item, parent, false);

        ProfileAdapter.ViewHolder vh = new ProfileAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ViewHolder holder, final int position) {

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