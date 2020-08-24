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
import com.xzit.app.listener.MusicPrefListener;
import com.xzit.app.retrofit.model.response.masterdata.MUSICTYPE;
import com.xzit.app.utils.ImageUtils;

import java.util.List;

public class PreferenceMusicAdapter extends RecyclerView.Adapter<PreferenceMusicAdapter.ViewHolder> {
    private List<MUSICTYPE> listMusic;
    int mLayout;
    private Context mContext;
    private MusicPrefListener listener;

    public PreferenceMusicAdapter(Context mContext, int mLayout, List<MUSICTYPE> listMusic) {
        this.listMusic = listMusic;
        this.mLayout = mLayout;
        this.listener = listener;
        this.mContext = mContext;

    }

    public String getSelection() {
        String selection = "";
        for (int i = 0; i < listMusic.size(); i++) {
            if (listMusic.get(i).isSelected()) {
                selection = selection + listMusic.get(i).getVALUE() + ",";
            }
        }
        if(!selection.isEmpty()){
            selection = selection.substring(0, selection.length() - 1);
        }

        return selection;
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
        holder.tvPrefName.setText(model.getVALUE());
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
        AppCompatImageView  ivPreference, ivOverlay, ivOverlayBorder;
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