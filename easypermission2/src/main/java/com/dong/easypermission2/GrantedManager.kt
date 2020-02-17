package com.dong.easypermission2

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.lang.RuntimeException

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 09:40
 */
class GrantedManager {
    private var fragment: PermissionFragment? = null
    private var fragmentX: PermissionFragmentX? = null

    constructor(fragment: PermissionFragment) {
        this.fragment = fragment
    }

    constructor(fragmentX: PermissionFragmentX) {
        this.fragmentX = fragmentX
    }

    fun permissions(permissionList: List<String>): GrantedBuilder {
        //找到未授权的权限
        val definedPermissions = ArrayList<String>()
        for (permission in permissionList) {
            fragmentX?.let {
                if (ContextCompat.checkSelfPermission(
                        it.requireContext(),
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    definedPermissions.add(permission)
                }
            }

            fragment?.let {
                if (ContextCompat.checkSelfPermission(
                        it.context,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    definedPermissions.add(permission)
                }
            }
        }

        fragmentX?.let {
            return GrantedBuilder(definedPermissions,permissionList,it)
        }
        fragment?.let {
            return GrantedBuilder(definedPermissions,permissionList,it)
        }
        throw RuntimeException("fragmentX or fragment is null")
    }
}