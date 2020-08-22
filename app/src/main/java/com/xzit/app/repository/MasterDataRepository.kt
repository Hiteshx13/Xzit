package com.xzit.app.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xzit.app.retrofit.model.response.login.masterdata.MasterDataResponse
import com.xzit.app.utils.isNetworkConnected
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class MasterDataRepository : BaseRepository() {
    open var apiData = MutableLiveData<MasterDataResponse>()

    fun callMasterData(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callMasterData(req).enqueue(object : retrofit2.Callback<MasterDataResponse> {
                override fun onFailure(call: Call<MasterDataResponse>, t: Throwable) {
                    hideProgress()

                    apiData.value = MasterDataResponse(
                            4001,
                            t.message,
                            null)
                }

                override fun onResponse(call: Call<MasterDataResponse>, response: Response<MasterDataResponse>) {
                    hideProgress()
                    apiData.value = response.body()
                }
            })
        }
    }

}