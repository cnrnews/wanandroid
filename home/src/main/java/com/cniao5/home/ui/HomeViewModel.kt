package com.cniao5.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cniao5.common.base.BaseViewModel
import com.cniao5.common.model.DataResult
import com.cniao5.home.net.BannerList
import com.cniao5.home.repo.IHomeResource
import com.test.service.net.BaseResponse

/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 首页的viewmodel
 */
class HomeViewModel(private val resource: IHomeResource) : BaseViewModel() {

    val liveBanner = resource.liveBanner    //首页上方的banner图

    val liveArticles = resource.liveArticles

    //首页上方banner图的网络请求
    fun getBanner() = serverAwait {
        resource.getBanner()
    }

    fun getArticles(page:Int) = serverAwait {
        resource.getArticles(page)
    }
}