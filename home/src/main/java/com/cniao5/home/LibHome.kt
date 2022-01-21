package com.cniao5.home

import com.cniao5.common.network.KtRetrofit
import com.cniao5.common.utils.getBaseHost
import com.cniao5.home.net.HomeService
import com.cniao5.home.repo.HomeResource
import com.cniao5.home.repo.IHomeResource
import com.cniao5.home.ui.HomeViewModel
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 依赖注入管理 Home的module
 */
val moduleHome = module {

    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(HomeService::class.java)
    }

    //repo IMineResource
    single { HomeResource(get()) } bind IHomeResource::class

    viewModel { HomeViewModel(get()) }

}