package com.dongdongwu.mvitest.base.net

import com.dongdongwu.mvitest.base.model.Response
import retrofit2.http.GET

/**
 * 创建人：吴冬冬<br/>
 * 创建时间：2022/1/13 15:11 <br/>
 */
interface Service {
    @GET("article/list/0/json")
    suspend fun getWanAndroidList(): Response<Any>
}