package com.example.permissionhandler

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission


//https://stackoverflow.com/questions/36590454/android-multiple-runtime-permission-in-single-dialog

class PermissionHandler : ActivityCompat.OnRequestPermissionsResultCallback {
     private lateinit var callingActivity:AppCompatActivity
     private lateinit var permission: String
     private lateinit var permissionGranted: PermissionGranted
     private lateinit var context: Context
     val PERMISSION_REQUEST = 100

    constructor(context:Context,callingActivity:AppCompatActivity, permission: String, permissionGranted: PermissionGranted){
                 this.callingActivity = callingActivity
                 this.permission = permission
                 this.permissionGranted = permissionGranted
                 this.context = context
                 askForPermission()
    }

    interface  PermissionGranted {
         fun onPermissionGranted()
         fun onPermissionNotGranted()
    }


    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(callingActivity, permission)) {
                showAlertDialog()
            } else {

                ActivityCompat.requestPermissions(
                    callingActivity,
                    arrayOf (permission),
                    100
                )
            }
        } else {
            permissionGranted.onPermissionGranted()
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(context)
            .apply{
                setTitle("Allow Permission")
                setPositiveButton("Ok", object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        ActivityCompat.requestPermissions(
                            callingActivity,
                            arrayOf (permission),
                            100
                        )
                    }

                })
                setNegativeButton("Cancel", object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(context, "PermissionDialog Denied", Toast.LENGTH_SHORT).show()

                    }

                })
            }

          .show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST ->{
                 if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                     permissionGranted.onPermissionGranted()
                 }else{
                     permissionGranted.onPermissionNotGranted()
                 }
            }
        }

    }

}