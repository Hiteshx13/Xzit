package com.xzit.app.retrofit.model.response.eventdata

data class EventListingResponse(
        var status: Int,
        var message: String?,
        var Response:EventListingData?,
        var authToken: String?

)