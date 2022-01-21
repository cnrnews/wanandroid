package com.cniao5.course.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.network.support.serverData
import com.cniao5.course.net.CourseService
import com.cniao5.course.net.WendDaListRsp
import com.test.service.net.onBizError
import com.test.service.net.onBizOK
import com.test.service.net.onFailure
import com.test.service.net.onSuccess
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class CourseResource(val service: CourseService) : ICourseResource {

    private val pageSize = 10
    override suspend fun getWenDaListPaging(
    ): Flow<PagingData<WendDaListRsp.Data>> {
        val pagingConfig = PagingConfig(
            pageSize = pageSize, // 每页显示的数据的大小。对应 PagingSource 里 LoadParams.loadSize
            prefetchDistance = 2, // 预刷新的距离，距离最后一个 item 多远时加载数据，默认为 pageSize
            initialLoadSize = 10,  // 初始化加载数量，默认为 pageSize * 3
            maxSize = pageSize * 3 // 一次应在内存中保存的最大数据，默认为 Int.MAX_VALUE
        )
        return Pager(config = pagingConfig, null) {
            CourseListPagingSource(service)
        }.flow
    }


    class CourseListPagingSource constructor(
        private val service: CourseService,
    ) : PagingSource<Int, WendDaListRsp.Data>() {

        override fun getRefreshKey(state: PagingState<Int, WendDaListRsp.Data>): Int? = null

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WendDaListRsp.Data> {
            var result: LoadResult<Int, WendDaListRsp.Data> =
                LoadResult.Error(Exception("加载中"))
            var firstPage = params.key ?: 1
            service.getWenDaList(firstPage)
                .serverData()
                .onSuccess {
                    onBizError { code, message ->
                        LogUtils.w("获取课程列表Paging3 BizError $code,$message")
                        result = LoadResult.Error(Exception(message))
                    }
                    onBizOK<WendDaListRsp> { code, data, message ->
                        LogUtils.i("获取课程列表Paging3 BizOK $data")
                        val totalPage = data?.pageCount ?: 0

                        result = LoadResult.Page (
                            data = data?.datas ?: emptyList(),
                            prevKey = if (firstPage == 1) null else firstPage - 1, //todo 正式开发这里为null
                            nextKey = if (firstPage < totalPage) firstPage + 1 else null //todo 正式开发这里为nextPage
                                )
                    }
                }
                .onFailure {
                    LogUtils.e("获取课程列表Paging3 接口异常 ${it.message}")
                    result = LoadResult.Error(it)
                }
            return result
        }
    }
}

