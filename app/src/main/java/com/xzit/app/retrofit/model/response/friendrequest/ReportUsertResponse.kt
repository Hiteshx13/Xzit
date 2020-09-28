package com.xzit.app.retrofit.model.response.friendrequest

data class ReportUsertResponse (
        var status: Int,
        var message: String="",
        var Response: List<String>?,
        var authToken: String
)