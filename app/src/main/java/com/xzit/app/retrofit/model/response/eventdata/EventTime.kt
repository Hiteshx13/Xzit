package com.xzit.app.retrofit.model.response.eventdata

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class EventTime {

    @SerializedName("monday")
    @Expose
     val monday: EventWeekDay? = null

    @SerializedName("tuesday")
    @Expose
     val tuesday: EventWeekDay? = null

    @SerializedName("wednesday")
    @Expose
     val wednesday: EventWeekDay? = null

    @SerializedName("thrusday")
    @Expose
     val thrusday: EventWeekDay? = null

    @SerializedName("friday")
    @Expose
     val friday: EventWeekDay? = null

    @SerializedName("saturday")
    @Expose
     val saturday: EventWeekDay? = null

    @SerializedName("sunday")
    @Expose
     val sunday: EventWeekDay? = null
}