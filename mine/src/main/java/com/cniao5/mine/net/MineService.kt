package com.cniao5.mine.net

import com.test.service.net.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


/*
* Mine模块相关的网络接口
* */

interface MineService {

    /**
     * 用户详情
     */
    @GET("user/lg/userinfo/json")
    fun getUserInfo():Call<BaseResponse>

}