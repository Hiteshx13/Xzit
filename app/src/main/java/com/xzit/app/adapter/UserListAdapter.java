package com.xzit.app.adapter;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.databinding.RowUserListingBinding;
import com.xzit.app.listener.OnButtonClickListener;
import com.xzit.app.listener.OnReportDialogClickListener;
import com.xzit.app.listener.OnViewClickListener;
import com.xzit.app.retrofit.model.response.userlisting.UserListingData;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.ImageUtils;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<UserListingData> values;
    private OnButtonClickListener listener;
    private Activity mActivity;
    private OnReportDialogClickListener reportDialogClickListener;
    private OnViewClickListener viewClickListener;

    public UserListAdapter(Activity mActivity, List<UserListingData> myDataset, OnViewClickListener viewClickListener, OnButtonClickListener listener, OnReportDialogClickListener reportDialogClickListener) {
        values = myDataset;
        this.mActivity = mActivity;
        this.viewClickListener = viewClickListener;
        this.listener = listener;
        this.reportDialogClickListener = reportDialogClickListener;
    }


    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
//        View v =
//                inflater.inflate(R.layout.row_user_listing, parent, false);
        RowUserListingBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_user_listing, parent, false);
        UserListAdapter.ViewHolder holder = new UserListAdapter.ViewHolder(binding);
        return holder;
        /* UserListAdapter.ViewHolder holder;
        if (parent.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            RowUserListingBinding binding =
                    DataBindingUtil.inflate(inflater, R.layout.row_user_listing, parent, false);

            holder = new UserListAdapter.ViewHolder(binding);
            parent.setTag(holder);
        } else {
            holder = (UserListAdapter.ViewHolder) parent.getTag();
        }*/
    }

    public void setRequestStatus(int pos) {
        UserListingData model = values.get(pos);
        if (model.getStatus().equals(AppUtilsKt.STATUS_ACCEPT)) {
            model.setStatus("");
        } else {
            model.setStatus(AppUtilsKt.STATUS_PENDING);
        }

        values.remove(pos);
        values.add(pos, model);
        notifyItemChanged(pos);
        ///model.getStatus().equals(AppUtilsKt.STATUS_PENDING)
    }

//    @Override
//    public long getItemId(int position) {
//        return values.get(position);
//    }
    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, final int position) {

        final UserListingData model = values.get(holder.getAdapterPosition());


        Log.d("#ImageUrl",""+model.getProfilePic());
        if (!model.getProfilePic().isEmpty()) {
//            holder.binding.ivProfile.setImageURI(Uri.parse( model.getProfilePic()));
            new ImageUtils().loadImage(holder.binding.ivProfile.getContext(), model.getProfilePic(), holder.binding.ivProfile);
        }else{
            holder.binding.ivProfile.setImageResource(R.drawable.app_icon);
        }

        if (!model.getUsername().isEmpty()) {
            holder.binding.tvName.setText(model.getUsername());
        }
        if (!model.getBusinessname().isEmpty()) {
            holder.binding.tvName.setText(model.getBusinessname());
        }


        Log.d("Name:" + model.getBusinessname(), "__" + model.getProfilePic());
        if (model.getStatus().equals(AppUtilsKt.STATUS_PENDING)) {
//            holder.binding.tvAddFriendUnfriend.setClickable(false);
            holder.binding.tvAddFriendUnfriend.setSelected(true);
            holder.binding.tvAddFriendUnfriend.setText(model.getStatus());
        } else if (model.getStatus().equals(AppUtilsKt.STATUS_ACCEPT)) {
            holder.binding.tvAddFriendUnfriend.setText(mActivity.getString(R.string.friends));
            holder.binding.tvAddFriendUnfriend.setSelected(true);
        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        //        private AppCompatTextView tvName;
//        private CircleImageView ivProfile;
//        AppCompatTextView tvAddFriendUnfriend;
//
        RowUserListingBinding binding;

        ViewHolder(RowUserListingBinding view) {
            super(view.getRoot());
            binding = view;

            binding.tvAddFriendUnfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!values.get(getAdapterPosition()).getStatus().equals(AppUtilsKt.STATUS_PENDING)) {
                        listener.onClick(getAdapterPosition());
                    }

                }
            });

            binding.ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.cvReport.setVisibility(View.VISIBLE);
                }
            });
            binding.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.cvReport.getVisibility() == View.VISIBLE) {
                        binding.cvReport.setVisibility(View.GONE);
                    } else {
                        viewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

            binding.cvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.cvReport.setVisibility(View.GONE);
                    reportDialogClickListener.onButtonClicked(values.get(getAdapterPosition()), "");
                }
            });
        }
    }
}
