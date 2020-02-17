package com.dong.easypermission2

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 10:50
 */
interface BeforeGrantedListener {
    /**
     * 开始调起授权窗口前
     * @param definedPermissions 实际会提示用户授权的权限
     * 因为有些权限其实是不会调起授权窗口的，或者有些权限已经被通过了
     */
    fun beforeGranted(definedPermissions:List<String>)
}