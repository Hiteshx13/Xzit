package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.VenueModel;

import java.util.List;

public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceAdapter.ViewHolder> {
    private List<VenueModel> listVenue;
    int mLayout;

    public PreferenceAdapter(int mLayout, List<VenueModel> listVenue) {
        this.listVenue = listVenue;
        this.mLayout = mLayout;
    }

    @Override
    public PreferenceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(mLayout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreferenceAdapter.ViewHolder holder, final int position) {

        final VenueModel model = listVenue.get(position);
        int pos = position;
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listVenue.get(pos).isSelected) {
                    listVenue.get(pos).isSelected = false;
                    holder.ivOverlay.setVisibility(View.GONE);
                    holder.ivOverlayBorder.setVisibility(View.GONE);
                } else {
                    listVenue.get(pos).isSelected = true;
                    holder.ivOverlay.setVisibility(View.VISIBLE);
                    holder.ivOverlayBorder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVenue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        RelativeLayout rlRoot;
        ImageView ivOverlay, ivOverlayBorder;

        public ViewHolder(View v) {
            super(v);

            layout = v;
            rlRoot = layout.findViewById(R.id.rlRoot);
            ivOverlay = layout.findViewById(R.id.ivOverlay);
            ivOverlayBorder = layout.findViewById(R.id.ivOverlayBorder);


        }
    }

}