package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowDiscoverBinding;
import com.xzit.app.retrofit.model.response.eventdata.Ongoing;

import java.util.List;

public class OngoingEventAdapter extends RecyclerView.Adapter<OngoingEventAdapter.ViewHolder> {
    private List<Ongoing> listData;
    boolean isVisible = false;

    public OngoingEventAdapter(List<Ongoing> listData) {
        this.listData = listData;
    }

    @Override
    public OngoingEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        RowDiscoverBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_discover, parent, false);
        OngoingEventAdapter.ViewHolder vh = new OngoingEventAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(OngoingEventAdapter.ViewHolder holder, final int position) {

        Ongoing model = listData.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(model.getEventTitle());
        holder.binding.tvSubTitle.setText(model.getEventTitle());
        holder.binding.tvDescription.setText(model.getEventDetail());

        if (holder.getAdapterPosition() == listData.size() - 1) {
            holder.binding.viewSpace.setVisibility(View.VISIBLE);
        } else {

            holder.binding.viewSpace.setVisibility(View.GONE);
        }
//        final String name = listData.get(position).;


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowDiscoverBinding binding;

        ViewHolder(RowDiscoverBinding binding) {
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

            binding.ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isVisible) {
                        binding.llMenu.setVisibility(View.GONE);
                    } else {
                        binding.llMenu.setVisibility(View.VISIBLE);
                    }
                    isVisible = !isVisible;
                    //listener.onClickChat(getAdapterPosition());
                }
            });
        }
    }


}
