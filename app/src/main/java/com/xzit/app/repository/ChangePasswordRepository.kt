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

open class ChangePasswordRepository : BaseRepository() {
    open var responseData = MutableLiveData<ChangePasswordResponse>()

    fun callChangePassword(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callChangePassword(req).enqueue(object : retrofit2.Callback<ChangePasswordResponse> {
                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    hideProgress()
                    var model= ChangePasswordResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    responseData.value = model
                }

                override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model=  Gson().fromJson(jObjError.toString(),ChangePasswordResponse::class.java)
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