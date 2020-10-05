package com.xzit.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.xzit.app.R
import com.xzit.app.activity.CreateEventActivity
import com.xzit.app.activity.EditProfileActivity
import com.xzit.app.activity.SettingsActivity
import com.xzit.app.activity.XzitApp
import com.xzit.app.adapter.OngoingEventAdapter
import com.xzit.app.adapter.UpcommingEventAdapter
import com.xzit.app.databinding.FragmentDiscoverBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.repository.EventRepository
import com.xzit.app.retrofit.model.response.eventdata.Completed
import com.xzit.app.retrofit.model.response.eventdata.EventListingResponse
import com.xzit.app.retrofit.model.response.eventdata.Ongoing
import com.xzit.app.retrofit.model.response.eventdata.Upcomming
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.utils.RESP_API_SUCCESS
import com.xzit.app.utils.USER_TYPE_NORMAL
import com.xzit.app.utils.showMessageDialog
import java.util.*

class DiscoverFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentDiscoverBinding? = null
    private var mAdapterOngoing: OngoingEventAdapter? = null
    private var mAdapterUpcomming: UpcommingEventAdapter? = null
    private lateinit var repository: EventRepository
    private var userData: LoginData = XzitApp.getLoginUserData()
    private var listOngoing: List<Ongoing>? = null
    private var listUpcomming: List<Upcomming>? = null
    private var listCompleted: List<Completed>? = null


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

        repository = EventRepository()

        if (userData.userType == USER_TYPE_NORMAL) {
            binding?.btnCreateEvent?.visibility = View.GONE
        }
        initListener()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        callEventListing()
    }
    fun initListener() {
        binding?.btnCreateEvent?.setOnClickListener(this)
        binding?.tvNews?.setOnClickListener(this)
        binding?.tvUpcommingNews?.setOnClickListener(this)
        binding?.rvEvents?.setHasFixedSize(true)
        binding?.rvEvents?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }


    fun initObserver() {
        repository.responsEventListing.observe(this, androidx.lifecycle.Observer<EventListingResponse> { response ->
            if (response != null && response.status == RESP_API_SUCCESS) {
                if (isOnGoingDataAvailable(response.Response?.ongoing)) {
                    listOngoing = response.Response?.ongoing
                    listUpcomming = response.Response?.upcomming
                    listCompleted = response.Response?.completed
                    showOnGoing(response.Response?.ongoing)
                }
            } else {
                binding?.tvNoDataFound?.visibility = View.VISIBLE
                binding?.tvNoDataFound?.setText(response?.message)
                binding?.rvEvents?.visibility = View.GONE
//                showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
            }
        })
    }

    fun showOnGoing(data: List<Ongoing>?) {
        binding?.rvEvents?.adapter = null
        mAdapterOngoing = OngoingEventAdapter(data)
        binding?.rvEvents?.adapter = mAdapterOngoing
    }

    fun showUpcomming(data: List<Upcomming>?) {
        binding?.rvEvents?.adapter = null
        mAdapterUpcomming = UpcommingEventAdapter(data)
        binding?.rvEvents?.adapter = mAdapterUpcomming
    }

    private fun callEventListing() {
        val map = HashMap<String, String>()
        map["postData[requestCase]"] = "eventListing"
        map["postData[clientId]"] = userData.clientId
        map["postData[userId]"] = userData.userId
        map["postData[pageNo]"] = "1"
        repository.callEventListing(mContext, map)


    }

    fun isOnGoingDataAvailable(data: List<Ongoing>?): Boolean {
        if (data?.size ?: 0 > 0) {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvEvents?.visibility = View.VISIBLE

            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvEvents?.visibility = View.GONE
            return false
        }
    }

    fun isUpcommingDataAvailable(data: List<Upcomming>?): Boolean {
        if (data?.size ?: 0 > 0) {
            binding?.tvNoDataFound?.visibility = View.GONE
            binding?.rvEvents?.visibility = View.VISIBLE

            return true
        } else {
            binding?.tvNoDataFound?.visibility = View.VISIBLE
            binding?.rvEvents?.visibility = View.GONE
            return false
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.imgbackscreen -> {


            }
            R.id.tvNews -> {
                //binding?.rvEventsUpcoming?.icn_hide = View.GONE
                if (isOnGoingDataAvailable(listOngoing)) {
                    showOnGoing(listOngoing)
                }
            }
            R.id.tvUpcommingNews -> {
                //binding?.rvEvents?.icn_hide = View.GONE
                if (isUpcommingDataAvailable(listUpcomming)) {
                    showUpcomming(listUpcomming)
                }
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
                val intentSettings = Intent(mContext, CreateEventActivity::class.java)
                startActivity(intentSettings)
            }

        }
    }


}