package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.changepassword.ChangePasswordResponse
import com.xzit.app.retrofit.model.response.changepassword.ForgotPasswordResponse
import com.xzit.app.retrofit.model.response.editprofile.EditProfileResponse
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.utils.isNetworkConnected
import com.xzit.app.utils.showToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class EditProfileRepository : BaseRepository() {
    open var responseData = MutableLiveData<EditProfileResponse>()

    fun callEditProfile(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callEditProfile(req).enqueue(object : retrofit2.Callback<EditProfileResponse> {
                override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                    hideProgress()
                    var model= EditProfileResponse()
                    model.setMessage(t.message)
                    model.setStatus(4001)
                    responseData.value = model
                }

                override fun onResponse(call: Call<EditProfileResponse>, response: Response<EditProfileResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model=  Gson().fromJson(jObjError.toString(),EditProfileResponse::class.java)
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