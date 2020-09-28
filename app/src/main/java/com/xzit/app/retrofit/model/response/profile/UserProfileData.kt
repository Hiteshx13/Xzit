package com.xzit.app.retrofit.model.response.profile

data class UserProfileData(
        var title: String,
        var fullname: String,
        var gender: String,
        var email: String,
        var description: String,
        var profilePic: List<Object>? = null,
        var storyListing: List<Object>? = null,
        var publicationCount: Int,
        var eventListing: List<Object>? = null,
        var savedEventListing: List<SavedEventListing>? = null)