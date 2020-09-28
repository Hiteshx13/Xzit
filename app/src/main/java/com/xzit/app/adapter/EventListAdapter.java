package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private List<String> values;
    OnChatClickListener listener;
    boolean isVisible = false;

    public EventListAdapter(List<String> myDataset, OnChatClickListener listener) {
        values = myDataset;
        this.listener = listener;
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_discover, parent, false);

        EventListAdapter.ViewHolder vh = new EventListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ViewHolder holder, final int position) {

        final String name = values.get(position);


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        //        RelativeLayout rlRoot;
        AppCompatImageView ivMenu;
        LinearLayout llMenu;


        ViewHolder(View v) {
            super(v);
            layout = v;
//            rlRoot = layout.findViewById(R.id.rlRoot);
            ivMenu = layout.findViewById(R.id.ivMenu);
            llMenu = layout.findViewById(R.id.llMenu);
//            rlRoot = layout.findViewById(R.id.rlRoot);
//            rlRoot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onClickChat(getAdapterPosition());
//                }
//            });

            ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isVisible) {
                        llMenu.setVisibility(View.GONE);
                    } else {
                        llMenu.setVisibility(View.VISIBLE);
                    }
                    isVisible = !isVisible;
                    //listener.onClickChat(getAdapterPosition());
                }
            });
        }
    }

    public interface OnChatClickListener {
        void onClickChat(int pos);
    }

}
