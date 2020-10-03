package com.xzit.app.retrofit.model.response.friendlist

data class FriendListResponse(

        var status: Int,
        var message: String = "",
        var Response: List<FriendListData>? = null
)