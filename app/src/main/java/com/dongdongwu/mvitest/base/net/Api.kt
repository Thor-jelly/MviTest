package com.dongdongwu.mvitest.base.net

import com.dongdongwu.mvitest.BuildConfig
import com.dongdongwu.mvitest.base.model.Response
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 在类中 通过server 请求网络
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:11 <br/>
 */
object Api {
    //获取wanAndroid 首页文章列表
    suspend fun getWanAndroidList(): Response<Any> {
        return service.getWanAndroidList()
    }

    ////////////////////////////////////////
    private val BASE_URL = "https://www.wanandroid.com/"
    private val okHttpClient by lazy {
        //下面代码看https://github.com/square/okhttp/wiki/HTTPS
        //https用ConnectionSpec.MODERN_TLS，不是的就用ConnectionSpec.CLEARTEXT
        var spec: ConnectionSpec? = null
        spec = if (BASE_URL.startsWith("https")) {
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
                .cipherSuites( // TLSv1.3.
                    CipherSuite.TLS_AES_128_GCM_SHA256,
                    CipherSuite.TLS_AES_256_GCM_SHA384,
                    CipherSuite.TLS_CHACHA20_POLY1305_SHA256,  // TLSv1.0, TLSv1.1, TLSv1.2.
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256,  // Note that the following cipher suites are all on HTTP/2's bad cipher suites list. We'll
                    // continue to include them until better suites are commonly available.
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA
                )
                .build()
        } else {
            ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT)
                .build()
        }

        //用来解决https需要证书认证的问题
        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)

        val builder = OkHttpClient.Builder()
        builder.readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .connectionSpecs(Collections.singletonList(spec!!))
            .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier())
            //.dns(OkHttpDns.getInstance())
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logging)
        }
        builder.build()
    }
    private val service by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(Service::class.java)
    }
}