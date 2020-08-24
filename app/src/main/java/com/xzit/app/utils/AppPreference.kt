package com.xzit.app.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.xzit.app.R
import com.xzit.app.activity.XzitApp.mContext
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.retrofit.model.response.masterdata.MasterDataResponse


open class AppPreference {

    var PREF_USER_DATA = "login_user_data"
    var USER_MASTER_DATA = "user_master_data"
    var mPrefs: SharedPreferences? = null

    init {
        mPrefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE)
    }

    /**save login data**/
    open fun saveLoginData(mContext: Context, response: LoginResponse) {
        if (mPrefs == null) {
            mPrefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE)
        }
        val prefsEditor: SharedPreferences.Editor = mPrefs!!.edit()
        val gson = Gson()
        val json = gson.toJson(response)
        prefsEditor.putString(PREF_USER_DATA, json)
        prefsEditor.commit()
    }

    open fun getUserData(mContext: Context): LoginResponse {
        if (mPrefs == null) {
            mPrefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE)
        }
        val gson = Gson()
        val json = mPrefs!!.getString(PREF_USER_DATA, null)
        val obj: LoginResponse = gson.fromJson(json, LoginResponse::class.java)
        return obj
    }

    open fun getUserData(): LoginResponse? {
        val gson = Gson()
        val json = mPrefs!!.getString(PREF_USER_DATA, null)
        if (json == null) {
            return null
        } else {
            val obj: LoginResponse = gson.fromJson(json, LoginResponse::class.java)
            return obj
        }
    }

    /**save master api data**/
    open fun saveMasterData(mContext: Context, response: MasterDataResponse) {
        if (mPrefs == null) {
            mPrefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE)
        }
        val prefsEditor: SharedPreferences.Editor = mPrefs!!.edit()
        val gson = Gson()
        val json = gson.toJson(response)
        prefsEditor.putString(USER_MASTER_DATA, json)
        prefsEditor.commit()
    }


    /**get master api data**/
    open fun getMasterData(mContext: Context): MasterDataResponse {
        if (mPrefs == null) {
            mPrefs = mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE)
        }
        val gson = Gson()
        val json = mPrefs!!.getString(USER_MASTER_DATA, null)
        val obj: MasterDataResponse = gson.fromJson(json, MasterDataResponse::class.java)
        return obj
    }

}