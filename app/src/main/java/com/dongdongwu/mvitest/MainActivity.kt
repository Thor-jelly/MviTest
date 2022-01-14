package com.dongdongwu.mvitest

import android.os.Bundle
import com.dongdongwu.mvitest.base.BaseActivity
import com.dongdongwu.mvitest.base.utils.ekt.observeState
import com.dongdongwu.mvitest.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mvi
        //从mvvm双向绑定到mvi单向数据流
        //1.用户操作intent的形式通知model
        //2.model基于intent更新state
        //3.view接收到state变化刷新ui
        //viewDate
        //viewEvent
        //viewAction
        initViewModelObserve()
        getWanAndroidList()

        //view binding封装在BaseActivity中
        viewBinding.tv.text = "我是viewbinding 更改后的文字"
    }

    private fun initViewModelObserve() {
        viewModel.viewStates.run {
            //有两种方式 可以订阅
            observe(this@MainActivity) {
                when (it.pageState) {
                    PageState.EMPTY -> {

                    }
                    PageState.CONTENT -> {
                        //获取到列表数据
                        Timber.d("原始订阅方法获取数据：${it.list}")
                    }
                }
            }

            observeState(this@MainActivity, MainViewState::list) {
                //获取到列表数据
                Timber.d("新订阅方法获取数据：${it}")
            }
            observeState(this@MainActivity, MainViewState::pageState) {
                when (it) {
                    PageState.EMPTY -> {

                    }
                    PageState.CONTENT -> {
                        Timber.d("新订阅方法获取状态：${it}")
                    }
                }
            }
        }

        viewModel.viewEvents.run {
            observe(this@MainActivity) {
                when (it) {
                    is MainViewEvent.ShowSnackbar -> {

                    }
                    is MainViewEvent.ShowToast -> {
                        Timber.d("v层弹出toast：${it}")
                    }
                }
            }
        }
    }

    private fun getWanAndroidList() {
        viewModel.handleAction(MainViewAction.RefreshData)
    }
}