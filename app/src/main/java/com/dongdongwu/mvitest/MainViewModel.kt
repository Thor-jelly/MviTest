package com.dongdongwu.mvitest

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongdongwu.mvitest.base.BaseViewModel
import com.dongdongwu.mvitest.base.net.Api
import com.dongdongwu.mvitest.base.utils.SingleLiveEvent
import com.dongdongwu.mvitest.base.utils.ekt.asLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 14:41 <br/>
 */
class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _viewStates: MutableLiveData<MainViewState> = MutableLiveData()
    val viewStates = _viewStates.asLiveData()

    private val _viewEvents: SingleLiveEvent<MainViewEvent> = SingleLiveEvent()
    val viewEvents = _viewEvents.asLiveData()

    fun handleAction(action: MainViewAction) {
        when (action) {
            is MainViewAction.RefreshData -> {
                getWanAndroidList()
            }
            is MainViewAction.ItemClicked -> {

            }
            is MainViewAction.LoadMoreData -> {

            }
        }
    }

    private fun getWanAndroidList() {
        viewModelScope.launch {
            netFlow<Any>(Api.getWanAndroidList())
                .collect {
                    //获取到最终数据
                    Timber.tag("123===").d("获取到网络请求数据：$it")
                    _viewStates.value = MainViewState(PageState.CONTENT, listOf("1", "1", "2", "3"))
                    _viewEvents.setValue(MainViewEvent.ShowToast("错误数据来啦，手动弹出错误数据！"))
                }
        }
    }
}