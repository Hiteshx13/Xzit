package com.xzit.app.retrofit.model.response.eventinvitationsent

import com.xzit.app.retrofit.model.response.eventinvitationreceived.EventInvitationReceivedData

data class EventInvitationReceived(
        var status: Int,
        var message: String?,
        var Response: List<EventInvitationReceivedData?>?,
        var authToken: String?

)