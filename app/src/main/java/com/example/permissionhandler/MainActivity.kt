package com.example.permissionhandler

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.permissionhandler.PermissionHandler.PermissionGranted

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun checkPermission(view: View){
        PermissionHandler(
            this,
            this@MainActivity,
            Manifest.permission.POST_NOTIFICATIONS,
            object : PermissionGranted {
                override fun onPermissionGranted() {
                    Toast.makeText(this@MainActivity, "PermissionGranted !", Toast.LENGTH_SHORT).show()

                }

                override fun onPermissionNotGranted() {
                    Toast.makeText(this@MainActivity, "Permission NOt Granted!", Toast.LENGTH_SHORT).show()
                }

            })
    }

}