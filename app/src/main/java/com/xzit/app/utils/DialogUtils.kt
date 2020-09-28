package com.xzit.app.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.xzit.app.R
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnReportDialogClickListener
import com.xzit.app.retrofit.model.response.userlisting.UserListingData

fun showMessageDialog(context: Context?, strMessage: String?, isCancelable: Boolean?,  clickListener: OnDialogClickListener) {
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
        tvOk.setText(strYes)
    }

    if (strNo?.isEmpty() == false) {
        tvCancel.visibility = View.VISIBLE
        tvCancel.setText(strNo)
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