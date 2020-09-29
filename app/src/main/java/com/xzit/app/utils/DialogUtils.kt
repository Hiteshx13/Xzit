package com.xzit.app.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.xzit.app.R
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnReportDialogClickListener
import com.xzit.app.retrofit.model.response.userlisting.UserListingData
import java.util.*


fun showMessageDialog(context: Context?, strMessage: String?, isCancelable: Boolean?, clickListener: OnDialogClickListener) {
    val mDialog = Dialog(context!!)
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog.setContentView(R.layout.dialog_message)
    mDialog.setCancelable(isCancelable!!)
    mDialog.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
    val tvMessage: AppCompatTextView = mDialog.findViewById(R.id.tvMessage)
    val tvOk: AppCompatTextView = mDialog.findViewById(R.id.tvOK)
    val tvCancel: AppCompatTextView = mDialog.findViewById(R.id.tvCancel)


    tvMessage.text = strMessage
    tvOk.setOnClickListener {
        clickListener.onButtonClicked(true)
        mDialog.dismiss()
    }
    mDialog.show()
}

fun showDatePickerDialog(context: Context?, isCancelable: Boolean?, clickListener: OnDialogClickListener) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_date_picker, null, false)
    builder.setView(view)
    val mDialog: AlertDialog = builder.create()
    mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    var btnSave: AppCompatButton = view.findViewById(R.id.btnSave)
    var calendar: DateRangeCalendarView = view.findViewById(R.id.calendar)
    var strStartDate=""
    var strEndDate=""

    calendar.setCalendarListener(object : CalendarListener {
        override fun onFirstDateSelected(startDate: Calendar) {
            strStartDate=startDate.getTime().toString()
        }

        override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
            strEndDate= endDate.getTime().toString()
        }
    })
    btnSave.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if(strStartDate.isEmpty()&&strEndDate.isEmpty()){
                showToast(context!!,"Please select start and end date")
            }else if(strStartDate.isEmpty()){
                showToast(context!!,"Please select start date")
            }else if(strEndDate.isEmpty()){
                showToast(context!!,"Please select end date")
            }else{
                mDialog.dismiss()
            }

        }
    })
    mDialog.show()
}
fun showTimePickerDialog(context: Context?, isCancelable: Boolean?, clickListener: OnDialogClickListener) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_time_picker, null, false)
    builder.setView(view)
    val mDialog: AlertDialog = builder.create()
    mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    var btnSave: AppCompatButton = view.findViewById(R.id.btnSave)
//    var calendar: DateRangeCalendarView = view.findViewById(R.id.calendar)
    var strStartDate=""
    var strEndDate=""

//    calendar.setCalendarListener(object : CalendarListener {
//        override fun onFirstDateSelected(startDate: Calendar) {
//            strStartDate=startDate.getTime().toString()
//        }
//
//        override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
//            strEndDate= endDate.getTime().toString()
//        }
//    })
    btnSave.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            mDialog.dismiss()

        }
    })
    mDialog.show()
}

fun showMessageDialog(context: Context?, strMessage: String?, isCancelable: Boolean?, strYes: String? = null, strNo: String? = null, clickListener: OnDialogClickListener) {
    val mDialog = Dialog(context!!)
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog.setContentView(R.layout.dialog_message)
    mDialog.setCancelable(isCancelable!!)
    mDialog.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
    val tvMessage: AppCompatTextView = mDialog.findViewById(R.id.tvMessage)
    val tvOk: AppCompatTextView = mDialog.findViewById(R.id.tvOK)
    val tvCancel: AppCompatTextView = mDialog.findViewById(R.id.tvCancel)

    if (strYes?.isEmpty() == false) {
        tvOk.text = strYes
    }

    if (strNo?.isEmpty() == false) {
        tvCancel.visibility = View.VISIBLE
        tvCancel.text = strNo
    }

    tvMessage.text = strMessage
    tvOk.setOnClickListener {
        clickListener.onButtonClicked(true)
        mDialog.dismiss()
    }

    tvCancel.setOnClickListener {
        clickListener.onButtonClicked(false)
        mDialog.dismiss()
    }
    mDialog.show()
}

fun showReportDialog(context: Context?, isCancelable: Boolean?, model: UserListingData, clickListener: OnReportDialogClickListener) {
    val mDialog = Dialog(context!!)
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog.setContentView(R.layout.dialog_report)
    mDialog.setCancelable(isCancelable!!)
    mDialog.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
    val etDescription: AppCompatEditText = mDialog.findViewById(R.id.etDescription)
    val btnSubmitReport: AppCompatButton = mDialog.findViewById(R.id.btnSubmitReport)
    btnSubmitReport.setOnClickListener {
        clickListener.onButtonClicked(model, etDescription.text.toString())
        mDialog.dismiss()
    }
    mDialog.show()
}

fun showProgressDialog(context: Context?) {
    val mDialog = Dialog(context!!)
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog.setContentView(R.layout.dialog_progress)
    mDialog.setCancelable(false)
    mDialog.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
    mDialog.show()
}