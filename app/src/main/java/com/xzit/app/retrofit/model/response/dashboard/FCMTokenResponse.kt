package com.xzit.app.retrofit.model.response.dashboard

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class FCMTokenResponse {


    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("Response")
    @Expose
    private var response: ArrayList<String?>? = null

    @SerializedName("authToken")
    @Expose
    private var authToken: String? = null

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

    fun getResponse(): ArrayList<String?>? {
        return response
    }

    fun setResponse(response: ArrayList<String?>?) {
        this.response = response
    }

    fun getAuthToken(): String? {
        return authToken
    }

    fun setAuthToken(authToken: String?) {
        this.authToken = authToken
    }
}