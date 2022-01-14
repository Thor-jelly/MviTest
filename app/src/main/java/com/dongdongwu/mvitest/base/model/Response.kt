package com.dongdongwu.mvitest.base.model

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:30 <br/>
 */
data class Response<T>(
    val errorCode: Int,
    var errorMsg: String,
    var data: T
) {
    fun isSuccess(): Boolean {
        return errorCode == 0
    }
}
