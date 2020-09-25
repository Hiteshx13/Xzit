package com.xzit.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.listener.OnButtonClickListener;
import com.xzit.app.retrofit.model.response.userlisting.UserListingData;
import com.xzit.app.utils.AppUtilsKt;
import com.xzit.app.utils.ImageUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<UserListingData> values;
    private OnButtonClickListener listener;
    private Context context;

    public UserListAdapter(Context context, List<UserListingData> myDataset, OnButtonClickListener listener) {
        values = myDataset;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_user_listing, parent, false);

        UserListAdapter.ViewHolder vh = new UserListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, final int position) {

        final UserListingData model = values.get(holder.getAdapterPosition());

        if (!model.getProfilePic().isEmpty()) {
            new ImageUtils().loadImage(context, model.getProfilePic(), holder.ivProfile);
        }
        holder.tvName.setText(model.getBusinessname());
        Log.d("Name:" + model.getBusinessname(), "__" + model.getProfilePic());
        if (model.getStatus().equals(AppUtilsKt.STATUS_PENDING)) {
            holder.tvAddFriendUnfriend.setClickable(false);
            holder.tvAddFriendUnfriend.setSelected(true);
            holder.tvAddFriendUnfriend.setText(model.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        private AppCompatTextView tvName;
        private CircleImageView ivProfile;
        AppCompatTextView tvAddFriendUnfriend;

        ViewHolder(View v) {
            super(v);
            layout = v;
            tvAddFriendUnfriend = layout.findViewById(R.id.tvAddFriendUnfriend);
            tvName = layout.findViewById(R.id.tvName);
            ivProfile = layout.findViewById(R.id.ivProfile);
            tvAddFriendUnfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnChatClickListener {
        void onClickChat(int pos);
    }

}
