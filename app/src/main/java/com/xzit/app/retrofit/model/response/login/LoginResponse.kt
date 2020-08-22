package com.xzit.app.retrofit.model.response.login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginResponse {

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("Response")
    @Expose
    private var response: List<LoginData?>? = null

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

    fun getResponse(): List<LoginData?>? {
        return response
    }

    fun setResponse(response: List<LoginData?>?) {
        this.response = response
    }

}