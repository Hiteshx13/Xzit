package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.CreateEventResponse
import com.xzit.app.utils.isNetworkConnected
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class EventRepository : BaseRepository() {
    open var responseData = MutableLiveData<CreateEventResponse>()

    fun callCreateEvent(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callCreateEvent(req).enqueue(object : retrofit2.Callback<CreateEventResponse> {
                override fun onFailure(call: Call<CreateEventResponse>, t: Throwable) {
                    hideProgress()
                    var model = CreateEventResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
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
            apiInterface.callCreateEventImage(requestCase,clientId,userId,eventTitleName,eventDescDetail, image
            ).enqueue(object : retrofit2.Callback<CreateEventResponse> {
                override fun onFailure(call: Call<CreateEventResponse>, t: Throwable) {
                    hideProgress()
                    var model = CreateEventResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
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


}