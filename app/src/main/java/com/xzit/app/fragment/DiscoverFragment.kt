package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.ChatActivity
import com.xzit.app.activity.CreatEvents
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.adapter.EventListAdapter
import com.xzit.app.databinding.FragmentDiscoverBinding
import java.util.*

class DiscoverFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentDiscoverBinding? = null
    private var mAdapter: EventListAdapter? = null


    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding?.btnCreateEvent?.setOnClickListener(this)
//        binding?.tvEditProfile?.setOnClickListener(this)
//        binding?.ivSettings?.setOnClickListener(this)
//        binding?.llTabGallery?.setOnClickListener(this)
//        binding?.llTabContacts?.setOnClickListener(this)
//        binding?.llTabGallery?.callOnClick()
        binding?.rvEvents?.setHasFixedSize(true)
        binding?.rvEvents?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val input: MutableList<String> = ArrayList()
        for (i in 0..20) {
            input.add("Test$i")
        }
        mAdapter = EventListAdapter(input, object : EventListAdapter.OnChatClickListener {
            override fun onClickChat(pos: Int) {
                startActivity(Intent(mContext, ChatActivity::class.java))
            }
        })
        binding?.rvEvents?.adapter = mAdapter
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

            R.id.btnCreateEvent -> {
                val intentSettings = Intent(mContext, CreatEvents::class.java)
                startActivity(intentSettings)
            }

        }
    }


}