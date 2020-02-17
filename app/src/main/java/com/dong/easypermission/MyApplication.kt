package com.dong.easypermission

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Create by AndroidStudio
 * Author: pd
 * Time: 2020/2/17 11:38
 */
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}