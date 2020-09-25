package com.xzit.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.retrofit.model.response.masterdata.Subtype;

import java.util.List;

public class PreferenceFoodAdapter extends RecyclerView.Adapter<PreferenceFoodAdapter.ViewHolder> {
    private List<Subtype> listFood;
    int mLayout;
    private Context mContext;

    public PreferenceFoodAdapter(Context mContext, int mLayout, List<Subtype> listFood) {
        this.listFood = listFood;
        this.mLayout = mLayout;
        this.mContext = mContext;
    }

    public String getSelection() {
        String selection = "";
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).isSelected) {
                selection = selection + listFood.get(i).getDisplayValue() + ",";
            }
        }
        if (!selection.isEmpty()) {
            selection = selection.substring(0, selection.length() - 1);
        }
        return selection;
    }

    @Override
    public PreferenceFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(mLayout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreferenceFoodAdapter.ViewHolder holder, final int position) {

        int pos = position;
        final Subtype model = listFood.get(position);
        holder.tvPrefName.setText(model.getDisplayValue());
//        if (!model.getURL().isEmpty()) {
//            ImageUtils imageUtils = new ImageUtils();
//            imageUtils.loadImage(mContext, model.getURL(), holder.ivOverlay);
//        }


        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listFood.get(pos).isSelected) {
                    listFood.get(pos).isSelected = false;
                    holder.ivOverlay.setVisibility(View.GONE);
                    holder.ivOverlayBorder.setVisibility(View.GONE);
                } else {
                    listFood.get(pos).isSelected = true;
                    holder.ivOverlay.setVisibility(View.VISIBLE);
                    holder.ivOverlayBorder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        RelativeLayout rlRoot;
        AppCompatImageView ivPreference, ivOverlay, ivOverlayBorder;
        AppCompatTextView tvPrefName;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            rlRoot = layout.findViewById(R.id.rlRoot);
            tvPrefName = layout.findViewById(R.id.tvPrefName);
            ivPreference = layout.findViewById(R.id.ivPreference);
            ivOverlay = layout.findViewById(R.id.ivOverlay);
            ivOverlayBorder = layout.findViewById(R.id.ivOverlayBorder);
        }
    }
}