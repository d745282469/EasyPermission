package com.dong.easypermission2

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.lang.RuntimeException

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 09:56
 */
class GrantedBuilder {
    private val definedPermissions: List<String>
    private val allPermissions:List<String>
    private var fragmentX: PermissionFragmentX? = null
    private var fragment: PermissionFragment? = null
    private val grantedManager:GrantedManager

    /**事件监听器*/
    private var beforeGrantedListener:BeforeGrantedListener? = null
    private var onDefinedListener:OnDefinedListener? = null
    private var onGrantedListener:OnGrantedListener? = null

    companion object {
        private const val myRequestCode = 666
    }

    constructor(definedPermissions: List<String>,allPermissions:List<String>,
                fragmentX: PermissionFragmentX,grantedManager: GrantedManager) {
        this.fragmentX = fragmentX
        this.definedPermissions = definedPermissions
        this.allPermissions = allPermissions
        this.grantedManager = grantedManager
    }

    constructor(definedPermissions: List<String>,allPermissions:List<String>,
                fragment: PermissionFragment,grantedManager: GrantedManager) {
        this.fragment = fragment
        this.definedPermissions = definedPermissions
        this.allPermissions = allPermissions
        this.grantedManager = grantedManager
    }

    /**
     * 授权结果回调
     * 在PermissionFragment中调用
     */
    internal fun onPermissionResult(
        requestCode: Int, //识别码
        permissions: Array<out String>,//申请的权限数组
        grantResults: IntArray // 授权结果
    ) {
        if (requestCode == myRequestCode) {
            val definedPermissions = ArrayList<String>()
            val grantedPermissions = ArrayList<String>()
            //遍历授权结果
            for (index in grantResults.indices) {
                when (val result = grantResults[index]) {
                    PackageManager.PERMISSION_DENIED -> {
                        //权限被拒绝
                        definedPermissions.add(permissions[index])
                    }
                    PackageManager.PERMISSION_GRANTED -> {
                        //授予权限
                        grantedPermissions.add(permissions[index])
                    }
                    else -> {
                        throw RuntimeException("Unknown granted result:$result")
                    }
                }
            }

            if (definedPermissions.size > 0){
                //有权限未通过
                onDefinedListener?.onDefined(definedPermissions)
                if (grantedPermissions.size > 0){
                    //通过的部分权限
                    onGrantedListener?.onGranted(grantedPermissions)
                }
            }else{
                //所有权限通过
                onGrantedListener?.onGrantedAll(allPermissions)
            }

            clear()
        }
    }

    /**
     * 移除透明Fragment
     */
    private fun clear() {
        fragmentX?.let {
            val fragmentManager = it.fragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.remove(it)
            transaction?.commitNow()
        }
        fragment?.let {
            val fragmentManager = it.fragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.remove(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                transaction?.commitNow()
            } else {
                throw RuntimeException("SdkVersion needs 24,But current version is " + Build.VERSION.SDK_INT + ". Use other methods or change your minSdkVersion.")
            }
        }
        grantedManager.clear()
    }

    fun beforeGranted(listener: BeforeGrantedListener):GrantedBuilder{
        beforeGrantedListener = listener
        return this
    }

    fun onDefined(listener: OnDefinedListener):GrantedBuilder{
        onDefinedListener = listener
        return this
    }

    fun onGranted(listener: OnGrantedListener):GrantedBuilder{
        onGrantedListener = listener
        return this
    }

    /**
     * 发起授权
     * @return true表示全部权限都获取到了
     */
    fun granted():Boolean {
        var result = false
        if (definedPermissions.isEmpty()) {
            result = true
            onGrantedListener?.onGrantedAll(allPermissions)
        } else {
            beforeGrantedListener?.beforeGranted(definedPermissions)
            val array = definedPermissions.toTypedArray()

            // 开始发起授权窗口
            fragmentX?.let {
                it.grantedBuilder = this
                it.requestPermissions(array, myRequestCode)
            }

            fragment?.let {
                it.grantedBuilder = this
                it.requestPermissions(array, myRequestCode)
            }
        }
        return result
    }

    /**
     * 判断是否已经获取到了权限
     *
     * 注意：这个函数不会回调任何监听器
     * @return
     */
    fun haveGranted():Boolean{
        return definedPermissions.isEmpty()
    }
}