package com.xzit.app.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.utils.isNetworkConnected
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class ChangePasswordRepository : BaseRepository() {
    open var responseData = MutableLiveData<LoginResponse>()

    fun callChangePassword(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callLogin(req).enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    hideProgress()
                    var model=LoginResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    responseData.value = model
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    hideProgress()
                    responseData.value = response.body()
                }
            })
        }
    }

}