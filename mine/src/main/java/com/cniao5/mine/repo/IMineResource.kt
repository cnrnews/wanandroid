package com.cniao5.mine.repo

import androidx.lifecycle.LiveData
import com.cniao5.mine.net.UserInfo


/*
* Mine模块的数据获取 接口
* */
interface IMineResource {

    // 用户详情
    val userInfo:LiveData<UserInfo>
    // 用户详情
    suspend fun getUserInfo()

}