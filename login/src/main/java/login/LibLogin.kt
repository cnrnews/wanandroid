package login

import login.net.LoginService
import login.repo.ILoginResource
import login.repo.LoginRepo
import com.cniao5.common.network.KtRetrofit
import com.cniao5.common.utils.getBaseHost
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*
* koin注解管理类
* */
val moduleLogin: Module = module {


    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(LoginService::class.java)
    }

    //repo ILoginResource
    single { LoginRepo(get()) } bind ILoginResource::class

    //viewModel
    viewModel { LoginViewModel(get()) }
}