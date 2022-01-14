package com.dongdongwu.mvitest.base.utils.ekt

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KProperty1

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/14 11:14 <br/>
 */
fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun <T, R> LiveData<T>.observeState(
    @NonNull owner: LifecycleOwner,
    kProperty1: KProperty1<T, R>,
    action: (R) -> Unit
) {
    this.observe(owner) {
        val r = kProperty1.get(it)
        action(r)
    }
}