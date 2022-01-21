package com.cniao5.course.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.cniao5.course.net.WendDaListRsp
import kotlinx.coroutines.flow.Flow

interface ICourseResource {

    //课程列表paging3
    suspend fun getWenDaListPaging(
    ): Flow<PagingData<WendDaListRsp.Data>>
}