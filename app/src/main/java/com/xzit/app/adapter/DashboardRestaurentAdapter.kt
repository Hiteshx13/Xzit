package com.xzit.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R

class DashboardRestaurentAdapter(var context: Context, var list: ArrayList<String>?) : RecyclerView.Adapter<DashboardRestaurentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(
                context)
        val v: View = inflater.inflate(R.layout.hotspot_item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = list!![position]
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var imgViewStory: ImageView

        init {
            imgViewStory = layout.findViewById(R.id.imgAdd)
        }
    }

}
