package com.xzit.app.retrofit.model.request

data class SignUpRequest(var BusinessName: String,
                         var email: String,
                         var password: String,
                         var username: String,
                         var title: String,
                         var description: String,
                         var category: String,
                         var telephone: String
)