package com.xzit.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.UserListAdapter
import com.xzit.app.databinding.FragmentChatBinding
import com.xzit.app.listener.OnButtonClickListener
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.repository.UserListRepository
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.retrofit.model.response.userlisting.UserListingData
import com.xzit.app.retrofit.model.response.userlisting.UserListingResponse
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.showMessageDialog
import java.util.*

class UserListingFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentChatBinding? = null
    private var mAdapter: UserListAdapter? = null
    private lateinit var repository: UserListRepository
    private lateinit var userdata: LoginData

    companion object {
        fun newInstance(): UserListingFragment {
            return UserListingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        repository = UserListRepository()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        friendSearchList()
        setObserver()

        val map = HashMap<String, String>()
        userdata = XzitApp.getLoginUserData()

        map["postData[requestCase]"] = "getAllUserList"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        repository.callUserList(mContext, map)
    }

    fun initListener() {
    }

    private fun setObserver() {
        repository.apiData.observe(this, Observer<UserListingResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                updateUI(response.response)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })
    }

    private fun updateUI(data: List<UserListingData>) {
        binding?.rrFriend?.setHasFixedSize(true)
        binding?.rrFriend?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAdapter = UserListAdapter(data, object : OnButtonClickListener {
            override fun onClick(position: Int) {
                callFriendUnfriend(data.get(position).userId)
            }
        })
        binding?.rrFriend?.adapter = mAdapter
    }

    private fun callFriendUnfriend(friendsUserId: String) {
        val map = HashMap<String, String>()
        userdata = XzitApp.getLoginUserData()

        map["postData[requestCase]"] = "sendFriendsRequest"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        map["postData[friendsUserId]"] = friendsUserId
        repository.callFriendUnfriend(mContext, map)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.imgbackscreen -> {

            }
        }
    }

    private fun friendSearchList() {

    }
}