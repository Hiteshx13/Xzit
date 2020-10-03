package com.xzit.app.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
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

        userdata = XzitApp.getLoginUserData()

        callUserListing("")
    }

    fun initListener() {
        binding?.ivBack?.setOnClickListener(this)
        binding?.ivRequest?.setOnClickListener(this)
        binding?.etSearch?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callUserListing(binding?.etSearch?.getText().toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
        binding?.etSearch?.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
               if(s.toString().trim().length==0){
                   callUserListing("")
               }
            }
        })
    }

    private fun setObserver() {
        repository.userListingResponse.observe(mActivity, Observer<UserListingResponse?> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                updateUI(response.response)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
                updateUI(null)
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

    fun callUserListing(keyword: String?) {
        val map = HashMap<String, String>()
        map["postData[requestCase]"] = "getAllUserList"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        map["postData[keyword]"] = keyword?:""

        Log.d("#Params",""+map.toString())
        repository.callUserList(mContext, map)
    }

    private fun updateUI(data: List<UserListingData>?) {

        if (data==null || data.size == 0) {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvUsers?.visibility = View.GONE
        } else {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvUsers?.visibility = View.VISIBLE

            binding?.rvUsers?.setHasFixedSize(true)
            binding?.rvUsers?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
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
            binding?.rvUsers?.adapter = mAdapter
        }
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