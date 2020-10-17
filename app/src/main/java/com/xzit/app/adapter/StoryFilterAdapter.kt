package com.xzit.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.xzit.app.R
import com.xzit.app.databinding.RowColorFilterBinding


class StoryFilterAdapter(var mContext: Context?, var colorList: ArrayList<Int>):PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
//        val modelObject: ModelObject = ModelObject.values().get(position)
        val inflater = LayoutInflater.from(mContext)
        var rowBinding:RowColorFilterBinding=DataBindingUtil.inflate(
            inflater,
            R.layout.row_color_filter,
            collection,
            false
        )
        //val layout = inflater.inflate(R.layout.row_pager, collection, false) as ViewGroup
        //var image=layout.findViewById<AppCompatImageView>(R.id.ivImage)
        rowBinding.view.setBackgroundColor(colorList.get(position))
        collection.addView(rowBinding.root)
        return rowBinding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
    override fun getCount(): Int {
        return colorList.size
    }



    override fun getPageTitle(position: Int): CharSequence? {
        //val customPagerEnum: ModelObject = ModelObject.values().get(position)
        return ""
    }
}