package com.xzit.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.xzit.app.R
import com.xzit.app.retrofit.model.response.masterdata.Subtype

class DashboardCategoryAdater(var context: Context, private val listCategory: List<Subtype>?) : RecyclerView.Adapter<DashboardCategoryAdater.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(
                context)
        val v = inflater.inflate(R.layout.row_category, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Subtype? = listCategory?.get(position) ?: null
        holder.tvCategoryName.text =model?.displayValue
    }

    override fun getItemCount(): Int {
        return listCategory?.size ?: 0
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var ivCategory: AppCompatImageView
        var tvCategoryName: AppCompatTextView

        init {
            ivCategory = layout.findViewById(R.id.ivCategory)
            tvCategoryName = layout.findViewById(R.id.tvCategoryName)
        }
    }
}