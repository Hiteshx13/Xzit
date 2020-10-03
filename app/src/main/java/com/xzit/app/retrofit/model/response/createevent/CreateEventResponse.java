package com.xzit.app.retrofit.model.response.createevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateEventResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Response")
    @Expose
    private List<CreateEventData> response = null;
    @SerializedName("authToken")
    @Expose
    private String authToken;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CreateEventData> getResponse() {
        return response;
    }

    public void setResponse(List<CreateEventData> response) {
        this.response = response;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
