package com.xzit.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowFriendListingBinding;
import com.xzit.app.listener.OnAddRemoveFriendListener;
import com.xzit.app.retrofit.model.response.friendlist.FriendListData;
import com.xzit.app.utils.AppUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<FriendListData> values;
    private OnAddRemoveFriendListener listener;
    private Activity mActivity;
    private ArrayList<String> listSelection;


    public FriendListAdapter(Activity mActivity, List<FriendListData> myDataset, OnAddRemoveFriendListener listener) {
        values = myDataset;
        listSelection = new ArrayList<>();
        this.mActivity = mActivity;
        this.listener = listener;
    }


    public String getSelectedUsers() {
        if (listSelection.size() == 0) {
            AppUtilsKt.showToast(mActivity, "Please select any user");
            return null;
        }else{
            return listSelection.toString();
        }
    }

    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());

        RowFriendListingBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_friend_listing, parent, false);
        FriendListAdapter.ViewHolder holder = new FriendListAdapter.ViewHolder(binding);
        return holder;
    }

    public void setRequestStatus(int pos) {

        ///model.getStatus().equals(AppUtilsKt.STATUS_PENDING)
    }

    @Override
    public void onBindViewHolder(FriendListAdapter.ViewHolder holder, final int position) {

        final FriendListData model = values.get(holder.getAdapterPosition());

        holder.binding.tvName.setText(model.getFullname());
        holder.binding.ivAddRemove.setSelected(model.isAdded());

//        if (!model.getProfilePic().isEmpty()) {
//            new ImageUtils().loadImage(mActivity, model.getProfilePic(), holder.binding.ivProfile);
//        }
//
//        if (!model.getUsername().isEmpty()) {
//            holder.binding.tvName.setText(model.getUsername());
//        }
//        if (!model.getBusinessname().isEmpty()) {
//            holder.binding.tvName.setText(model.getBusinessname());
//        }
//
//
//        Log.d("Name:" + model.getBusinessname(), "__" + model.getProfilePic());
//        if (model.getStatus().equals(AppUtilsKt.STATUS_PENDING)) {
//            holder.binding.tvAddFriendUnfriend.setClickable(false);
//            holder.binding.tvAddFriendUnfriend.setSelected(true);
//            holder.binding.tvAddFriendUnfriend.setText(model.getStatus());
//        } else if (model.getStatus().equals(AppUtilsKt.STATUS_ACCEPT)) {
//            holder.binding.tvAddFriendUnfriend.setText(mActivity.getString(R.string.friends));
//            holder.binding.tvAddFriendUnfriend.setSelected(true);
//        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        RowFriendListingBinding binding;

        ViewHolder(RowFriendListingBinding view) {
            super(view.getRoot());
            binding = view;

            binding.ivAddRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    FriendListData model = values.get(pos);
                    if (model.isAdded()) {
                        model.setAdded(false);
                        listSelection.remove(model.getUserId());
                    } else {
                        model.setAdded(true);
                        listSelection.add(model.getUserId());
                    }
                    values.remove(pos);
                    values.add(pos, model);
                    notifyItemChanged(pos);
                }
            });
        }
    }
}
