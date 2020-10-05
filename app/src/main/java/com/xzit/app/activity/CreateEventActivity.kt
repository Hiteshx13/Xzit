package com.xzit.app.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.xzit.app.R
import com.xzit.app.adapter.SpinnerCategoryAdapter
import com.xzit.app.databinding.ActivityCreatEventBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.listener.OnMultiDateSelectedListener
import com.xzit.app.listener.OnTimeSelectedListener
import com.xzit.app.repository.EventRepository
import com.xzit.app.retrofit.model.response.createevent.CreateEventResponse
import com.xzit.app.retrofit.model.response.login.LoginData
import com.xzit.app.retrofit.model.response.masterdata.Subtype
import com.xzit.app.utils.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URISyntaxException
import java.util.*
import kotlin.collections.ArrayList


class CreateEventActivity : BaseActivity(), View.OnClickListener {
    private var binding: ActivityCreatEventBinding? = null
    private var profileUri: Uri? = null
    private var listCategory: ArrayList<Subtype>? = ArrayList()
    private var listOther: ArrayList<Subtype>? = ArrayList()
    private var listPreference: ArrayList<Subtype>? = ArrayList()
    private var strCategory: String = ""
    private var strOther: String? = ""
    private var strPreference: String = ""
    private var strEventTime: String = ""
    private var eventOnSale: String = "NO"

