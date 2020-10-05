package com.xzit.app.retrofit.model.response.registration

data class RegistrationResponse(
        var status: Int,
        var message: String = "",
        var Response: ArrayList<String?>,
        var authToken: Object
)