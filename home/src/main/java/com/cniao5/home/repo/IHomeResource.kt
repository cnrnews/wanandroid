package com.cniao5.home.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.cniao5.common.model.DataResult
import com.cniao5.home.net.Article
import com.cniao5.home.net.BannerList
import com.test.service.net.BaseResponse
import kotlinx.coroutines.flow.Flow


/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 抽象上层接口
 */
interface IHomeResource {

    //首页上方banner图
    val liveBanner: LiveData<BannerList>
    // 首页 Banner
    suspend fun getBanner()

    // 文章列表
    val liveArticles: LiveData<Article>
    suspend fun getArticles(page:Int = 0)
}