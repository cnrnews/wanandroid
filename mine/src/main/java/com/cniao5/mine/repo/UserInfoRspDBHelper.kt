package com.cniao5.mine.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.cniao5.mine.net.UserDetail
import com.cniao5.mine.net.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
* 个人信息数据库操作帮助类
* */
object UserInfoRspDBHelper {

    /*
    * 获取room数据表中存储的userinforsp
    * return livedata类型的观察者
    * */
    fun getLiveUserInfo(context: Context): LiveData<UserInfo> =
        UserDetailDB.getInstance(context).userinforspDao().queryLiveUserDetail()

    /*
    * 获取userinforsp
    * 以普通数据对象的形式
    * */
    fun getUserInfoRsp(context: Context) =
        UserDetailDB.getInstance(context).userinforspDao().queryUserDetail()

    /*
    * 删除数据表中的userinforsp信息
    * */
    fun deleteUserInfoRsp(context: Context) {
        //用户网络请求和文件访问要指定Dispatchers.IO线程
        GlobalScope.launch(Dispatchers.IO) {
            //如果查询表返回结果为空的话就不执行删除
            getUserInfoRsp(context)?.let {
                // TODO
                // UserInfoRspDB.getInstance(context).userinforspDao().deleteUserInfoRsp(it)
            }
        }
    }

    /*
    * 新增个人信息到数据表
    * */
    fun insertUserInfoRsp(context: Context, userInfoRsp: UserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            UserDetailDB.getInstance(context).userinforspDao().insertUserDetail(userInfoRsp)
        }
    }
}























