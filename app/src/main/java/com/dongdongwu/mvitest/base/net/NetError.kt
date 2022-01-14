package com.dongdongwu.mvitest.base.net

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2021/3/3 17:57 <br/>
 */
object NetError {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000
    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001
    /**
     * 网络错误
     */
    const val NET_ERROR = 1002
    /**
     * 协议出错
     */
    const val HTTP_ERROR = 1003

    /**
     * 证书出错
     */
    const val SSL_ERROR = 1005

    /**
     * 连接超时
     */
    const val TIMEOUT_ERROR = 1006

    /**
     * 需要重新登录
     */
    const val NEED_LOGIN = 1007
}