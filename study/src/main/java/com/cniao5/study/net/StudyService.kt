package com.cniao5.study.net

import com.test.service.net.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
* 学习中心模块的接口
* */

interface StudyService {
    // 体系
    @GET("tree/json")
    fun getTreeList(): Call<BaseResponse>

    // 体系分类下的文章列表
    @GET("article/list/{page}/json")
    fun getTreeSubList(
        @Path("page") page:Int = 0,
        @Query("cid") cid:Int = 0
    ):Call<BaseResponse>

    // 导航
    @GET("navi/json")
    fun getNavList(): Call<BaseResponse>
}