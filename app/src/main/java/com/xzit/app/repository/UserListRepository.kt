package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.userlisting.UserListingResponse
import com.xzit.app.utils.isNetworkConnected
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class UserListRepository : BaseRepository() {
    open var apiData = MutableLiveData<UserListingResponse>()

    fun callUserList(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callUserListing(req).enqueue(object : retrofit2.Callback<UserListingResponse> {
                override fun onFailure(call: Call<UserListingResponse>, t: Throwable) {
                    hideProgress()
                    var model = UserListingResponse()
                    model.message = t.message
                    model.status = 4001
                    apiData.value = model
                }

                override fun onResponse(call: Call<UserListingResponse?>, response: Response<UserListingResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), UserListingResponse::class.java)
                            apiData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        apiData.value = response.body()
                    }
                }
            })
        }
    }

    fun callFriendUnfriend(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callUserListing(req).enqueue(object : retrofit2.Callback<UserListingResponse> {
                override fun onFailure(call: Call<UserListingResponse>, t: Throwable) {
                    hideProgress()
                    var model = UserListingResponse()
                    model.message = t.message
                    model.status = 4001
                    apiData.value = model
                }

                override fun onResponse(call: Call<UserListingResponse?>, response: Response<UserListingResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), UserListingResponse::class.java)
                            apiData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        apiData.value = response.body()
                    }
                }
            })
        }
    }

}