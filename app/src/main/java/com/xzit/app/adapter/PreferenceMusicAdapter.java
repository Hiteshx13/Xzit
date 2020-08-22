package com.xzit.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.retrofit.model.response.login.masterdata.MUSICTYPE;
import com.xzit.app.utils.ImageUtils;

import java.util.List;

public class PreferenceMusicAdapter extends RecyclerView.Adapter<PreferenceMusicAdapter.ViewHolder> {
    private List<MUSICTYPE> listMusic;
    int mLayout;
    private Context mContext;

    public PreferenceMusicAdapter(Context mContext, int mLayout, List<MUSICTYPE> listMusic) {
        this.listMusic = listMusic;
        this.mLayout = mLayout;
        this.mContext = mContext;

    }

    @Override
    public PreferenceMusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(mLayout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreferenceMusicAdapter.ViewHolder holder, final int position) {

        final MUSICTYPE model = listMusic.get(position);
        int pos = position;

        if (!model.getURL().isEmpty()) {
            ImageUtils imageUtils = new ImageUtils();
            imageUtils.loadImage(mContext, model.getURL(), holder.ivOverlay);
        }
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listMusic.get(pos).isSelected()) {
                    listMusic.get(pos).setSelected(false);
                    holder.ivOverlay.setVisibility(View.GONE);
                    holder.ivOverlayBorder.setVisibility(View.GONE);
                } else {
                    listMusic.get(pos).setSelected(true);
                    holder.ivOverlay.setVisibility(View.VISIBLE);
                    holder.ivOverlayBorder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        RelativeLayout rlRoot;
        AppCompatImageView ivPreference, ivOverlay, ivOverlayBorder;

        public ViewHolder(View v) {
            super(v);

            layout = v;
            rlRoot = layout.findViewById(R.id.rlRoot);
            ivPreference = layout.findViewById(R.id.ivPreference);
            ivOverlay = layout.findViewById(R.id.ivOverlay);
            ivOverlayBorder = layout.findViewById(R.id.ivOverlayBorder);
        }
    }

}