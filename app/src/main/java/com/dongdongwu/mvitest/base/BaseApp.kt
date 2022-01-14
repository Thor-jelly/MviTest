package com.dongdongwu.mvitest.base

import android.app.Application
import com.dongdongwu.mvitest.BuildConfig
import com.dongdongwu.mvitest.base.timber.LogTimber
import timber.log.Timber

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:16 <br/>
 */
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(LogTimber())
        }
    }
}