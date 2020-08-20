package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;

import java.util.List;

public class RestaurentAdapter extends RecyclerView.Adapter<RestaurentAdapter.ViewHolder> {
    private List<String> values;

    public RestaurentAdapter(List<String> myDataset) {
        values = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.hotspot_item_view, parent, false);

        RestaurentAdapter.ViewHolder vh = new RestaurentAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
