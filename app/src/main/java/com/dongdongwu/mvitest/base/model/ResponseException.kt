package com.dongdongwu.mvitest.base.model

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 16:56 <br/>
 */
data class ResponseException(
    var code: Int,
    val throwable: Throwable,
    override var message: String? = null
) : Exception(message, throwable)