package com.cniao5.study.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.network.support.serverData
import com.cniao5.study.net.NavRsp
import com.cniao5.study.net.StudyService
import com.cniao5.study.net.TreeRsp
import com.cniao5.study.net.TreeSub
import com.google.gson.Gson
import com.test.service.net.*
import kotlinx.coroutines.flow.Flow
import java.lang.Math.random
import kotlin.Exception

class StudyResource(private val service: StudyService) : IStudyResource {

    private val _treeList = MutableLiveData<TreeRsp>()
    private val _treeSubList = MutableLiveData<TreeSub>()
    private val _navList = MutableLiveData<NavRsp>()

    override val liveTreeList: LiveData<TreeRsp> = _treeList
    override val liveTreeSubList: LiveData<TreeSub> = _treeSubList
    override val liveNavList: LiveData<NavRsp> = _navList


    /**
     * 体系
     */
    override suspend fun getTreeList() {
        service.getTreeList()
            .serverData()
            .onSuccess {
                //只要不是接口响应成功
                onBizError { code, message ->
                    // _studyInfo.value = null
                    LogUtils.w("getTreeList BizError $code,$message") //警告
                }
                onBizOK<TreeRsp> { code, data, message ->
                    LogUtils.w("getTreeList onBizOK ${data.toString()}") //警告
                    _treeList.value = data
                    // LogUtils.i("获取学习信息 BizOK $data") //信息
                    return@onBizOK
                }
            }
            .onFailure {
                // _studyInfo.value = null
                LogUtils.e("getTreeList  接口异常 ${it.message}") //错误
            }
    }

    /**
     * 体系下的分类列表
     */
    override suspend fun getTreeSubList(page:Int,cid:Int) {
        service.getTreeSubList(page, cid)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功
                onBizError { code, message ->
                    // _studyInfo.value = null
                }
                onBizOK<TreeSub> { code, data, message ->
                    _treeSubList.value = data
                    return@onBizOK
                }
            }
            .onFailure {
                // _studyInfo.value = null
                LogUtils.e("getTreeSubList  接口异常 ${it.message}") //错误
            }
    }

    /**
     * 导航
     */
    override suspend fun getNavList() {
        service.getNavList()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("导航 BizError $code,$message")
                }
                onBizOK<NavRsp> { code, data, message ->
                    _navList.value = data
                    // LogUtils.i("获取学习过的课程列表 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.e("导航 接口异常 ${it.message}")
            }
    }
}

