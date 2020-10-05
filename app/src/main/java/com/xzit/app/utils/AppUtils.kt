package com.xzit.app.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.xzit.app.R
import com.xzit.app.utils.RealPathUtil.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.net.URISyntaxException
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
fun loadImage(mContext: Context, url: String, imageView: AppCompatImageView) {
    Glide.with(mContext).load(url).into(imageView).onLoadFailed(mContext.getDrawable(R.drawable.demo))
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
@SuppressLint("NewApi")
@Throws(URISyntaxException::class)
fun getFilePathFromContentUri(context:Context,uri: Uri): String? {
    var uri = uri
    var selection: String? = null
    var selectionArgs: Array<String>? = null
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, uri)) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            if ("image" == type) {
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            selection = "_id=?"
            selectionArgs = arrayOf(
                    split[1]
            )
        }
    }
    if ("content".equals(uri.scheme, ignoreCase = true)) {
        if (isGooglePhotosUri(uri)) {
            return uri.lastPathSegment
        }
        val projection = arrayOf(
                MediaStore.Images.Media.DATA
        )
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver
                    .query(uri, projection, selection, selectionArgs, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                return cursor.getString(column_index)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}
//fun getRequestBody(str: String?): RequestBody? {
//    return RequestBody.create(MediaType.parse("multipart/form-data"), str)
//}
