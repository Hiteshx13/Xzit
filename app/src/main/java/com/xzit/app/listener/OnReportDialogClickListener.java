package com.xzit.app.listener;

import com.xzit.app.retrofit.model.response.userlisting.UserListingData;

public interface OnReportDialogClickListener {
    void onButtonClicked(UserListingData model, String report);
}
