package com.xzit.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.adapter.FriendListAdapter
import com.xzit.app.databinding.ActivityEventInvteFriendsBinding
import com.xzit.app.listener.OnAddRemoveFriendListener
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.repository.UserRepository
import com.xzit.app.retrofit.model.response.eventinvitation.EventInvitationResponse
import com.xzit.app.retrofit.model.response.friendlist.FriendListData
import com.xzit.app.retrofit.model.response.friendlist.FriendListResponse
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.utils.PARAM_EVENT_ID
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.showMessageDialog
import java.util.*

class EventInviteFriendsActivity : BaseActivity(), View.OnClickListener {

    var binding: ActivityEventInvteFriendsBinding? = null
    private var mAdapter: FriendListAdapter? = null
    private lateinit var repository: UserRepository
    private lateinit var userdata: LoginData
    private var selectedPos: Int = 0
    private var eventID: String? = ""

    companion object {
        fun getIntent(context: Context, eventID: String): Intent {
            var intent = Intent(context, EventInviteFriendsActivity::class.java)
            intent.putExtra(PARAM_EVENT_ID, eventID)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_invte_friends)
        repository = UserRepository()
        eventID = intent.getStringExtra(PARAM_EVENT_ID)
        initListener()
        friendSearchList()
        setObserver()

        userdata = XzitApp.getLoginUserData()

        val map = HashMap<String, String>()
        map["postData[requestCase]"] = "getFriendsList"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        repository.callFriendList(mContext, map)
    }


    fun initListener() {
        binding?.ivBack?.setOnClickListener(this)
        binding?.btnSendInvitation?.setOnClickListener(this)
//        binding?.etSearch?.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if(s.toString().trim().length==0){
//                    callUserListing("",false)
//                }
//            }
//        })
    }

    private fun setObserver() {
        repository.friendListResponse.observe(this, Observer<FriendListResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                if (isDataAvailable(response.Response)) {
                    updateUI(response.Response)
                }
            } else {
                binding?.tvNoDataFound?.visibility = View.VISIBLE
                binding?.tvNoDataFound?.text = response?.message
                binding?.rvFriends?.visibility = View.GONE
                binding?.btnSendInvitation?.visibility = View.GONE
                //showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })

        repository.eventInvitationResponse.observe(this, Observer<EventInvitationResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
//                showMessageDialog(mContext, response.message, true, OnDialogClickListener {
//
//                })
                finish()
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })
    }

    fun isDataAvailable(data: List<FriendListData>?): Boolean {
        if (data?.size ?: 0 > 0) {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvFriends?.visibility = View.VISIBLE
            binding?.btnSendInvitation?.visibility = View.VISIBLE

            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvFriends?.visibility = View.GONE
            binding?.btnSendInvitation?.visibility = View.GONE
            return false
        }
    }

    private fun updateUI(data: List<FriendListData>?) {

        binding?.rvFriends?.setHasFixedSize(true)
        binding?.rvFriends?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAdapter = FriendListAdapter(this, data, object : OnAddRemoveFriendListener {
            override fun onClick(position: Int, model: FriendListData) {
                selectedPos = position
            }
        })
        binding?.rvFriends?.adapter = mAdapter
    }

    private fun callEventInvitationToUser() {
        if (mAdapter!!.selectedUsers != null) {
            val map = HashMap<String, String>()
            map["postData[requestCase]"] = "sendEventInvitationToUsers"
            map["postData[clientId]"] = userdata.clientId
            map["postData[userId]"] = userdata.userId
            map["postData[userList]"] = mAdapter!!.selectedUsers
            map["postData[eventId]"] = eventID?:""
            Log.d("#params", "" + map.toString())
            repository.callEventInvitation(mContext, map)
        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ivBack -> {
                this.onBackPressed()
            }
            R.id.btnSendInvitation -> {
                callEventInvitationToUser()
            }
        }
    }

    private fun friendSearchList() {

    }
}