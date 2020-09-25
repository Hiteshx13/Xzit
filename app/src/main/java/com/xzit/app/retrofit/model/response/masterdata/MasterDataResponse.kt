package com.xzit.app.retrofit.model.response.masterdata

import com.google.gson.annotations.SerializedName


data class MasterDataResponse(
        val status: Int? = null,
        val message: String? = null,
        var Response: ArrayList<MasterResponse>? = null,
        @SerializedName("authToken")
        var authToken: String? = null
)
