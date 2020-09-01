package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.squareup.okhttp.RequestBody
import com.xzit.app.retrofit.model.response.registration.RegistrationResponse
import com.xzit.app.utils.isNetworkConnected
import okhttp3.MultipartBody
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

    fun callSignUp(mContext: Context,
                   reqCase: RequestBody,
                   accountType: RequestBody,
                   businessName: RequestBody,
                   email: RequestBody,
                   password: RequestBody,
                   confPassword: RequestBody,
                   userName: RequestBody,
                   title: RequestBody,
                   category: RequestBody,
                   phone: RequestBody,
                   website: RequestBody,
                   businessHours: RequestBody,
                   image: MultipartBody.Part
    ) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
           // apiInterface.apiRegister();
            apiInterface.callRegisterData(reqCase,
                    accountType, businessName, email, password, confPassword, userName, title, category,
                    phone,
                    website,
                    businessHours,
                    image).enqueue(object : retrofit2.Callback<RegistrationResponse> {
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