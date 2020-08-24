package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse
import com.xzit.app.utils.isNetworkConnected
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class MasterDataRepository : BaseRepository() {
    open var responseData = MutableLiveData<MasterDataResponse>()

    fun callMasterData(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callMasterData(req).enqueue(object : retrofit2.Callback<MasterDataResponse> {
                override fun onFailure(call: Call<MasterDataResponse>, t: Throwable) {
                    hideProgress()

                    responseData.value = MasterDataResponse(
                            4001,
                            t.message,
                            null)
                }

                override fun onResponse(call: Call<MasterDataResponse>, response: Response<MasterDataResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), MasterDataResponse::class.java)
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