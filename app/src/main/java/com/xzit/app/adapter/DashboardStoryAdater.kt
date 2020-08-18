package com.xzit.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R

class DashboardStoryAdater(var values: List<String>?) : RecyclerView.Adapter<DashboardStoryAdater.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(
                parent.context)

        val v: View = inflater.inflate(R.layout.row_add_story_item, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return values!!.size
    }

    class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}