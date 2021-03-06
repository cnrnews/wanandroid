package com.cniao5.study

import com.cniao5.common.network.KtRetrofit
import com.cniao5.common.utils.getBaseHost
import com.cniao5.study.net.StudyService
import com.cniao5.study.repo.IStudyResource
import com.cniao5.study.repo.StudyResource
import com.cniao5.study.ui.StudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*
* 依赖注入管理 study的module
* */
val moduleStudy = module {

    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(StudyService::class.java)
    }

    //repo IMineResource
    single { StudyResource(get()) } bind IStudyResource::class

    viewModel { StudyViewModel(get()) }

}