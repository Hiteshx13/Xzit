package com.xzit.app.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import com.xzit.app.R
import com.xzit.app.activity.XzitApp
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnMultiDateSelectedListener
import com.xzit.app.listener.OnReportDialogClickListener
import com.xzit.app.listener.OnTimeSelectedListener
import com.xzit.app.retrofit.model.request.createevent.EventTime
import com.xzit.app.retrofit.model.request.createevent.EventTimeRow
import com.xzit.app.retrofit.model.response.userlisting.UserListingData
import java.util.*
import kotlin.collections.ArrayList


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

fun showDatePickerDialog(context: Context?, isCancelable: Boolean?, listener: OnMultiDateSelectedListener) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_date_picker, null, false)
    builder.setView(view)
    val mDialog: AlertDialog = builder.create()
    mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    var btnSave: AppCompatButton = view.findViewById(R.id.btnSave)
    var calendar: DateRangeCalendarView = view.findViewById(R.id.calendar)
    var strStartDate = ""
    var strEndDate = ""
    XzitApp.getLoginUserData()

    calendar.setCalendarListener(object : CalendarListener {
        override fun onFirstDateSelected(startDate: Calendar) {
            strStartDate = getStringDate(startDate.time)
        }

        override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
            strEndDate =  getStringDate(endDate.time)
        }
    })
    btnSave.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (strStartDate.isEmpty() && strEndDate.isEmpty()) {
                showToast(context!!, "Please select start and end date")
            } else if (strStartDate.isEmpty()) {
                showToast(context!!, "Please select start date")
            } else if (strEndDate.isEmpty()) {
                showToast(context!!, "Please select end date")
            } else {
                listener.onDateSelected(strStartDate,strEndDate)
                mDialog.dismiss()
            }

        }
    })
    mDialog.show()
}

fun showTimePickerDialog(context: Context?, isCancelable: Boolean?, clickListener: OnTimeSelectedListener) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_time_picker, null, false)
    builder.setView(view)
    val mDialog: AlertDialog = builder.create()
    mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    var btnSave: AppCompatButton = view.findViewById(R.id.btnSave)
    var cbEveryday: CheckBox = view.findViewById(R.id.cbEveryday)
    var cbMonday: CheckBox = view.findViewById(R.id.cbMonday)
    var cbTuesday: CheckBox = view.findViewById(R.id.cbTuesday)
    var cbWednesday: CheckBox = view.findViewById(R.id.cbWednesday)
    var cbThursday: CheckBox = view.findViewById(R.id.cbThursday)
    var cbFriday: CheckBox = view.findViewById(R.id.cbFriday)
    var cbSaturday: CheckBox = view.findViewById(R.id.cbSaturday)
    var cbSunday: CheckBox = view.findViewById(R.id.cbSunday)
    var pSEvery: SingleDateAndTimePicker = view.findViewById(R.id.pSEvery)
    var pEEvery: SingleDateAndTimePicker = view.findViewById(R.id.pEEvery)

    var pSMon: SingleDateAndTimePicker = view.findViewById(R.id.pSMon)
    var pEMon: SingleDateAndTimePicker = view.findViewById(R.id.pEMon)

    var pSTue: SingleDateAndTimePicker = view.findViewById(R.id.pSTue)
    var pETue: SingleDateAndTimePicker = view.findViewById(R.id.pETue)

    var pSWed: SingleDateAndTimePicker = view.findViewById(R.id.pSWed)
    var pEWed: SingleDateAndTimePicker = view.findViewById(R.id.pEWed)

    var pSThu: SingleDateAndTimePicker = view.findViewById(R.id.pSThu)
    var pEThu: SingleDateAndTimePicker = view.findViewById(R.id.pEThu)

    var pSFri: SingleDateAndTimePicker = view.findViewById(R.id.pSFri)
    var pEFri: SingleDateAndTimePicker = view.findViewById(R.id.pEFri)

    var pSSat: SingleDateAndTimePicker = view.findViewById(R.id.pSSat)
    var pESat: SingleDateAndTimePicker = view.findViewById(R.id.pESat)

    var pSSun: SingleDateAndTimePicker = view.findViewById(R.id.pSSun)
    var pESun: SingleDateAndTimePicker = view.findViewById(R.id.pESun)


    cbEveryday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (isChecked) {
                cbMonday.isChecked = true
                cbTuesday.isChecked = true
                cbWednesday.isChecked = true
                cbThursday.isChecked = true
                cbFriday.isChecked = true
                cbSaturday.isChecked = true
                cbSunday.isChecked = true
            }
        }
    })

    cbMonday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbTuesday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbWednesday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbThursday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbFriday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbSaturday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })
    cbSunday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if (!isChecked) {
                cbEveryday.isChecked = false
            }
        }
    })

    btnSave.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            var arrayEventTime = HashMap<String, ArrayList<EventTimeRow>>()

            if (cbEveryday.isChecked) {
                var arrayTime = ArrayList<EventTime>()
                var arrayTimeDay = ArrayList<EventTimeRow>()
                arrayTime.add(EventTime(getTimeHM(pSEvery.date), getTimeHM(pEEvery.date)))
                arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                arrayEventTime.put(context.getString(R.string.everyday).toLowerCase(), arrayTimeDay)

            } else {
                if (cbMonday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSMon.date), getTimeHM(pEMon.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.monday).toLowerCase(), arrayTimeDay)
                }

                if (cbTuesday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSTue.date), getTimeHM(pETue.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.tuesday).toLowerCase(), arrayTimeDay)
                }

                if (cbWednesday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSWed.date), getTimeHM(pEWed.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.wednesday).toLowerCase(), arrayTimeDay)
                }

                if (cbThursday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSThu.date), getTimeHM(pEThu.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.thursday).toLowerCase(), arrayTimeDay)
                }
                if (cbFriday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSFri.date), getTimeHM(pEFri.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.friday).toLowerCase(), arrayTimeDay)
                }
                if (cbSaturday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSSat.date), getTimeHM(pESat.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.saturday).toLowerCase(), arrayTimeDay)
                }

                if (cbSunday.isChecked) {
                    var arrayTime = ArrayList<EventTime>()
                    var arrayTimeDay = ArrayList<EventTimeRow>()
                    arrayTime.add(EventTime(getTimeHM(pSSun.date), getTimeHM(pESun.date)))
                    arrayTimeDay.add(EventTimeRow(context!!.getString(R.string.on), arrayTime))
                    arrayEventTime.put(context.getString(R.string.sunday).toLowerCase(), arrayTimeDay)
                }
            }

            var strTime = arrayEventTime.toString().replace("EventTimeRow(", "")
                    .replace("EventTime(", "")
                    .replace(")])", "]")
                    .replace("[", "{")
                    .replace("]", "}")
            clickListener.onClick(strTime)
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