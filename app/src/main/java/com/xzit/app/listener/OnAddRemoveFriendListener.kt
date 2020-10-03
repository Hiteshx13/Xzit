package com.xzit.app.listener

import com.xzit.app.retrofit.model.response.friendlist.FriendListData
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestData

interface OnAddRemoveFriendListener {
    fun onClick(position: Int,model:FriendListData)
}