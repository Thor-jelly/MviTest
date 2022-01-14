package com.dongdongwu.mvitest.base.timber

import com.dongdongwu.mvitest.BuildConfig
import timber.log.Timber

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:18 <br/>
 */
class LogTimber : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG) {
            super.log(priority, tag, message, t)
        }
    }
}