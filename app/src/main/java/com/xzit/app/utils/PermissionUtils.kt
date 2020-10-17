package com.xzit.app.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtils {
    private fun checkCamera(activity: AppCompatActivity): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),
                REQ_CAMERA)
    }

    companion object {
        @JvmStatic
        fun askForPermission(activity: AppCompatActivity?, permission: String, requestCode: Int?): Boolean {
            return if (!isPermissionGranted(activity, permission)) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {

                    //This is called if user has denied the permission before
                    //In this case I am just asking the permission again
                    ActivityCompat.requestPermissions(activity, arrayOf<String?>(permission), requestCode!!)
                } else {
                    ActivityCompat.requestPermissions(activity, arrayOf<String?>(permission), requestCode!!)
                }
                false
            } else {
                true
                //Toast.makeText(activity, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            }
        }

        fun isPermissionGranted(activity: AppCompatActivity?, permission: String?): Boolean {
            return ContextCompat.checkSelfPermission(activity!!, permission!!) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun hasPermissions(context: AppCompatActivity, vararg permissions: String): Boolean =
                permissions.all {
                    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }


        @JvmStatic
        fun isAllGranted(context: AppCompatActivity, vararg permissions: String): Boolean =
                permissions.all {
                    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }

        const val REQ_WRITE_EXST = 501
        const val REQ_CAMERA = 502
    }
}