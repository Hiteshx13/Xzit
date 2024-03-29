package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.utils.isNetworkConnected
import com.xzit.app.utils.showToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class LoginRepository : BaseRepository() {
    open var loginData = MutableLiveData<LoginResponse>()

    fun callLogin(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callLogin(req).enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    hideProgress()
                    var model = LoginResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    loginData.value = model
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
//                            Toast.makeText(mContext, jObjError.getString("message"), Toast.LENGTH_LONG).show()
                            var model=  Gson().fromJson(jObjError.toString(),LoginResponse::class.java)
                            loginData.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    }else{
                        loginData.value = response.body()
                    }
                }
            })
        }
    }

}