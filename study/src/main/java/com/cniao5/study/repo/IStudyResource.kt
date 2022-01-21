package com.cniao5.study.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.cniao5.study.net.NavRsp
import com.cniao5.study.net.TreeRsp
import com.cniao5.study.net.TreeSub
import kotlinx.coroutines.flow.Flow


interface IStudyResource {

    val liveTreeList: LiveData<TreeRsp>
    val liveTreeSubList: LiveData<TreeSub>
    val liveNavList: LiveData<NavRsp>

    // 体系
    suspend fun getTreeList()
    // 体系下的分类文章列表
    suspend fun getTreeSubList(page:Int,cid:Int)

    // 导航
    suspend fun getNavList()


}