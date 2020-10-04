package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.adapter.InvitationAdater
import com.xzit.app.adapter.ProfileAdapter
import com.xzit.app.databinding.FragmentInvitationBinding

class InvitationSentFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentInvitationBinding? = null
    private var mAdapter: ProfileAdapter? = null
    private var MyAdapterVenue: RecyclerView.Adapter<*>? = null
    private lateinit var invitationAdapter: InvitationAdater
    var listDummy = ArrayList<String>()

    companion object {
        fun newInstance(): InvitationSentFragment {
            return InvitationSentFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setData()
    }

    fun initListener() {
        binding?.llInvitationSent?.setOnClickListener(this)
        binding?.llInvitationReceived?.setOnClickListener(this)
        binding?.llInvitationSent?.callOnClick()
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


            }
            R.id.llInvitationReceived -> {
                binding?.llInvitationSent?.isSelected = false
                binding?.ivInvitationSent?.isSelected = false
                binding?.tvInvitationSent?.isSelected = false

                binding?.llInvitationReceived?.isSelected = true
                binding?.ivInvitationReceived?.isSelected = true
                binding?.tvInvitationReceived?.isSelected = true

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