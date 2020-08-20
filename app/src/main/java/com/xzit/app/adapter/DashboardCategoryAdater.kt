package com.xzit.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R

class DashboardCategoryAdater(var context: Context, private val values: List<String>) : RecyclerView.Adapter<DashboardCategoryAdater.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(
                context)
        val v = inflater.inflate(R.layout.row_category, parent, false)
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