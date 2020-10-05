package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.createevent.CreateEventResponse
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponse
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponseError
import com.xzit.app.retrofit.model.response.eventinvitation.EventInvitationAcceptReject
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationReceived
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationSent
import com.xzit.app.utils.isNetworkConnected
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class EventRepository : BaseRepository() {
    open var responseData = MutableLiveData<CreateEventResponse>()
    open var responseEventInvitationSent = MutableLiveData<EventInvitationSent>()
    open var responseEventInvitationReceived = MutableLiveData<EventInvitationReceived>()
    open var responsEventListing = MutableLiveData<EventListingResponse>()
    open var responsEventAcceptReject = MutableLiveData<EventInvitationAcceptReject>()

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
                            var model = Gson().fromJson(jObjError.toString(), EventListingResponseError::class.java)
                            responsEventListing.value = EventListingResponse(4001, model.message, null, null)

                        } catch (e: Exception) {
                            var model = EventListingResponse(4001, "No data found", null, null)
                            responsEventListing.value = model
                            //Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responsEventListing.value = response.body()
                    }
                }
            })
        }
    }

    fun callAcceptRejectEventInvitation(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callAcceptRejectEventInvitation(req).enqueue(object : retrofit2.Callback<EventInvitationAcceptReject> {
                override fun onFailure(call: Call<EventInvitationAcceptReject>, t: Throwable) {
                    hideProgress()
                    var model = EventInvitationAcceptReject(4001, t.message?:"", null, null)
                    responsEventAcceptReject.value = model
                }

                override fun onResponse(call: Call<EventInvitationAcceptReject>, response: Response<EventInvitationAcceptReject>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {

                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventListingResponseError::class.java)
                            responsEventAcceptReject.value = EventInvitationAcceptReject(4001, model.message?:"", null, null)

                        } catch (e: Exception) {
                            var model = EventInvitationAcceptReject(4001, "No data found", null, null)
                            responsEventAcceptReject.value = model
                            //Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responsEventAcceptReject.value = response.body()
                    }
                }
            })
        }
    }

    // params: HashMap<String, RequestBody?>
    fun callCreateEventImage(mContext: Context, map: HashMap<String, RequestBody?>, image: ArrayList<MultipartBody.Part?>
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
            apiInterface.callAllSendInvitationByUser(req).enqueue(object : retrofit2.Callback<EventInvitationSent> {
                override fun onFailure(call: Call<EventInvitationSent>, t: Throwable) {
                    hideProgress()
                    var model = EventInvitationSent(4001, t.message,null,null)
                    model.message = t.message
                    model.status = 4001
                    responseEventInvitationSent.value = model
                }

                override fun onResponse(call: Call<EventInvitationSent>, response: Response<EventInvitationSent>) {
                    hideProgress()
                    if (response.body() == null) {

                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventInvitationSent::class.java)
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
            showProgress(mContext)
            apiInterface.callReceivedEventInvitation(req).enqueue(object : retrofit2.Callback<EventInvitationReceived> {
                override fun onFailure(call: Call<EventInvitationReceived>, t: Throwable) {
                    hideProgress()
                    var model = EventInvitationReceived(4001, t.message,null,null)
                    model.message = t.message
                    model.status = 4001
                    responseEventInvitationReceived.value = model
                }

                override fun onResponse(call: Call<EventInvitationReceived>, response: Response<EventInvitationReceived>) {
                    hideProgress()
                    if (response.body() == null) {

                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventInvitationReceived::class.java)
                            responseEventInvitationReceived.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        responseEventInvitationReceived.value = response.body()
                    }
                }
            })
        }
    }


}