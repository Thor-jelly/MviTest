package com.dongdongwu.mvitest.base.utils.ekt

import com.dongdongwu.mvitest.base.model.Response
import com.dongdongwu.mvitest.base.model.ResponseException
import com.dongdongwu.mvitest.base.model.dataConvert
import com.dongdongwu.mvitest.base.utils.commonCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 18:18 <br/>
 */
fun <T> Response<T>.asFlow(
    catch: (ResponseException) -> Unit
): Flow<T> {
    val model = this
    return flow<T> {
        emit(model.dataConvert())
    }.commonCatch {
        //异常处理
        catch(it)
    }
}