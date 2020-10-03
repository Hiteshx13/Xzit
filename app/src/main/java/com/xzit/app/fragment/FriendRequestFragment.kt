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
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.FriendRequestAdapter
import com.xzit.app.databinding.FragmentFriendRequestBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnFriendRequestClickListener
import com.xzit.app.repository.UserRepository
import com.xzit.app.retrofit.model.response.friendrequest.AcceptRejectFriendRequestResponse
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestData
import com.xzit.app.retrofit.model.response.friendrequest.FriendRequestResponse
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.RESP_API_SUCCESS2
import com.xzit.app.utils.showMessageDialog
import java.util.*
import kotlin.collections.ArrayList

class FriendRequestFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentFriendRequestBinding? = null
    private var mAdapter: FriendRequestAdapter? = null
    private lateinit var repository: UserRepository
    private lateinit var userdata: LoginData
    private var selectedPos = 0

    companion object {
        fun newInstance(): FriendRequestFragment {
            return FriendRequestFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend_request, container, false)
        repository = UserRepository()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
        friendSearchList()

        val map = HashMap<String, String>()
        userdata = XzitApp.getLoginUserData()

        map["postData[requestCase]"] = "receivedFriendsRequestList"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        repository.callReceivedFriendRequest(mContext, map)
    }

    fun initListener() {
        binding?.ivBack?.setOnClickListener(this)
    }

    fun initObserver() {
        repository.friendRequestResponse.observe(this, Observer<FriendRequestResponse> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                if (isDataAvailable(response.Response)) {

//                    binding?.tvNoDataFound?.icn_hide = View.GONE
//                    binding?.rvFriendRequest?.icn_hide = View.VISIBLE

//                    var list = response.Response as ArrayList<FriendRequestData>
//                    list.addAll(response.Response as ArrayList)
//                    list.addAll(response.Response as ArrayList)
//                    list.addAll(response.Response as ArrayList)

                    mAdapter = FriendRequestAdapter(response.Response, object : OnFriendRequestClickListener {
                        override fun onClick(position: Int, isAccept: Boolean) {
                            selectedPos = position

                            var model = response.Response?.get(position)
                            val map = HashMap<String, String>()
                            map["postData[requestCase]"] = "acceptRejectFriendsRequest"
                            map["postData[clientId]"] = userdata.clientId
                            map["postData[userId]"] = userdata.userId
                            map["postData[friendsRequestId]"] = model!!.pkRequestId
                            map["postData[friendsUserId]"] = model.receivedRequestFromId
                            if (isAccept) {
                                map["postData[status]"] = "ACCEPT"
                            } else {
                                map["postData[status]"] = "REJECT"
                            }
                            Log.d("Params:", "" + map.toString())
                            repository.callAcceptRejectRequest(mContext, map)
                        }
//{postData[userId]=46, postData[friendsUserId]=44, postData[status]=REJECT, postData[friendsRequestId]=52, postData[clientId]=1, postData[requestCase]=acceptRejectFriendsRequest}
//                        override fun onClick(pos: Int) {
//                            startActivity(Intent(mContext, ChatActivity::class.java))
//                        }
                    })
                    binding?.rvFriendRequest?.adapter = mAdapter
                }
//                else {
//                    binding?.tvNoDataFound?.icn_hide = View.VISIBLE
//                    binding?.rvFriendRequest?.icn_hide = View.GONE
//                }

            } else {
                binding?.tvNoDataFound?.visibility = View.VISIBLE
                binding?.tvNoDataFound?.text = response?.message
                binding?.rvFriendRequest?.visibility = View.GONE
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })

        repository.acceptRejectRequestResponse.observe(this, Observer<AcceptRejectFriendRequestResponse> { response ->

            if (response != null && response.status == RESP_API_SUCCESS||response.status == RESP_API_SUCCESS2) {
                mAdapter?.removeItemAt(selectedPos)
                isDataAvailable(mAdapter?.list)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }


        })

    }

    fun isDataAvailable(data: List<FriendRequestData>?): Boolean {
        if (data?.size ?: 0 > 0) {

            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvFriendRequest?.visibility = View.VISIBLE

            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvFriendRequest?.visibility = View.GONE
            return false
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ivBack -> {
                mActivity.onBackPressed()
            }
        }
    }

    private fun friendSearchList() {
        binding?.rvFriendRequest?.setHasFixedSize(true)
        binding?.rvFriendRequest?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val input: MutableList<String> = ArrayList()
        for (i in 0..20) {
            input.add("Test$i")
        }

        binding?.rvFriendRequest?.adapter = mAdapter
    }
}