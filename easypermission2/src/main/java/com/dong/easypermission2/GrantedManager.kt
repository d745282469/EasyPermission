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

    /**
     * 申请单独一个权限
     * @param permission
     */
    fun addPermission(permission: String): GrantedBuilder {
        return permissions(listOf(permission))
    }

    /**
     * 申请一堆权限
     * @param permissionList
     */
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
            return GrantedBuilder(definedPermissions, permissionList, it, this)
        }
        fragment?.let {
            return GrantedBuilder(definedPermissions, permissionList, it, this)
        }
        throw RuntimeException("FragmentX or Fragment is null")
    }

    internal fun clear() {
        fragment = null
        fragmentX = null
    }
}