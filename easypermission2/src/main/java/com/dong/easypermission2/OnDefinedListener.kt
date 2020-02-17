package com.dong.easypermission2

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/14 10:53
 */
interface OnDefinedListener {
    /**
     * @param definedPermissions 被拒绝的权限集合
     */
    fun onDefined(definedPermissions:List<String>)
}