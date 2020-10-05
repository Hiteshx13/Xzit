package com.xzit.app.retrofit.model.response.eventinvitationreceived

data class EventInvitationReceivedData(

        var fronName: String?,
        var userId: String?,
        var eventId: String?,
        var eventName: String?,
        var inviReceivedOn: String?,
        var eventLocation: String?,
        var eventLat: String?,
        var eventLong: String?,
        var eventVenueName: String?,
        var eventInviId: String?,
        var eventBanners: List<String?>?

)