package com.xzit.app.repository

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.lifecycle.MutableLiveData
import com.xzit.app.R
import com.xzit.app.retrofit.ApiClient
import com.xzit.app.retrofit.model.response.login.LoginResponse

open class BaseRepository {
    var apiInterface = ApiClient.getApiInterface()
    var mDialog: Dialog? = null

    init {

    }

    fun getFailureData(t: Throwable): MutableLiveData<LoginResponse> {
        var failureData = MutableLiveData<LoginResponse>()
        failureData.value?.setStatus(4001)
        failureData.value?.setMessage(t.message)
        return failureData
    }

    fun showProgress(cotext: Context) {
        mDialog = Dialog(cotext)
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.setContentView(R.layout.dialog_progress)
        mDialog?.setCancelable(false)
        mDialog?.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
        mDialog?.show()
    }

    fun hideProgress() {
        if (mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }
}