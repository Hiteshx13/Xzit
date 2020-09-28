package com.xzit.app.retrofit.model.response.friendrequest

data class FriendRequestResponse (
        var status: Int,
        var message: String="",
        var Response: List<FriendRequestData>?,
        var authToken: String
)