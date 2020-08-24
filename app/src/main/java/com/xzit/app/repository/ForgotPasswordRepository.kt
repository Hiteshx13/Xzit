package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.utils.isNetworkConnected
import com.xzit.app.utils.showToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class ForgotPasswordRepository : BaseRepository() {
    open var responseData = MutableLiveData<ForgotPasswordResponse>()

    fun callForgotPassword(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callForgotPassword(req).enqueue(object : retrofit2.Callback<ForgotPasswordResponse> {
                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                    hideProgress()
                    var model= ForgotPasswordResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    responseData.value = model
                }

                override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model=  Gson().fromJson(jObjError.toString(), ForgotPasswordResponse::class.java)
                            responseData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    }else{
                        responseData.value = response.body()
                    }
                }
            })
        }
    }

}