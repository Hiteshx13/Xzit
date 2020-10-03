package com.xzit.app.retrofit.model.response.registration

data class RegistrationResponse(
        var status: Integer,
        var message: String = "",
        var Response: ArrayList<Object>,
        var authToken: Object
)