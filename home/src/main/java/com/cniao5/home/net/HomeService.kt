package com.cniao5.home.net

import com.test.service.net.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 主页模块的接口
 */
interface HomeService {
    /*
    * 首页上方banner
    * */
    @GET("banner/json")
    fun getBanner(
    ) : Call<BaseResponse>


    // 首页文章列表
    @GET("article/list/{page}/json")
    fun getArticles(
        @Path("page") page:Int = 0
    ): Call<BaseResponse>
}