package com.dongdongwu.mvitest.base.utils

import android.net.ParseException
import com.dongdongwu.mvitest.base.model.ResponseException
import com.dongdongwu.mvitest.base.model.ServerException
import com.dongdongwu.mvitest.base.net.NetError
import com.google.gson.JsonParseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 17:55 <br/>
 */
fun <T> Flow<T>.commonCatch(action: (ResponseException) -> Unit): Flow<T> {
    return this.catch {
        val error = when (it) {
            is ResponseException -> {
                it
            }
            is HttpException -> {
                //CrashReport.postCatchedException(it)
                val responseException = ResponseException(NetError.HTTP_ERROR, it)
                responseException.message = it.message()
                responseException
            }
            is ServerException -> {
                val responseException = ResponseException(it.code, it)
                responseException.message = it.message
                responseException
            }
            is JsonParseException,
            is JSONException,
            is ParseException -> {
                //CrashReport.postCatchedException(it)
                ResponseException(NetError.PARSE_ERROR, it, "解析错误")
            }
            is ConnectException,
            is NoRouteToHostException -> {
                //CrashReport.postCatchedException(it)
                ResponseException(NetError.NET_ERROR, it, "网络连接失败")
            }
            is SSLHandshakeException -> {
                //CrashReport.postCatchedException(it)
                ResponseException(NetError.SSL_ERROR, it, "证书验证失败")
            }
            is ConnectTimeoutException,
            is UnknownHostException,
            is SocketTimeoutException -> {
                //CrashReport.postCatchedException(it)
                ResponseException(NetError.TIMEOUT_ERROR, it, "连接超时")
            }
            else -> {
                //CrashReport.postCatchedException(it)
                ResponseException(NetError.UNKNOWN, it, "未知错误，请重启APP")
            }
        }
        action(error)
    }
}