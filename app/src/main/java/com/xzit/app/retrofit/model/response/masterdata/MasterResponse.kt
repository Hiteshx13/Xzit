package com.xzit.app.retrofit.model.response.masterdata

import com.google.gson.annotations.SerializedName


data class MasterResponse(
        @SerializedName("lookupType")
        var lookupType: String,
        @SerializedName("lookupTypeName")
        var lookupTypeName: String,
        @SerializedName("lookupTypeId")
        var lookupTypeId: String,
        @SerializedName("subtype")
        var subtype: List<Subtype>


)