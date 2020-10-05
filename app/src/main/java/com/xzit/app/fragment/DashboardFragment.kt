package com.xzit.app.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandrlab.widgets.MetalRecyclerViewPager
import com.xzit.app.R
import com.xzit.app.activity.DashboardActivity
import com.xzit.app.activity.FullMetalAdapter
import com.xzit.app.activity.XzitApp.getLoginUserData
import com.xzit.app.activity.XzitApp.preference
import com.xzit.app.adapter.DashboardCategoryAdater
import com.xzit.app.adapter.DashboardRestaurentAdapter
import com.xzit.app.adapter.RestaurentAdapter
import com.xzit.app.databinding.FragmentDashboardBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.retrofit.model.response.login.LoginResponse
import com.xzit.app.retrofit.model.response.masterdata.Subtype
import com.xzit.app.utils.ImageUtils
import com.xzit.app.utils.showMessageDialog
import java.util.*

class DashboardFragment : BaseFragment(), View.OnClickListener {

    var binding: FragmentDashboardBinding? = null
    var categoryAdapter: DashboardCategoryAdater? = null
    var restaurentAdapter: DashboardRestaurentAdapter? = null
    var listCategory: List<Subtype>? = null
    val userdata: LoginData = getLoginUserData()
    val listDummy: ArrayList<String> = ArrayList()

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..99) {
            listDummy.add("Test$i")
        }
        binding?.llProfile?.setOnClickListener(this)
        binding?.llAddContact?.setOnClickListener(this)


        binding?.tvUserName?.text = userdata.username
        setCategory()
        setVenue()
        setRestaurentData()
        setMostViewed()
        if(userdata.profilePic.size>0){
            ImageUtils().loadImage(mActivity, userdata.profilePic.get(0), binding?.ivProfile!!);
        }

    }


    private fun setCategory() {
        listCategory = preference.getMasterData(mContext).Response?.get(3)?.subtype
        binding!!.rvCategory.setHasFixedSize(true)
        binding!!.rvCategory.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = DashboardCategoryAdater(mContext, listCategory)
        binding!!.rvCategory.adapter = categoryAdapter
    }

    private fun setRestaurentData() {
        binding?.rvRestaurents?.setHasFixedSize(true)

        binding?.rvRestaurents?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvMostViewed?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        var restaurentAdapter = RestaurentAdapter(listDummy)
        binding!!.rvRestaurents.adapter = restaurentAdapter
    }

    private fun setMostViewed() {
        binding?.rvMostViewed?.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        var mostViewed = RestaurentAdapter(listDummy)
        binding!!.rvMostViewed.adapter = mostViewed

    }

    private fun setVenue() {
        val metrics: DisplayMetrics = getDisplayMetrics()
        val metalList = Arrays.asList("\\m/", "\\m/", "\\m/")
        val fullMetalAdapter = FullMetalAdapter(metrics, metalList)
        val viewPager: MetalRecyclerViewPager = binding!!.viewPager
        viewPager.adapter = fullMetalAdapter
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val display: Display? = activity?.windowManager?.defaultDisplay
        val metrics = DisplayMetrics()
        display?.getMetrics(metrics)
        return metrics
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llProfile -> {
//                SnapTimePickerDialog.Builder().apply {
//                useViewModel()
//            }.build().show(mActivity.supportFragmentManager, SnapTimePickerDialog.TAG)
//
//            SnapTimePickerUtil.observe(this) { selectedHour: Int, selectedMinute: Int ->
//                Log.d(""+selectedHour,""+selectedMinute);
//            }
                (mActivity as DashboardActivity).addFragment(ProfileFragment.newInstance(), true)
            }
            R.id.llAddContact -> {
                (mActivity as DashboardActivity).addFragment(UserListingFragment.newInstance(), true)
            }
        }
    }

    /*class MyAdapterHotspot(private val values: List<String>) : RecyclerView.Adapter<MyAdapterHotspot.ViewHolder?>() {
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(
                    parent.context)
            val v = inflater.inflate(R.layout.hotspot_item_view, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val name = values[position]
        }

        override fun getItemCount(): Int {
            return values.size
        }

        inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
            var imgViewStory: ImageView

            init {
                imgViewStory = layout.findViewById(R.id.imgAdd)
            }
        }

    }
*/
}