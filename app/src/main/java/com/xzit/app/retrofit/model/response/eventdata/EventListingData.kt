package com.xzit.app.retrofit.model.response.eventdata

data class EventListingData(
        var ongoing: List<Ongoing>? = null,
        var upcomming: List<Upcomming>? = null,
        var completed: List<Completed>? = null)