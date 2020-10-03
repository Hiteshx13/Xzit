package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.createevent.CreateEventResponse
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponse
import com.xzit.app.utils.isNetworkConnected
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class EventRepository : BaseRepository() {
    open var responseData = MutableLiveData<CreateEventResponse>()
    open var responseEventInvitationSent = MutableLiveData<EventListingResponse>()
    open var responsEventListing = MutableLiveData<EventListingResponse>()

    fun callCreateEvent(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callCreateEvent(req).enqueue(object : retrofit2.Callback<CreateEventResponse> {
                override fun onFailure(call: Call<CreateEventResponse>, t: Throwable) {
                    hideProgress()
                    var model = CreateEventResponse()
                    model.message = t.message
                    model.status = 4001
                    responseData.value = model
                }

                override fun onResponse(call: Call<CreateEventResponse>, response: Response<CreateEventResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), CreateEventResponse::class.java)
                            responseData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseData.value = response.body()
                    }
                }
            })
        }
    }

    fun callEventListing(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callEventListing(req).enqueue(object : retrofit2.Callback<EventListingResponse> {
                override fun onFailure(call: Call<EventListingResponse>, t: Throwable) {
                    hideProgress()
                    var model = EventListingResponse(4001, t.message, null, null)
                    responsEventListing.value = model
                }

                override fun onResponse(call: Call<EventListingResponse>, response: Response<EventListingResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventListingResponse::class.java)
                            responsEventListing.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responsEventListing.value = response.body()
                    }
                }
            })
        }
    }

    // params: HashMap<String, RequestBody?>
    fun callCreateEventImage(mContext: Context, map: HashMap<String, RequestBody?>, image: MultipartBody.Part
    ) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callCreateEventImage(map, image
            ).enqueue(object : retrofit2.Callback<CreateEventResponse> {
                override fun onFailure(call: Call<CreateEventResponse>, t: Throwable) {
                    hideProgress()
                    var model = CreateEventResponse()
                    model.message = t.message
                    model.status = 4001
                    responseData.value = model
                }

                override fun onResponse(call: Call<CreateEventResponse>, response: Response<CreateEventResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), CreateEventResponse::class.java)
                            responseData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseData.value = response.body()
                    }
                }
            })
        }
    }

    // params: HashMap<String, RequestBody?>
    fun callCreateEventImage(mContext: Context,
                             requestCase: RequestBody, clientId: RequestBody, userId: RequestBody,
                             eventTitleName: RequestBody, eventDescDetail: RequestBody, image: MultipartBody.Part
    ) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callCreateEventImage(requestCase, clientId, userId, eventTitleName, eventDescDetail, image
            ).enqueue(object : retrofit2.Callback<CreateEventResponse> {
                override fun onFailure(call: Call<CreateEventResponse>, t: Throwable) {
                    hideProgress()
                    var model = CreateEventResponse()
                    model.message = t.message
                    model.status = 4001
                    responseData.value = model
                }

                override fun onResponse(call: Call<CreateEventResponse>, response: Response<CreateEventResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), CreateEventResponse::class.java)
                            responseData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseData.value = response.body()
                    }
                }
            })
        }
    }


    fun callAllSendInvitationByUser(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callAllSendInvitationByUser(req).enqueue(object : retrofit2.Callback<EventListingResponse> {
                override fun onFailure(call: Call<EventListingResponse>, t: Throwable) {
                    var model = EventListingResponse(4001, t.message,null,null)
                    model.message = t.message
                    model.status = 4001
                    responseEventInvitationSent.value = model
                }

                override fun onResponse(call: Call<EventListingResponse>, response: Response<EventListingResponse>) {
                    if (response.body() == null) {

                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventListingResponse::class.java)
                            responseEventInvitationSent.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseEventInvitationSent.value = response.body()
                    }
                }
            })
        }
    }fun callAllReceivedInvitationByUser(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {

            apiInterface.callAllSendInvitationByUser(req).enqueue(object : retrofit2.Callback<EventListingResponse> {
                override fun onFailure(call: Call<EventListingResponse>, t: Throwable) {
                    hideProgress()
                    var model = EventListingResponse(4001, t.message,null,null)
                    model.message = t.message
                    model.status = 4001
                    responseEventInvitationSent.value = model
                }

                override fun onResponse(call: Call<EventListingResponse>, response: Response<EventListingResponse>) {
                    hideProgress()
                    if (response.body() == null) {

                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventListingResponse::class.java)
                            responseEventInvitationSent.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseEventInvitationSent.value = response.body()
                    }
                }
            })
        }
    }


}