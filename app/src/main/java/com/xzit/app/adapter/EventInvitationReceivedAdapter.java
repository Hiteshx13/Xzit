package com.xzit.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowInvitationReceivedBinding;
import com.xzit.app.listener.OnAcceptRejectClicked;
import com.xzit.app.retrofit.model.response.eventinvitationreceived.EventInvitationReceivedData;
import com.xzit.app.utils.AppUtilsKt;

import java.util.List;

public class EventInvitationReceivedAdapter extends RecyclerView.Adapter<EventInvitationReceivedAdapter.ViewHolder> {
    private List<EventInvitationReceivedData> listData;
    boolean isVisible = false;
    private OnAcceptRejectClicked listener;
    private Context mContext;

    public EventInvitationReceivedAdapter(Context mContext, List<EventInvitationReceivedData> listData, OnAcceptRejectClicked listener) {
        this.listData = listData;
        this.listener = listener;
        this.mContext = mContext;
    }

    public void removeItem(int pos) {
        listData.remove(pos);
        notifyDataSetChanged();
    }

    public List<EventInvitationReceivedData> getListData() {
        return listData;
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

        EventInvitationReceivedData model = listData.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(model.getEventName());
        holder.binding.tvDetail.setText(model.getEventLocation());
        holder.binding.tvDate.setText(model.getInviReceivedOn());
        holder.binding.tvFrom.setText(model.getFronName());
        if (model.getEventBanners().size() > 0) {
            AppUtilsKt.loadImage(mContext, model.getEventBanners().get(0), holder.binding.ivEventImage);
        }
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
            binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listData.size() > 0) {
                        EventInvitationReceivedData item = listData.get(getAdapterPosition());
                        listener.onClick(getAdapterPosition(), true, item);
                    } else {
                        listener.onClick(getAdapterPosition(), true,null);
                    }
                }
            });

            binding.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), false, listData.get(getAdapterPosition()));
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
