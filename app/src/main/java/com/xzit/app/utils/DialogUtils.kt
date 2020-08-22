package com.xzit.app.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.appcompat.widget.AppCompatTextView
import com.xzit.app.R
import com.xzit.app.listener.OnDialogClickListener

fun showMessageDialog(context: Context?, strMessage: String?, isCancelable: Boolean?, clickListener: OnDialogClickListener) {
    val mDialog = Dialog(context!!)
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog.setContentView(R.layout.dialog_message)
    mDialog.setCancelable(isCancelable!!)
    mDialog.window!!.setBackgroundDrawableResource(R.color.colorTransparent)
    val tvMessage: AppCompatTextView = mDialog.findViewById(R.id.tvMessage)
    val tvOk: AppCompatTextView = mDialog.findViewById(R.id.tvOK)
    tvMessage.text = strMessage
    tvOk.setOnClickListener {
        clickListener.onButtonClicked(true)
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