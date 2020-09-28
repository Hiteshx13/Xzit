package com.xzit.app.retrofit.model.response.profile

data class UserProfileResponse (
        var status: Int,
        var message: String?="",
        var Response: UserProfileData?,
        var authToken: String?
)