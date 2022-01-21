package com.cniao5.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseViewModel
import com.cniao5.mine.net.UserDetail
import com.cniao5.mine.net.UserInfo
import com.cniao5.mine.repo.IMineResource

/*
* Mine模块的viewModel
* */
class MineViewModel(private val repo: IMineResource): BaseViewModel() {

    //用在userInfoRspFragment中
    val liveUserInfo: LiveData<UserInfo> = repo.userInfo

    //用于布局的Livedata
    val liveUserInfoBinding = MutableLiveData<UserInfo>()

    /*
    * 获取用户信息
    * */
    fun getUserInfo() {
        serverAwait {
            repo.getUserInfo()
        }
    }
}