package com.dongdongwu.mvitest.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongdongwu.mvitest.base.model.Response
import com.dongdongwu.mvitest.base.model.ResponseException
import com.dongdongwu.mvitest.base.utils.SingleLiveEvent
import com.dongdongwu.mvitest.base.utils.ekt.asFlow
import com.dongdongwu.mvitest.base.utils.ekt.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 14:44 <br/>
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val _loadingEvent = SingleLiveEvent<Boolean>()
    val loadingEvent = _loadingEvent.asLiveData()

    protected fun showLoading() {
        _loadingEvent.setValue(true)
    }

    protected fun closeLoading() {
        _loadingEvent.setValue(false)
    }

    fun <T> netFlow(
        response: Response<T>,
        catch: (ResponseException) -> Unit = {},
        onStart: () -> Unit = {},
        onCompletion: () -> Unit = {}
    ): Flow<T> {
        return response.asFlow {
            //异常处理
            Timber.i("toast展示异常=${it.message ?: ""}")
            catch(it)
        }.onStart {
            //是否显示加载框
            showLoading()
            onStart()
        }.onCompletion {
            //关闭加载框
            closeLoading()
            onCompletion()
        }
    }
}