package com.xzit.app.retrofit.model.request

import java.io.Serializable

data class SignUpRequest(var businessName: String = "",
                         var email: String = "",
                         var password: String = "",
                         var confPassword: String = "",

                         var username: String = "",
                         var title: String = "",
                         var description: String = "",
                         var category: String = "",
                         var telephone: String = "",
                         var website: String = "",
                         var businessHours: String = ""

):Serializable