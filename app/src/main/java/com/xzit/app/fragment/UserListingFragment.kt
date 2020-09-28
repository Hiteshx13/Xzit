package com.xzit.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.DashboardActivity
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.UserListAdapter
import com.xzit.app.databinding.FragmentUserListingBinding
import com.xzit.app.listener.OnButtonClickListener
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnReportDialogClickListener
import com.xzit.app.listener.OnViewClickListener
import com.xzit.app.repository.UserRepository
import com.xzit.app.retrofit.model.response.friendrequest.ReportUsertResponse
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.retrofit.model.response.userlisting.SendFriendRequestResponse
import com.xzit.app.retrofit.model.response.userlisting.UserListingData
import com.xzit.app.retrofit.model.response.userlisting.UserListingResponse
import com.xzit.app.utils.*
import java.util.*

class UserListingFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentUserListingBinding? = null
    private var mAdapter: UserListAdapter? = null
    private lateinit var repository: UserRepository
    private lateinit var userdata: LoginData
    private var selectedPos: Int = 0

    companion object {
        fun newInstance(): UserListingFragment {
            return UserListingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_listing, container, false)
        repository = UserRepository()
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
        binding?.ivBack?.setOnClickListener(this)
        binding?.ivRequest?.setOnClickListener(this)
    }

    private fun setObserver() {
        repository.userListingResponse.observe(mActivity, Observer<UserListingResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                updateUI(response.response)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })

        repository.sendFriendRequestResponse.observe(mActivity, Observer<SendFriendRequestResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                mAdapter?.setRequestStatus(selectedPos)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })
        repository.reportUserResponse.observe(this, Observer<ReportUsertResponse> { response ->

            if (response != null && response.status == RESP_API_SUCCESS || response.status == RESP_API_SUCCESS2) {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })
    }

    private fun updateUI(data: List<UserListingData>) {
        binding?.rrFriend?.setHasFixedSize(true)
        binding?.rrFriend?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mAdapter = UserListAdapter(mActivity, data, object : OnViewClickListener {
            override fun onClick(position: Int) {
                (mActivity as DashboardActivity).addFragment(ProfileFragment.newInstance(data.get(position).userId), true)
            }

        }, object : OnButtonClickListener {
            override fun onClick(position: Int) {
                selectedPos = position
                if (data.get(position).status.equals(STATUS_ACCEPT)) {
                    showMessageDialog(mContext, mActivity.getString(R.string.are_you_sure_to_unfriend), true, getString(R.string.yes), getString(R.string.no), OnDialogClickListener { listener ->
                        if (listener) {
                            callFriendUnfriend(data.get(position))
                        }
                    })
                } else {
                    callFriendUnfriend(data.get(position))
                }


            }
        }, object : OnReportDialogClickListener {
            override fun onButtonClicked(model: UserListingData?, report: String?) {
                showReportDialog(mActivity, true, model!!, OnReportDialogClickListener { model, report ->

                    val map = HashMap<String, String>()
                    map["postData[requestCase]"] = "reportmaster"
                    map["postData[clientId]"] = userdata.clientId
                    map["postData[userId]"] = userdata.userId
                    map["postData[feedback]"] = report
                    map["postData[reportId]"] = model.userId
                    map["postData[reportType]"] = REQ_CASE_USER
                    repository.callReportUser(mContext, map)
                })
            }
        })
        binding?.rrFriend?.adapter = mAdapter
    }

    private fun callFriendUnfriend(model: UserListingData) {
        val map = HashMap<String, String>()

        if (model.status.equals(STATUS_ACCEPT)) {
            map["postData[requestCase]"] = "unfriend"
            map["postData[friendRqtId]"] = model.userId
        } else {
            map["postData[requestCase]"] = "sendFriendsRequest"
            map["postData[friendsUserId]"] = model.userId
        }
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId

        Log.d("#req_sendFriendsRequest", "" + map.toString())

        repository.callSendFriendRequest(mContext, map)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ivBack -> {
                mActivity.onBackPressed()
            }
            R.id.ivRequest -> {
                (mActivity as DashboardActivity).addFragment(FriendRequestFragment.newInstance(), true)
            }
        }
    }

    private fun friendSearchList() {

    }
}