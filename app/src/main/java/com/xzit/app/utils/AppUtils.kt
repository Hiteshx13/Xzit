package com.xzit.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.xzit.app.R


const val PARAM_SIGNUP_TYPE = "param_signup_type"
const val SIGNUP_TYPE_MERCHENT = "signup_type_merchent"
const val SIGNUP_TYPE_USER = "signup_type_user"
const val DASHBOARD_TAB = "dashboard_tab"
const val REQ_LOGIN_WITH_GMAIL = 301

const val RESP_API_SUCCESS = 200

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isNetworkConnected(mContext: Context): Boolean {
    val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    if(!isConnected){
        showToast(mContext,mContext.getString(R.string.please_check_internet_connection))
    }
    return isConnected
}
