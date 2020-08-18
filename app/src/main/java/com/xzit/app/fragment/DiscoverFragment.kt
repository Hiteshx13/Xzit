package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R
import com.xzit.app.activity.CreatEventActivity
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.adapter.ProfileAdapter
import com.xzit.app.databinding.FragmentDiscoverBinding

class DiscoverFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentDiscoverBinding? = null
    private var mAdapter: ProfileAdapter? = null
    private var MyAdapterVenue: RecyclerView.Adapter<*>? = null

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
                val intentSettings = Intent(mContext, CreatEventActivity::class.java)
                startActivity(intentSettings)
            }

        }
    }


}