package com.xzit.app.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xzit.app.retrofit.model.response.eventinvitation.EventInvitationResponse
import com.xzit.app.retrofit.model.response.friendlist.FriendListResponse
import com.xzit.app.retrofit.model.response.friendrequest.AcceptRejectFriendRequestResponse
import com.xzit.app.retrofit.model.response.friendrequest.BlockUnblockUserResponse
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestResponse
import com.xzit.app.retrofit.model.response.friendrequest.ReportUsertResponse
import com.xzit.app.retrofit.model.response.profile.UserProfileResponse
import com.xzit.app.retrofit.model.response.userlisting.SendFriendRequestResponse
import com.xzit.app.retrofit.model.response.userlisting.UserListingResponse
import com.xzit.app.utils.isNetworkConnected
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

open class UserRepository : BaseRepository() {
    open var userListingResponse = MutableLiveData<UserListingResponse>()
    open var sendFriendRequestResponse = MutableLiveData<SendFriendRequestResponse>()
    open var friendRequestResponse = MutableLiveData<FriendRequestResponse>()
    open var reportUserResponse = MutableLiveData<ReportUsertResponse>()
    open var acceptRejectRequestResponse = MutableLiveData<AcceptRejectFriendRequestResponse>()
    open var userProfileResponse = MutableLiveData<UserProfileResponse>()
    open var blockUnblockResponse = MutableLiveData<BlockUnblockUserResponse>()
    open var friendListResponse = MutableLiveData<FriendListResponse>()
    open var eventInvitationResponse = MutableLiveData<EventInvitationResponse>()

    fun callUserProfile(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callUserProfile(req).enqueue(object : retrofit2.Callback<UserProfileResponse> {
                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    hideProgress()
                    var model = UserProfileResponse(4001, t.message, null, null)
//                    model.message = t.message
//                    model.status = 4001
                    userProfileResponse.value = model
                }

                override fun onResponse(call: Call<UserProfileResponse?>, response: Response<UserProfileResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), UserProfileResponse::class.java)
                            userProfileResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        userProfileResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callBlockUser(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callBlockUser(req).enqueue(object : retrofit2.Callback<BlockUnblockUserResponse> {
                override fun onFailure(call: Call<BlockUnblockUserResponse>, t: Throwable) {
                    hideProgress()
                    var model = BlockUnblockUserResponse(4001, t.message, null, null)
                    blockUnblockResponse.value = model
                }

                override fun onResponse(call: Call<BlockUnblockUserResponse?>, response: Response<BlockUnblockUserResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), BlockUnblockUserResponse::class.java)
                            blockUnblockResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        blockUnblockResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callUserList(mContext: Context, req: HashMap<String, String>,showProgress:Boolean) {
        if (isNetworkConnected(mContext)) {
            if(showProgress){
                showProgress(mContext)
            }

            apiInterface.callUserListing(req).enqueue(object : retrofit2.Callback<UserListingResponse> {
                override fun onFailure(call: Call<UserListingResponse>, t: Throwable) {
                    if(showProgress){
                        hideProgress()
                    }

                    var model = SendFriendRequestResponse()
                    model.message = t.message
                    model.status = 4001
                    sendFriendRequestResponse.value = model
                }

                override fun onResponse(call: Call<UserListingResponse?>, response: Response<UserListingResponse>) {
                    if(showProgress){
                        hideProgress()
                    }
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), UserListingResponse::class.java)
                            userListingResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        userListingResponse.value = response.body()
                    }
                }
            })
        }
    }


    fun callSendFriendRequest(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callSendFriendRequest(req).enqueue(object : retrofit2.Callback<SendFriendRequestResponse> {
                override fun onFailure(call: Call<SendFriendRequestResponse>, t: Throwable) {
                    hideProgress()
                    var model = SendFriendRequestResponse()
                    model.message = t.message
                    model.status = 4001
                    sendFriendRequestResponse.value = model
                }

                override fun onResponse(call: Call<SendFriendRequestResponse?>, response: Response<SendFriendRequestResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), SendFriendRequestResponse::class.java)
                            sendFriendRequestResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        sendFriendRequestResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callReceivedFriendRequest(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callFriendRequest(req).enqueue(object : retrofit2.Callback<FriendRequestResponse> {
                override fun onFailure(call: Call<FriendRequestResponse>, t: Throwable) {
                    hideProgress()
                    var model = FriendRequestResponse(4001, t.message ?: "", null, "")
                    friendRequestResponse.value = model
                }

                override fun onResponse(call: Call<FriendRequestResponse?>, response: Response<FriendRequestResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), FriendRequestResponse::class.java)
                            friendRequestResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        friendRequestResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callAcceptRejectRequest(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            //showProgress(mContext)
            apiInterface.callAcceptRejectRequest(req).enqueue(object : retrofit2.Callback<AcceptRejectFriendRequestResponse> {
                override fun onFailure(call: Call<AcceptRejectFriendRequestResponse>, t: Throwable) {
                   // hideProgress()
                    var model = AcceptRejectFriendRequestResponse(4001, t.message ?: "", null, "")
                    acceptRejectRequestResponse.value = model
                }

                override fun onResponse(call: Call<AcceptRejectFriendRequestResponse?>, response: Response<AcceptRejectFriendRequestResponse>) {
                   // hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), AcceptRejectFriendRequestResponse::class.java)
                            acceptRejectRequestResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        acceptRejectRequestResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callReportUser(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callReportUser(req).enqueue(object : retrofit2.Callback<ReportUsertResponse> {
                override fun onFailure(call: Call<ReportUsertResponse>, t: Throwable) {
                    hideProgress()
                    var model = ReportUsertResponse(4001, t.message ?: "", null, "")
                    reportUserResponse.value = model
                }

                override fun onResponse(call: Call<ReportUsertResponse?>, response: Response<ReportUsertResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), ReportUsertResponse::class.java)
                            reportUserResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        reportUserResponse.value = response.body()
                    }
                }
            })
        }
    }

    fun callFriendList(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callFriendList(req).enqueue(object : retrofit2.Callback<FriendListResponse> {
                override fun onFailure(call: Call<FriendListResponse>, t: Throwable) {
                    hideProgress()
                    var model = FriendListResponse(4001, t.message ?: "", null)
                    friendListResponse.value = model
                }

                override fun onResponse(call: Call<FriendListResponse?>, response: Response<FriendListResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), FriendListResponse::class.java)
                            friendListResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        friendListResponse.value = response.body()
                    }
                }
            })
        }
    }

fun callEventInvitation(mContext: Context, req: HashMap<String, String>) {
        if (isNetworkConnected(mContext)) {
            showProgress(mContext)
            apiInterface.callEventInvitation(req).enqueue(object : retrofit2.Callback<EventInvitationResponse> {
                override fun onFailure(call: Call<EventInvitationResponse>, t: Throwable) {
                    hideProgress()
                    var model = FriendListResponse(4001, t.message ?: "", null)
                    friendListResponse.value = model
                }

                override fun onResponse(call: Call<EventInvitationResponse?>, response: Response<EventInvitationResponse>) {
                    hideProgress()
                    if (response.body() == null) {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            var model = Gson().fromJson(jObjError.toString(), EventInvitationResponse::class.java)
                            eventInvitationResponse.value = model
                        } catch (e: Exception) {
                            Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
                        }
                        return
                    } else {
                        eventInvitationResponse.value = response.body()
                    }
                }
            })
        }
    }

}