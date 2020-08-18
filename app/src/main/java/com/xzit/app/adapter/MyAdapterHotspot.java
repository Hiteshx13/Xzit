package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;

import java.util.List;

public class MyAdapterHotspot extends RecyclerView.Adapter<MyAdapterHotspot.ViewHolder> {
    private List<String> values;

    public MyAdapterHotspot(List<String> myDataset) {
        values = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.hotspot_item_view, parent, false);

        MyAdapterHotspot.ViewHolder vh = new MyAdapterHotspot.ViewHolder(v);
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
