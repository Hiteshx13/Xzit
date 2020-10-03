package com.xzit.app.retrofit.model.response.friendlist

data class FriendListData(
        var title: String,
        var fullname: String,
        var phone: String,
        var profilePic: String,
        var userId: String,
        var pkfriendId: String,
        var isAdded: Boolean=false
)