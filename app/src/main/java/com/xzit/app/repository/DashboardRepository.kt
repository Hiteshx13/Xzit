package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.dashboard.FCMTokenResponse
import com.xzit.app.utils.isNetworkConnected
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class DashboardRepository : BaseRepository() {
    open var fcmTokenResponse = MutableLiveData<FCMTokenResponse>()


    fun callApi(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callUpdateFCMToken(req).enqueue(object : retrofit2.Callback<FCMTokenResponse> {
                override fun onFailure(call: Call<FCMTokenResponse>, t: Throwable) {
                    hideProgress()
                    var model = FCMTokenResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    fcmTokenResponse.value = model
                }

                override fun onResponse(call: Call<FCMTokenResponse>, response: Response<FCMTokenResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), FCMTokenResponse::class.java)
                            fcmTokenResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        fcmTokenResponse.value = response.body()
                    }
                }
            })
        }
    }
}