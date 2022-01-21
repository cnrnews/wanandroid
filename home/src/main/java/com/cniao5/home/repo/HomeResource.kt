package com.cniao5.home.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.model.DataResult
import com.cniao5.common.network.support.serverData
import com.cniao5.home.net.Article
import com.cniao5.home.net.BannerList
import com.cniao5.home.net.HomeService
import com.test.service.net.*
import kotlinx.coroutines.flow.Flow
import java.lang.Exception


/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 实现抽象上层接口
 */
class HomeResource(val service: HomeService) : IHomeResource {

    // region 获取首页上方banner图数据
    private val _liveBanner = MutableLiveData<BannerList>()
    override val liveBanner: LiveData<BannerList>
        get() = _liveBanner

    // 文章列表
    private val _liveArticles = MutableLiveData<Article>()
    override val liveArticles: LiveData<Article> = _liveArticles


    override suspend fun getBanner() {
        service.getBanner()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取banner信息 onBizzError $code$message")
                }
                onBizOK<BannerList> { code, data, message ->
                    _liveBanner.value = data
                    LogUtils.i("获取banner信息 onBizzOK $data")
                }
            }
            .onFailure {
                LogUtils.e("获取banner信息 接口异常 ${it.message}")
            }
    }

    /*
    *   首页文章列表的paging3
    * */
    override suspend fun getArticles(page:Int){
        service.getArticles(page)
                .serverData()
                .onSuccess {
                    onBizError { code, message ->
                        LogUtils.w("获取课程列表Paging3 BizError $code,$message")
                    }
                    onBizOK<Article> { code, data, message ->
                        LogUtils.i("获取课程列表Paging3 BizOK $data")
                        _liveArticles.value = data
                    }
                }
                .onFailure {
                    LogUtils.e("获取课程列表Paging3 接口异常 ${it.message}")
                }
    }
}