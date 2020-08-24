package com.xzit.app.retrofit.model.response.masterdata

data class MasterResponse(
        var VENUE_TYPE: List<VENUETYPE>? = null,
        val MUSIC_TYPE: List<MUSICTYPE>? = null,
        var FOOD_TYPE: List<FOODTYPE>? = null,
        var CATAGORY_LIST: List<CATAGORYLIST>? = null
)