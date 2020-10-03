package com.xzit.app.retrofit.model.response.preference

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PreferenceResponse {

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("authToken")
    @Expose
    private var authToken: String? = null

    @SerializedName("Response")
    @Expose
    private var response:  ArrayList<Object>? =null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getResponse(): ArrayList<Object>? {
        return response
    }

    fun setResponse(response: ArrayList<Object>?) {
        this.response = response
    }

}