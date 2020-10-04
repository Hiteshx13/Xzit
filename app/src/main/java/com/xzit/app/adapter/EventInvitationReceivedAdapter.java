package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowInvitationReceivedBinding;
import com.xzit.app.listener.OnAcceptRejectClicked;

import java.util.List;

public class EventInvitationReceivedAdapter extends RecyclerView.Adapter<EventInvitationReceivedAdapter.ViewHolder> {
    private List<Object> listData;
    boolean isVisible = false;
    OnAcceptRejectClicked listener;

    public EventInvitationReceivedAdapter(List<Object> listData, OnAcceptRejectClicked listener) {
        this.listData = listData;
        this.listener = listener;
    }

    @Override
    public EventInvitationReceivedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        RowInvitationReceivedBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_invitation_received, parent, false);
        EventInvitationReceivedAdapter.ViewHolder vh = new EventInvitationReceivedAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventInvitationReceivedAdapter.ViewHolder holder, final int position) {

        //Ongoing model = listData.get(holder.getAdapterPosition());
//        holder.binding.tvName.setText(model.getEventTitle());
//        holder.binding.tvSubTitle.setText(model.getEventTitle());
//        holder.binding.tvDescription.setText(model.getEventDetail());
//
//        if (holder.getAdapterPosition() == listData.size() - 1) {
//            holder.binding.viewSpace.setVisibility(View.VISIBLE);
//        } else {
//
//            holder.binding.viewSpace.setVisibility(View.GONE);
//        }
//        final String name = listData.get(position).;


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowInvitationReceivedBinding binding;

        ViewHolder(RowInvitationReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.icAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listData.remove(getAdapterPosition());
                    listener.onClick(getAdapterPosition(), listData.size() == 0);
                }
            });

            binding.ivReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listData.remove(getAdapterPosition());
                    listener.onClick(getAdapterPosition(), listData.size() == 0);
                }
            });
//            ivMenu = layout.findViewById(R.id.ivMenu);
//            llMenu = layout.findViewById(R.id.llMenu);
//            rlRoot = layout.findViewById(R.id.rlRoot);
//            rlRoot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onClickChat(getAdapterPosition());
//                }
//            });

//            binding.ivMenu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (isVisible) {
//                        binding.llMenu.setVisibility(View.GONE);
//                    } else {
//                        binding.llMenu.setVisibility(View.VISIBLE);
//                    }
//                    isVisible = !isVisible;
//                    //listener.onClickChat(getAdapterPosition());
//                }
//            });
        }
    }


}
