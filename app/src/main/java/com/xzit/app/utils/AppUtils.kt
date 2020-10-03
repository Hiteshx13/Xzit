package com.xzit.app.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xzit.app.R
import okhttp3.MediaType
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*


const val PARAM_SIGNUP_TYPE = "param_signup_type"
const val PARAM_SIGNUP_DATA = "param_signup_data"
const val PARAM_USER_ID = "param_user_id"
const val PARAM_CLIENT_ID = "param_client_id"
const val PARAM_EVENT_ID = "param_event_id"
const val SIGNUP_TYPE_MERCHENT = "signup_type_merchent"
const val EVENT_INVITATION_STATUS_ACCEPT = "ACCEPT"
const val SIGNUP_TYPE_USER = "signup_type_user"
const val DASHBOARD_TAB = "dashboard_tab"
const val VALIDATION_password_length = 6
const val STATUS_PENDING = "PENDING"
const val STATUS_ACCEPT = "ACCEPT"
const val REQ_CASE_USER = "USER"
const val USER_TYPE_NORMAL = "NORMAL"
const val USER_TYPE_BUSSINESS = "BUSSINESS"


const val REQ_WRITE_EXST = 501
const val REQ_LOGIN_WITH_GMAIL = 301
const val REQ_SELECT_PHOTO_GALLERY = 111

const val RESP_API_SUCCESS = 200
const val RESP_API_SUCCESS2 = 400

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun LaunchActivity(mContext: Context, intent: Intent) {
    mContext.startActivity(intent)
}

fun isEmailValid(email: String): Boolean {
    if (TextUtils.isEmpty(email)) {
        return false
    } else {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

fun isNetworkConnected(mContext: Context): Boolean {
    val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    if (!isConnected) {
        showToast(mContext, mContext.getString(R.string.please_check_internet_connection))
    }
    return isConnected
}

fun getStringDate(date: Date): String {
    var spf = SimpleDateFormat("yyyy-MM-dd")
    var strdate = spf.format(date)
    return strdate
}

fun getTimeHM(date: Date): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(date)
}

fun getRequestBody(str: String?): RequestBody? {
    return RequestBody.create(MediaType.parse("multipart/form-data"), str)
}
