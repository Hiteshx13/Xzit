package com.xzit.app.retrofit.model.response.registration

data class RegistrationResponse(
        var status: Integer,
        var message: String = "",
        var response: String,
        var authToken: Object
)