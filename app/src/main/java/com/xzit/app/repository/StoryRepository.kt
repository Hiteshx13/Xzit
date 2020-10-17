package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse
import com.xzit.app.utils.isNetworkConnected
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

open class StoryRepository : BaseRepository() {
    open var responseData = MutableLiveData<RegistrationResponse>()

    fun callAddStory(mContext: Context,
                        params: HashMap<String, okhttp3.RequestBody>, image: ArrayList<MultipartBody.Part?>
    ) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callRegisterData(params, image
            ).enqueue(object : retrofit2.Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    hideProgress()
                }

                override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), RegistrationResponse::class.java)
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