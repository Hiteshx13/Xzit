package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.squareup.okhttp.RequestBody
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse
import com.xzit.app.utils.isNetworkConnected
import com.xzit.app.utils.showToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class RegistrationRepository : BaseRepository() {
    open var responseData = MutableLiveData<RegistrationResponse>()

    fun callSignUp(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callRegisterData(req).enqueue(object : retrofit2.Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    hideProgress()
//                    var model = RegistrationResponse()
//                    model.setMessage(t.message)
//                    model.setStatus(4001)
//                    loginData.value = model
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

    fun callSignUp(mContext: Context, req: HashMap<String, String>, profileImage: RequestBody) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callRegisterData(req, profileImage).enqueue(object : retrofit2.Callback<RegistrationResponse> {
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