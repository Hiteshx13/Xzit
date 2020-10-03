package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xzit.app.R
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.InvitationAdater
import com.xzit.app.databinding.FragmentInvitationBinding
import com.xzit.app.repository.EventRepository
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.utils.EVENT_INVITATION_STATUS_ACCEPT
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class InvitationFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentInvitationBinding? = null
    private lateinit var invitationAdapter: InvitationAdater
    private lateinit var repository: EventRepository

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
        repository = EventRepository()
        initListener()

        userdata = XzitApp.getLoginUserData()

        callInvitationSent()
        callInvitationReceived()
        //setData()
    }

    fun initListener() {
        binding?.llInvitationSent?.setOnClickListener(this)
        binding?.llInvitationReceived?.setOnClickListener(this)
        binding?.llInvitationSent?.callOnClick()
//        binding?.imgbackscreen?.setOnClickListener(this)
//        binding?.tvEditProfile?.setOnClickListener(this)
//        binding?.ivSettings?.setOnClickListener(this)
//        binding?.llTabGallery?.setOnClickListener(this)
//        binding?.llTabContacts?.setOnClickListener(this)
//        binding?.llTabGallery?.callOnClick()
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
        map["postData[status]"] = EVENT_INVITATION_STATUS_ACCEPT
        repository.callAllReceivedInvitationByUser(mContext, map)
    }

    fun setData() {
        listDummy.add("")
        listDummy.add("")
        listDummy.add("")
        listDummy.add("")
        listDummy.add("")
        listDummy.add("")
        invitationAdapter = InvitationAdater(mContext, listDummy)
        //binding!!.rvInvitation.adapter = invitationAdapter
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

                binding?.llInvitationReceivedData?.visibility = GONE
                binding?.llInvitationSentData?.visibility = VISIBLE
            }
            R.id.llInvitationReceived -> {
                binding?.llInvitationSent?.isSelected = false
                binding?.ivInvitationSent?.isSelected = false
                binding?.tvInvitationSent?.isSelected = false

                binding?.llInvitationReceived?.isSelected = true
                binding?.ivInvitationReceived?.isSelected = true
                binding?.tvInvitationReceived?.isSelected = true

                binding?.llInvitationReceivedData?.visibility = VISIBLE
                binding?.llInvitationSentData?.visibility = GONE
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