package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<String> values;
    OnChatClickListener listener;

    public ChatAdapter(List<String> myDataset, OnChatClickListener listener) {
        values = myDataset;
        this.listener = listener;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.friend_item_view, parent, false);

        ChatAdapter.ViewHolder vh = new ChatAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {

        final String name = values.get(position);


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        RelativeLayout rlRoot;

        ViewHolder(View v) {
            super(v);
            layout = v;
            rlRoot = layout.findViewById(R.id.rlRoot);
            rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickChat(getAdapterPosition());
                }
            });


        }
    }

    public interface OnChatClickListener {
        void onClickChat(int pos);
    }

}
