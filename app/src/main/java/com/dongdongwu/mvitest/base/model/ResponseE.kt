package com.dongdongwu.mvitest.base.model

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:39 <br/>
 */
fun <T> Response<T>.dataConvert(): T {
    if (this.isSuccess()) {
        return data
    } else {
        throw ServerException(errorCode, errorMsg)
    }
}