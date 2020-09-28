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
import com.xzit.app.activity.DashboardActivity
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.ProfileAdapter
import com.xzit.app.databinding.FragmentProfileBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.repository.UserRepository
import com.xzit.app.retrofit.model.response.friendrequest.BlockUnblockUserResponse
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.retrofit.model.response.profile.UserProfileData
import com.xzit.app.retrofit.model.response.profile.UserProfileResponse
import com.xzit.app.utils.PARAM_CLIENT_ID
import com.xzit.app.utils.PARAM_USER_ID
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.showMessageDialog
import java.util.*

class ProfileFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentProfileBinding? = null
    private var mAdapter: ProfileAdapter? = null
    private lateinit var repository: UserRepository
    private var userID: String? = null
    private var clientID: String? = null
    val userdata: LoginData = XzitApp.getLoginUserData()
    private var isBlocked = false

    companion object {
        fun newInstance(userID: String? = null, client: String? = null): ProfileFragment {
            val bundle = Bundle()
            bundle.putString(PARAM_USER_ID, userID)
            bundle.putString(PARAM_CLIENT_ID, client)
            var profileFragment = ProfileFragment()
            profileFragment.arguments = bundle
            return profileFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        repository = UserRepository()
        userID = arguments?.getString(PARAM_USER_ID)
        clientID = arguments?.getString(PARAM_CLIENT_ID)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()

        if (userID?.isEmpty() == false) {
            val map = HashMap<String, String>()
            map["postData[requestCase]"] = "userProfileListing"
            map["postData[clientId]"] = clientID ?: userdata.clientId
            map["postData[userId]"] = userID!!
            repository.callUserProfile(mContext, map)

            binding?.tvBlockUser?.visibility = View.VISIBLE
        } else {
            binding?.tvUserName?.text = userdata.username
            binding?.llAddContact?.visibility = View.VISIBLE
            binding?.tvEditProfile?.visibility = View.VISIBLE
            binding?.tvBlockUser?.visibility = View.GONE
        }
    }

    fun initListener() {
        binding?.ivBack?.setOnClickListener(this)
        binding?.tvEditProfile?.setOnClickListener(this)
        binding?.ivSettings?.setOnClickListener(this)
        binding?.llTabGallery?.setOnClickListener(this)
        binding?.llTabContacts?.setOnClickListener(this)
        binding?.llAddContact?.setOnClickListener(this)
        binding?.tvBlockUser?.setOnClickListener(this)
        binding?.llTabGallery?.callOnClick()
    }

    fun initObserver() {
        repository.userProfileResponse.observe(this, Observer<UserProfileResponse> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                updateView(response.Response)
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener {

                })
            }
        })
        repository.blockUnblockResponse.observe(this, Observer<BlockUnblockUserResponse> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                showMessageDialog(mContext, response.message, true, OnDialogClickListener {
                    isBlocked = !isBlocked
                    if (isBlocked) {
                        binding?.tvBlockUser?.text = getString(R.string.unblock_user)
                    } else {
                        binding?.tvBlockUser?.text = getString(R.string.block_user)
                    }

                })
            } else {
                showMessageDialog(mContext, response?.message, true, OnDialogClickListener {

                })
            }
        })
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ivBack -> {
                mActivity.onBackPressed()
            }

            R.id.tvBlockUser -> {

                var msg = mActivity.getString(R.string.are_you_sure_to_block_this_user)
                if (isBlocked) {
                    msg = mActivity.getString(R.string.are_you_sure_to_un_block_this_user)
                }

                showMessageDialog(mContext, msg, true, getString(R.string.yes), getString(R.string.no), OnDialogClickListener { listener ->
                    if (listener) {
                        val map = HashMap<String, String>()
                        map["postData[requestCase]"] = "blockUnblockUser"
                        map["postData[clientId]"] = userdata.clientId
                        map["postData[userId]"] = userdata.userId
                        map["postData[friendRqtId]"] = userID!!
                        if (isBlocked) {
                            map["postData[status]"] = "ACCEPT"
                        } else {
                            map["postData[status]"] = "BLOCK"
                        }

                        repository.callBlockUser(mContext, map)
                    }
                })
            }
            R.id.llAddContact -> {
                (mActivity as DashboardActivity).addFragment(UserListingFragment.newInstance(), true)
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
            R.id.llTabGallery -> {
                binding?.viewGallery?.isSelected = true
                binding?.viewContact?.isSelected = false

                binding?.ivTabContact?.isSelected = false
                binding?.ivTabGallery?.isSelected = true


            }
            R.id.llTabContacts -> {
                binding?.viewGallery?.isSelected = false
                binding?.viewContact?.isSelected = true

                binding?.ivTabContact?.isSelected = true
                binding?.ivTabGallery?.isSelected = false

            }
        }
    }

    fun updateView(model: UserProfileData?) {
        binding?.tvUserName?.text = model?.fullname
    }

    fun music() {
        binding?.rrmusic?.setHasFixedSize(true)
        binding?.rrmusic?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        val input: MutableList<String> = ArrayList()
        for (i in 0..99) {
            input.add("Test$i")
        }
        mAdapter = ProfileAdapter(input)
        binding?.rrmusic?.adapter = mAdapter
    }

}