package com.xzit.app.retrofit.model.response.eventdata

data class EventListingResponseError(
        var status: Int,
        var message: String?,
        var Response:ArrayList<EventListingData?>?,
        var authToken: String?

)