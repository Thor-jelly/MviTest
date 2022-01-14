package com.dongdongwu.mvitest.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import java.lang.reflect.ParameterizedType

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/12 15:57 <br/>
 */
open class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    lateinit var viewBinding: VB
        private set
    lateinit var viewModel: VM
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //反射获取bind
        try {
            val type = this.javaClass.genericSuperclass
            val tType = (type as ParameterizedType).actualTypeArguments[0] as Class<Any>
            //获取inflat方法
            val method = tType.getDeclaredMethod("inflate", LayoutInflater::class.java)
            viewBinding = method.invoke(null, layoutInflater) as VB
        } catch (e: Exception) {
            throw IllegalStateException("获取viewbinding 生成类的 inflate方法异常!")
        }
        setContentView(viewBinding.root)

        createViewModel()

        initLoadingObserve()
    }

    private fun initLoadingObserve() {
        viewModel.loadingEvent.observe(this) {
            if (it) {
                // TODO: 2022/1/14 123=== 显示loading
                Timber.i("显示loading")
            } else {
                // TODO: 2022/1/14 123===  隐藏loading
                Timber.i("隐藏loading")
            }
        }
    }

    private fun createViewModel() {
        if (!::viewModel.isInitialized) {
            val clazz: Class<VM>
            val type = this::class.java.genericSuperclass
            clazz = if (type is ParameterizedType) {
                try {
                    type.actualTypeArguments[1] as Class<VM>
                } catch (e: Exception) {
                    throw IllegalStateException("获取viewModel错误!")
                }
            } else {
                //如果没有使用泛型指定vm 则使用默认baseVm
                BaseViewModel::class as Class<VM>
            }
            viewModel = ViewModelProvider(this).get(clazz)
        }
    }
}