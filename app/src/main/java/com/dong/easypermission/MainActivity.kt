package com.dong.easypermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dong.easypermission2.BeforeGrantedListener
import com.dong.easypermission2.EasyPermission
import com.dong.easypermission2.OnDefinedListener
import com.dong.easypermission2.OnGrantedListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_WIFI_STATE)

        EasyPermission
            .inActivity(this)
            .permissions(permissions)
            .beforeGranted(object :BeforeGrantedListener{
                override fun beforeGranted(definedPermissions: List<String>) {
                    Log.d("dong","准备申请下列权限：${definedPermissions.toTypedArray().contentToString()}")
                }
            })
            .onDefined(object :OnDefinedListener{
                override fun onDefined(definedPermissions: List<String>) {
                    Log.e("dong","下列权限被拒绝：${definedPermissions.toTypedArray().contentToString()}")
                }
            })
            .onGranted(object :OnGrantedListener{
                override fun onGranted(grantedPermissions: List<String>) {
                    Log.d("dong","部分权限被通过：${grantedPermissions.toTypedArray().contentToString()}")
                }

                override fun onGrantedAll(grantedPermissions: List<String>) {
                    Log.d("dong","全部权限被通过：${grantedPermissions.toTypedArray().contentToString()}")
                }
            })
            .granted()
    }
}
