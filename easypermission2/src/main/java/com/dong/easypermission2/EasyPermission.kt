package com.dong.easypermission2

import android.app.Activity
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.RuntimeException

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 09:18
 */
class EasyPermission {
    companion object {
        fun inActivity(activity: FragmentActivity): GrantedManager {
            //创建透明Fragment
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = PermissionFragmentX()
            transaction.add(fragment, null)
            transaction.commitNow()
            return GrantedManager(fragment)
        }

        fun inActivity(activity: Activity): GrantedManager {
            val fragmentManager = activity.fragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = PermissionFragment()
            transaction.add(fragment, null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                transaction.commitNow()
            } else {
                throw RuntimeException("SdkVersion needs 24,But current version is " + Build.VERSION.SDK_INT + ". Use other methods or change your minSdkVersion.")
            }
            return GrantedManager(fragment)
        }

        fun inFragment(fragment:Fragment):GrantedManager{
            //创建透明Fragment
            val fragmentManager = fragment.childFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val permissionFragmentX = PermissionFragmentX()
            transaction.add(fragment, null)
            transaction.commitNow()
            return GrantedManager(permissionFragmentX)
        }
    }
}