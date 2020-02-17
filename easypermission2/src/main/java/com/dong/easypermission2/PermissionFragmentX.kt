package com.dong.easypermission2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * 透明Fragment
 * 用于获取授权结果
 */
class PermissionFragmentX : Fragment() {
    var grantedBuilder:GrantedBuilder? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        grantedBuilder?.onPermissionResult(requestCode,permissions,grantResults)
    }
}
