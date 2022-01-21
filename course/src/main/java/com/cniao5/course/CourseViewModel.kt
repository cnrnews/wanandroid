package com.cniao5.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cniao5.common.base.BaseViewModel
import com.cniao5.course.repo.ICourseResource

class CourseViewModel(val repo: ICourseResource) : BaseViewModel() {


    suspend fun getCourseListPaging(
    ) = repo.getWenDaListPaging().cachedIn(viewModelScope) //跟viewModel的生命周期绑定
}