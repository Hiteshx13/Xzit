package com.xzit.app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.xzit.app.retrofit.model.request.LoginRequest
import com.xzit.app.retrofit.model.response.LoginResponse
import retrofit2.Call
import retrofit2.Response

class LoginRepository : BaseRepository() {
    open var loginData: MutableLiveData<LoginResponse> = MutableLiveData<LoginResponse>()
    var loginRepository: LoginRepository? = null

    fun getInstance() {
        if (loginRepository == null) {
            loginRepository = LoginRepository()
        }
    }

    fun callLogin(req:LoginRequest) {
        apiInterface.callLogin(req).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("","")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginData.value = response.body()
                Log.d("","")
            }
        })
    }

}