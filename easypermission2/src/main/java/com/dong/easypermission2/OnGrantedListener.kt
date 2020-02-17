package com.dong.easypermission2

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 10:54
 */
interface OnGrantedListener {
    /**
     * 部分权限通过，此时OnDefinedListener也会被调用，因为部分权限被拒绝了
     * @param grantedPermissions 被授予的权限集合
     */
    fun onGranted(grantedPermissions:List<String>)

    /**
     * 所有申请的权限都被通过了
     * @param grantedPermissions 被授予的权限集合
     * */
    fun onGrantedAll(grantedPermissions: List<String>)
}