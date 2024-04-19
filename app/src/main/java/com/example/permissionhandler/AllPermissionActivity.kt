package com.example.permissionhandler

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class AllPermissionActivity : AppCompatActivity() {

    var context: Context? = null
    private val PERMISSION_REQUEST_CODE = 1
    var p1 = Manifest.permission.READ_CONTACTS
    var p2 = Manifest.permission.ACCESS_FINE_LOCATION
    var p3 = Manifest.permission.WRITE_CONTACTS
    var p4 = Manifest.permission.CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_permission)
        context = this
        permissionAccess()
    }



    private fun permissionAccess() {
        if (!checkPermission(p1)) {
            Log.e("TAG", p1)
            requestPermission(p1)
        } else if (!checkPermission(p2)) {
            Log.e("TAG", p2)
            requestPermission(p2)
        } else if (!checkPermission(p3)) {
            Log.e("TAG", p3)
            requestPermission(p3)
        } else if (!checkPermission(p4)) {
            Log.e("TAG", p4)
            requestPermission(p4)
        } else {
            Toast.makeText(context, "All permission granted", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(context!!, permission)
        return if (result == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            false
        }
    }

    private fun requestPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                context!!,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AllPermissionActivity,
                arrayOf<String>(permission),
                PERMISSION_REQUEST_CODE
            )
        } else {
            //Do the stuff that requires permission...
            Log.e("TAG", "Not say request")
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                Log.e("TAG", "val " + grantResults[0])
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionAccess()
                } else {
                    Toast.makeText(context, "Bye bye", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}