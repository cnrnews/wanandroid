package login.repo

import androidx.lifecycle.LiveData
import com.test.service.net.BaseResponse
import login.net.LoginReqBody
import login.net.LoginRsp
import login.net.RegisterRsp


/*
* 登录模块相关的抽象数据接口
* */
interface ILoginResource {

    //注册与否的检查结果
    val registerRsp: LiveData<RegisterRsp>

    //登录成功后的结果
    val loginRsp: LiveData<LoginRsp>
    // 登录失败
    val loginError:LiveData<BaseResponse>



    //body: LoginReqBody
    suspend fun login(username:String,password:String)

}