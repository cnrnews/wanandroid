package com.cniao5.study.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.cniao5.common.base.BaseViewModel
import com.cniao5.study.net.NavRsp
import com.cniao5.study.net.TreeRsp
import com.cniao5.study.net.TreeRspItem
import com.cniao5.study.net.TreeSub
import com.cniao5.study.repo.StudyResource
import com.cniao5.study.ui.StudiedAdapter
import com.test.service.repo.UserInfo

class StudyViewModel(private val resource: StudyResource) : BaseViewModel() {

    // 体系
    val liveTreeList: LiveData<TreeRsp> = resource.liveTreeList
    // 体系下的分类列表
    val liveTreeSubList: LiveData<TreeSub> = resource.liveTreeSubList
    // 导航
    val liveNavList: LiveData<NavRsp> = resource.liveNavList

    //
    // //我的学习列表适配器
    // val adapter = StudiedAdapter()

    /**
     * 体系
     */
    fun getTreeList()= serverAwait {
        resource.getTreeList()
    }
    /**
     * 体系下的分类文章
     */
    fun getTreeSubList(page:Int,cid:Int)= serverAwait {
        resource.getTreeSubList(page, cid)
    }
    /**
     * 导航
     */
    fun getNavList()= serverAwait {
        resource.getNavList()
    }
}