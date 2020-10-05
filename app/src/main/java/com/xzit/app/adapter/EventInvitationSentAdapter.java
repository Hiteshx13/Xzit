package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowInvitationSentBinding;
import com.xzit.app.retrofit.model.response.eventdata.Ongoing;
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationSentData;

import java.util.List;

public class EventInvitationSentAdapter extends RecyclerView.Adapter<EventInvitationSentAdapter.ViewHolder> {
    private List<EventInvitationSentData> listData;
    boolean isVisible = false;

    public EventInvitationSentAdapter(List<EventInvitationSentData> listData) {
        this.listData = listData;
    }

    @Override
    public EventInvitationSentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        RowInvitationSentBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_invitation_sent, parent, false);
        EventInvitationSentAdapter.ViewHolder vh = new EventInvitationSentAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(EventInvitationSentAdapter.ViewHolder holder, final int position) {

        EventInvitationSentData model = listData.get(holder.getAdapterPosition());
        holder.binding.tvEventName.setText(model.getEventName());
        holder.binding.tvAddress.setText(model.getEventLocation());
        holder.binding.tvDate.setText(model.getInviReceivedOn());
        holder.binding.tvGuestList.setSelected(true);

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

        RowInvitationSentBinding binding;

        ViewHolder(RowInvitationSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
