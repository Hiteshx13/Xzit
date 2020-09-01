package com.xzit.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.xzit.app.R;
import com.xzit.app.listener.OnButtonClickListener;
import com.xzit.app.retrofit.model.response.userlisting.UserListingData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<UserListingData> values;
    private OnButtonClickListener listener;

    public UserListAdapter(List<UserListingData> myDataset, OnButtonClickListener listener) {
        values = myDataset;
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

        final UserListingData model = values.get(position);
        holder.tvName.setText(model.getFullname());

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        private AppCompatTextView tvName;
        private CircleImageView ivProfile;
        RelativeLayout rlRoot;

        ViewHolder(View v) {
            super(v);
            layout = v;
            rlRoot = layout.findViewById(R.id.rlRoot);
            tvName = layout.findViewById(R.id.tvName);
            ivProfile = layout.findViewById(R.id.ivProfile);
            rlRoot.setOnClickListener(new View.OnClickListener() {
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
