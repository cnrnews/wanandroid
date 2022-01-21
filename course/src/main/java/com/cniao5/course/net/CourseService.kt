package com.cniao5.course.net

import com.test.service.net.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
* 问答模块的接口
* */
interface CourseService {

    /*
    * 获取问答列表
    * */
    @GET("wenda/list/1/json")
    fun getWenDaList(
        @Query("page_size ") page_size:Int = 1
    ): Call<BaseResponse>
}