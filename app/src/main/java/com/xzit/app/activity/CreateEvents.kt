package com.xzit.app.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.xzit.app.R
import com.xzit.app.databinding.ActivityCreatEventBinding
import com.xzit.app.listener.OnDialogClickListener
import com.xzit.app.utils.*
import java.util.*

class CreatEvents : BaseActivity(), View.OnClickListener {
    private var binding: ActivityCreatEventBinding? = null
    private var profileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        listener()
    }

    private fun initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creat_event)
        val today = Date()
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)
    }

    private fun listener() {
        binding!!.btnNext.setOnClickListener(this)
        binding!!.llSelectImage.setOnClickListener(this)
        binding!!.etEventDuration.setOnClickListener(this)
        binding!!.etEventTime.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnNext -> {
                val intentSettings = Intent(this, AddFriendsActivity::class.java)
                startActivity(intentSettings)
            }
            R.id.etEventDuration -> showDatePickerDialog(mContext, true, OnDialogClickListener { })
            R.id.etEventTime -> {
                showTimePickerDialog(mContext, true, OnDialogClickListener { })
            }
            R.id.llSelectImage -> if (PermissionUtils.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_WRITE_EXST)) {
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
}