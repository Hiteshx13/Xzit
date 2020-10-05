package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.EventInvitationReceivedAdapter
import com.xzit.app.adapter.EventInvitationSentAdapter
import com.xzit.app.databinding.FragmentInvitationBinding
import com.xzit.app.listener.OnAcceptRejectClicked
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.repository.EventRepository
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponse
import com.xzit.app.retrofit.model.response.eventinvitation.EventInvitationAcceptReject
import com.xzit.app.retrofit.model.response.eventinvitationreceived.EventInvitationReceivedData
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationReceived
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationSent
import com.xzit.app.retrofit.model.response.eventinvitationsent.EventInvitationSentData
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.utils.EVENT_INVITATION_STATUS_ACCEPT
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.RESP_API_SUCCESS2
import com.xzit.app.utils.showMessageDialog
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class InvitationFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentInvitationBinding? = null
    private lateinit var adapterInvitationSent: EventInvitationSentAdapter
    private lateinit var adapterInvitationReceived: EventInvitationReceivedAdapter
    private lateinit var repository: EventRepository
    private var position = 0

    private lateinit var userdata: LoginData
    var listDummy = ArrayList<String>()

    companion object {
        fun newInstance(): InvitationFragment {
            return InvitationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userdata = XzitApp.getLoginUserData()
        repository = EventRepository()
        binding?.rvInvitations?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        initListener()
        initObserver()
//        callInvitationSent()
        //callInvitationReceived()

    }

    fun initListener() {
        binding?.llInvitationSent?.setOnClickListener(this)
        binding?.llInvitationReceived?.setOnClickListener(this)
        binding?.llInvitationSent?.callOnClick()
    }

    fun initObserver() {
        repository.responseEventInvitationSent.observe(this, Observer<EventInvitationSent> { response ->

            if (response != null && response.status == RESP_API_SUCCESS || response.status == RESP_API_SUCCESS2) {
                if (isInvitationSentDataAvailable(response.Response)) {
                    updateUISent(response.Response!!)
                }
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }

        })
        repository.responseEventInvitationReceived.observe(this, Observer<EventInvitationReceived> { response ->

            if (response != null && response.status == RESP_API_SUCCESS || response.status == RESP_API_SUCCESS2) {
                if (isInvitationReceivedDataAvailable(response?.Response!!)) {
                    updateUIReceived(response.Response!!)
                }
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }

        })

        repository.responsEventAcceptReject.observe(this, Observer<EventInvitationAcceptReject> { response ->

            if (response != null && response.status == RESP_API_SUCCESS || response.status == RESP_API_SUCCESS2) {
                adapterInvitationReceived.removeItem(position)
                isInvitationReceivedDataAvailable(adapterInvitationReceived.listData)
//                if (isInvitationReceivedDataAvailable(response.Response)) {
//                    updateUIReceived(response.Response!!)
//                }
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }

        })

    }

    private fun updateUISent(data: List<EventInvitationSentData?>) {

        //binding?.rvInvitations?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        adapterInvitationSent = EventInvitationSentAdapter(data)
        binding?.rvInvitations?.adapter = null
        binding?.rvInvitations?.adapter = adapterInvitationSent
    }

    private fun updateUIReceived(data: List<EventInvitationReceivedData?>?) {


        //binding?.rvInvitations?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        adapterInvitationReceived = EventInvitationReceivedAdapter(mContext,data!!, object : OnAcceptRejectClicked {
            override fun onClick(pos: Int, isAccept: Boolean,model: EventInvitationReceivedData) {
                // isInvitationReceivedDataAvailable(null)
               // var model:EventInvitationReceivedData=data.get(position)
                position=pos
                var status = "REJECT"
                if (isAccept) {
                    status = "ACCPET"
                }
                val map = HashMap<String, String>()
                map["postData[requestCase]"] = "acceptRejectEventInvitation"
                map["postData[clientId]"] = userdata.clientId
                map["postData[userId]"] = userdata.userId
                map["postData[eventInvitationId]"] = model.eventInviId!!
                map["postData[eventId]"] = model.eventId!!
                map["postData[status]"] = status
                repository.callAcceptRejectEventInvitation(mContext, map)
            }
        })
        binding?.rvInvitations?.adapter = null
        binding?.rvInvitations?.adapter = adapterInvitationReceived
//        binding?.notifyChange()
    }


    fun isInvitationSentDataAvailable(data: List<EventInvitationSentData?>?): Boolean {
        if (data?.size ?: 0 > 0) {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvInvitations?.visibility = View.VISIBLE
            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvInvitations?.visibility = View.GONE
            return false
        }
    }

    fun isInvitationReceivedDataAvailable(data: List<EventInvitationReceivedData?>?): Boolean {
        if (data?.size ?: 0 > 0) {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvInvitations?.visibility = View.VISIBLE
            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvInvitations?.visibility = View.GONE
            return false
        }
    }

    fun callInvitationSent() {
        val map = HashMap<String, String>()
        map["postData[requestCase]"] = "getAllSendInvitationByUser"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
        repository.callAllSendInvitationByUser(mContext, map)
    }

    fun callInvitationReceived() {
        val map = HashMap<String, String>()
        map["postData[requestCase]"] = "getAllReceivedInvitationToUser"
        map["postData[clientId]"] = userdata.clientId
        map["postData[userId]"] = userdata.userId
       map["postData[status]"] = "ACCEPT,REJECT,PENDING"
        repository.callAllReceivedInvitationByUser(mContext, map)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.llInvitationSent -> {

                binding?.llInvitationSent?.isSelected = true
                binding?.ivInvitationSent?.isSelected = true
                binding?.tvInvitationSent?.isSelected = true

                binding?.llInvitationReceived?.isSelected = false
                binding?.ivInvitationReceived?.isSelected = false
                binding?.tvInvitationReceived?.isSelected = false
                callInvitationSent()
            }
            R.id.llInvitationReceived -> {
                binding?.llInvitationSent?.isSelected = false
                binding?.ivInvitationSent?.isSelected = false
                binding?.tvInvitationSent?.isSelected = false

                binding?.llInvitationReceived?.isSelected = true
                binding?.ivInvitationReceived?.isSelected = true
                binding?.tvInvitationReceived?.isSelected = true
                callInvitationReceived()
            }

            R.id.tvEditProfile
            -> {
                val intentEditprofile = Intent(mContext, EditProfileActivity::class.java)
                startActivity(intentEditprofile)
            }
            R.id.ivSettings -> {
                val intentSettings = Intent(mContext, SettingsActivity::class.java)
                startActivity(intentSettings)
            }

        }
    }


}