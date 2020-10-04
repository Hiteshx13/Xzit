package com.xzit.app.retrofit.model.response.eventinvitationsent

data class EventInvitationReceived(
        var status: Int,
        var message: String?,
        var Response: List<Object?>?,
        var authToken: String?

)