    private var eventStartDate: String = ""
    private var eventEndDate: String = ""
    private lateinit var repository: EventRepository
    private var userData: LoginData = XzitApp.getLoginUserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        listener()
        initObserver()
    }

    private fun initialization() {
        repository = EventRepository()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creat_event)
        binding?.etUserName?.setText(userData.username)
        listCategory = XzitApp.preference.getMasterData(mContext).Response!![3].subtype as ArrayList<Subtype>
        listOther = XzitApp.preference.getMasterData(mContext).Response!![12].subtype as ArrayList<Subtype>
        listPreference = XzitApp.preference.getMasterData(mContext).Response!![2].subtype as ArrayList<Subtype>

        val titleCategory = Subtype()
        val titlePreference = Subtype()
        val titleOthers = Subtype()
        titleCategory.displayValue = getString(R.string.select_a_category)
        listCategory!!.add(0, titleCategory)
        titlePreference.displayValue = getString(R.string.select_a_preference)
        listPreference!!.add(0, titlePreference)
        titleOthers.displayValue = getString(R.string.others)
        listOther!!.add(0, titleOthers)

        binding?.spPreference?.adapter = SpinnerCategoryAdapter(mContext, listPreference)
        binding?.spCategory?.adapter = SpinnerCategoryAdapter(mContext, listCategory)
        binding?.spOthers?.adapter = SpinnerCategoryAdapter(mContext, listOther)
    }

    private fun listener() {
        binding!!.btnNext.setOnClickListener(this)
        binding!!.cvImage.setOnClickListener(this)
        binding!!.etEventDate.setOnClickListener(this)
        binding!!.etEventTime.setOnClickListener(this)
        binding!!.ivBack.setOnClickListener(this)

        binding!!.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (position == 0) {
                    strCategory = ""
                } else {
                    strCategory = listCategory!![position].displayValue
                }
                binding!!.spCategory.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding!!.spOthers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (position == 0) {
                    strOther = ""
                } else {
                    strOther = listOther!![position].displayValue
                }
                binding!!.spOthers.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding!!.spPreference.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (position == 0) {
                    strPreference = ""
                } else {
                    strPreference = listPreference!![position].displayValue
                }
                binding!!.spPreference.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        binding?.swOnSale?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) eventOnSale = "YES" else eventOnSale = "NO"
            }
        })
    }

    fun initObserver() {
        repository.responseData.observe(this, object : Observer<CreateEventResponse> {
            override fun onChanged(response: CreateEventResponse?) {
                if (response != null && response.status == RESP_API_SUCCESS) {
                    var eventID = response.response.get(0).eventId
                    finish()
                    LaunchActivity(mContext, EventInviteFriendsActivity.getIntent(mContext, eventID))
//                    showMessageDialog(mContext, response.message, false, OnDialogClickListener {
//
//
//                    })
                } else {
                    showMessageDialog(mContext, response?.message, true, OnDialogClickListener { })
                }
            }

        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnNext -> {

                var strTitle = binding?.etTitle?.text.toString().trim()
                var strDescrip = binding?.etDescription?.text.toString().trim()
                var strEventPrice = binding?.etPrice?.text.toString().trim()
                var strDealPrice = binding?.etDealPrice?.text.toString().trim()
                var strDiscountPercent = binding?.etDiscountPercent?.text.toString().trim()
                var strDiscountPrice = binding?.etDiscountPrice?.text.toString().trim()
                var strAddress = binding?.etAddress?.text.toString().trim()
                if (strTitle.isEmpty()) {
                    showToast(mContext, "Please enter title")
                } else if (strDescrip.isEmpty()) {
                    showToast(mContext, "Please enter description")
                } else if (strCategory.isEmpty()) {
                    showToast(mContext, "Please select any category")
                } else if (strPreference.isEmpty()) {
                    showToast(mContext, "Please select any preference")
                } else if (eventStartDate.isEmpty()) {
                    showToast(mContext, "Please select Event duration")
                }
                else if(strEventTime.replace("{}","").trim().isEmpty()){
                    showToast(mContext,"Please select Event time")
                }
                else if (strEventPrice.isEmpty()) {
                    showToast(mContext, "Please enter Event price")
                } else if (strDealPrice.isEmpty()) {
                    showToast(mContext, "Please enter Event deal price")
                } else if (strDiscountPercent.isEmpty()) {
                    showToast(mContext, "Please enter Discount%")
                } else if (strDiscountPrice.isEmpty()) {
                    showToast(mContext, "Please enter Discount$")
                } else if (strAddress.isEmpty()) {
                    showToast(mContext, "Please enter event address")
                } else {
                    if (profileUri == null) {

                        val map = HashMap<String, String>()
                        map["postData[requestCase]"] = "createEvent"
                        map["postData[eventType]"] = "Casual"
                        map["postData[clientId]"] = userData.clientId
                        map["postData[userId]"] = userData.userId
                        map["postData[eventVenueId]"] = "1"
                        map["postData[eventVenue]"] = "Geelong Avenue Club"
                        map["postData[eventTitleName]"] = binding?.etTitle?.text.toString().trim()
                        map["postData[eventDescDetail]"] = binding?.etDescription?.text.toString().trim()
                        map["postData[eventCategory]"] = strCategory.toString()
                        map["postData[eventPreference]"] = strPreference.toString()
                        map["postData[eventDurationStart]"] = eventStartDate
                        map["postData[eventDurationEnd]"] = eventStartDate
                         map["postData[eventTime]"] = strEventTime
                        map["postData[eventPrice]"] = binding?.etPrice?.text.toString().trim()
                        map["postData[eventDealPrice]"] = binding?.etDealPrice?.text.toString().trim()
                        map["postData[eventDealDiscType]"] = binding?.etDiscountPercent?.text.toString().trim()
                        map["postData[eventDealDiscAmt]"] = binding?.etDiscountPrice?.text.toString().trim()
                        map["postData[eventLocation]"] = binding?.etAddress?.text.toString().trim()
                        map["postData[eventLat]"] = "-38.149918"
                        map["postData[eventLong]"] = "144.361725"
                        map["postData[eventOnSaleFlag]"] = eventOnSale

                        Log.d("#params", "" + map.toString())
                        repository.callCreateEvent(this, map)
                    } else {

                        val map = HashMap<String, RequestBody?>()
                        // map.put("postData[requestCase]", getRequestBody("createEvent"))
                        map["postData[requestCase]"] = getRequestBody("createEvent")
                        map["postData[eventType]"] = getRequestBody("Casual")
                        map["postData[clientId]"] = getRequestBody(userData.clientId)
                        map["postData[userId]"] = getRequestBody(userData.userId)
                        map["postData[eventVenueId]"] = getRequestBody("1")
                        map["postData[eventVenue]"] = getRequestBody("Geelong Avenue Club")
                        map["postData[eventTitleName]"] = getRequestBody(binding?.etTitle?.text.toString().trim())
                        map["postData[eventDescDetail]"] = getRequestBody(binding?.etDescription?.text.toString().trim())
                        map["postData[eventCategory]"] = getRequestBody(strCategory.toString())
                        map["postData[eventPreference]"] = getRequestBody(strPreference.toString())
                        map["postData[eventDurationStart]"] = getRequestBody(eventStartDate)
                        map["postData[eventDurationEnd]"] = getRequestBody(eventStartDate)
                         map["postData[eventTime]"] = getRequestBody(strEventTime)
                        map["postData[eventPrice]"] = getRequestBody(binding?.etPrice?.text.toString().trim())
                        map["postData[eventDealPrice]"] = getRequestBody(binding?.etDealPrice?.text.toString().trim())
                        map["postData[eventDealDiscType]"] = getRequestBody(binding?.etDiscountPercent?.text.toString().trim())
                        map["postData[eventDealDiscAmt]"] = getRequestBody(binding?.etDiscountPrice?.text.toString().trim())
                        map["postData[eventLocation]"] = getRequestBody(binding?.etAddress?.text.toString().trim())
                        map["postData[eventLat]"] = getRequestBody("-38.149918")
                        map["postData[eventLong]"] = getRequestBody("144.361725")
                        map["postData[eventOnSaleFlag]"] = getRequestBody(eventOnSale)

                        val attachmentName = ArrayList<MultipartBody.Part?>(1)
                        val file = File(getFilePath(profileUri!!))
                        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), file)
                        val body = MultipartBody.Part.createFormData("attachmentName[]", "", requestFile)
                        attachmentName.add(body)
                        Log.d("#params", "" + map.toString())
                        repository.callCreateEventImage(this, map, attachmentName)
                    }
                }
            }


            R.id.etEventDate -> showDatePickerDialog(mContext, true, object : OnMultiDateSelectedListener {
                override fun onDateSelected(strStart: String, strEnd: String) {
                    eventStartDate = strStart
                    eventEndDate = strEnd
                    binding?.etEventDate?.setText(eventStartDate + " to " + eventEndDate)
                }

            })

            R.id.ivBack -> finish()
            R.id.etEventTime -> {
                showTimePickerDialog(mContext, true, object : OnTimeSelectedListener {
                    override fun onClick(str: String,days:String) {
                        strEventTime = str.replace("=",":")
                        var strTempTime = str.replace("{", "").replace("}", "").replace("start=", "").replace("end=", "")
                        if(days.isNotEmpty()){
                            binding?.etEventTime?.setText("Weekdays : "+days)
                        }else{
                            binding?.etEventTime?.setText("")
                        }
                    }
                })
            }
            R.id.cvImage -> if (PermissionUtils.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXST)) {
                selectImageFromGallery()
            } else {
                showMessageDialog(this, this.getString(R.string.please_grant_all_required_permissions_from_application_setting), false, OnDialogClickListener {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                })
                //Toast.makeText(this, getString(R.string.please_grant_all_required_permissions_from_application_setting), Toast.LENGTH_SHORT).show();
            }
        }
    }


    protected fun selectImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_SELECT_PHOTO_GALLERY)
    }

    private fun getRequestBody(string: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), string)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_SELECT_PHOTO_GALLERY -> if (resultCode == RESULT_OK) {
                if (data!!.data != null) {
                    profileUri = data.data
                    binding!!.ivProfile.setImageURI(profileUri)
                    binding!!.llSelectImageRoot.visibility = View.GONE
                } else {
                    if (data.clipData != null) {
                        val mClipData = data.clipData
                        var i = 0
                        while (i < mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            profileUri = item.uri
                            binding!!.ivProfile.setImageURI(profileUri)
                            i++
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @Throws(URISyntaxException::class)
    fun getFilePath(uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(applicationContext, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(
                        split[1]
                )
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }
            val projection = arrayOf(
                    MediaStore.Images.Media.DATA
            )
            var cursor: Cursor? = null
            try {
                cursor = contentResolver
                        .query(uri, projection, selection, selectionArgs, null)
                val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}