package com.xzit.app.retrofit.model.response.eventinvitation

data class EventInvitationAcceptReject(

        var status: Int,
        var message: String?,
        var response: List<Object?>?,
        var authToken: String?
)