package com.dongdongwu.mvitest.base.model

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 16:55 <br/>
 */
data class ServerException(
    val code: Int,
    override var message: String?
) : RuntimeException()