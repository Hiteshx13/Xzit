package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.adapter.ProfileAdapter
import com.xzit.app.databinding.FragmentProfileBinding
import java.util.*

class ProfileFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentProfileBinding? = null
    private var mAdapter: ProfileAdapter? = null
    private var MyAdapterVenue: RecyclerView.Adapter<*>? = null

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding?.imgbackscreen?.setOnClickListener(this)
        binding?.tvEditProfile?.setOnClickListener(this)
        binding?.ivSettings?.setOnClickListener(this)
        binding?.llTabGallery?.setOnClickListener(this)
        binding?.llTabContacts?.setOnClickListener(this)
        binding?.llTabGallery?.callOnClick()
    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.imgbackscreen -> {
                // (mActivity as ProfileActivity).
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