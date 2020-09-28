package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowFriendRequestBinding;
import com.xzit.app.listener.OnFriendRequestClickListener;
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestData;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
    private List<FriendRequestData> values;
    OnFriendRequestClickListener listener;

    public FriendRequestAdapter(List<FriendRequestData> myDataset, OnFriendRequestClickListener listener) {
        values = new ArrayList<>();
        values.addAll(myDataset);
        this.listener = listener;
    }

    @Override
    public FriendRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        RowFriendRequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_friend_request, parent, false);
        FriendRequestAdapter.ViewHolder vh = new FriendRequestAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendRequestAdapter.ViewHolder holder, final int position) {

        final String name = values.get(position).getReceivedRequestFromName();
        holder.binding.tvName.setText(name);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void removeItemAt(int pos) {
        values.remove(pos);
        notifyDataSetChanged();
    }

    public List<FriendRequestData> getList() {
        return values;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RowFriendRequestBinding binding;

        ViewHolder(RowFriendRequestBinding v) {
            super(v.getRoot());
            binding = v;
            binding.ivAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), true);
                }
            });

            binding.ivReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), false);
                }
            });


        }
    }

    public interface OnChatClickListener {
        void onClickChat(int pos);
    }

}
