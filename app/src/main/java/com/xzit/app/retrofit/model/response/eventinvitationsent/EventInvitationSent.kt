package com.xzit.app.retrofit.model.response.eventinvitationsent

data class EventInvitationSent(
        var status: Int,
        var message: String?,
        var Response: List<EventInvitationSentData?>?,
        var authToken: String?

)