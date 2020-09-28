package com.akexorcist.snaptimepicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TimePickerAdapter : RecyclerView.Adapter<TimeNumberViewHolder>() {
    private var itemList: List<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TimeNumberViewHolder =
            TimeNumberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_snap_time_picker_number_item, parent, false))

    override fun getItemCount(): Int = itemList?.size ?: 0

    override fun onBindViewHolder(holder: TimeNumberViewHolder, position: Int) {
        val item = itemList?.get(position)
        holder.setNumber(item?.toString()?.padStart(2, '0'))
    }

    fun setItemList(itemList: List<Int>?) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getPositionByValue(value: Int): Int {
        itemList?.forEachIndexed { index, item ->
            if (value == item) {
                return index
            }
        }
        return -1
    }

    fun getValueByPosition(position: Int): Int {
        itemList?.forEachIndexed { index, item ->
            if (position == index) {
                return item
            }
        }
        return -1
    }
}