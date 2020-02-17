package com.dong.easypermission2

import android.app.Fragment

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 10:24
 */
class PermissionFragment:Fragment() {
    var grantedBuilder:GrantedBuilder? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        grantedBuilder?.onPermissionResult(requestCode,permissions,grantResults)
    }
}