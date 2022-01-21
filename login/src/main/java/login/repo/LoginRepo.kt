package login.repo

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.model.SingleLiveData
import com.cniao5.common.network.support.serverData
import com.test.service.net.*
import login.net.*

/*
* 数据管理类的实现
* */

class LoginRepo(private val service: LoginService) : ILoginResource{

    private val _loginRsp = SingleLiveData<LoginRsp>()
    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginError = SingleLiveData<BaseResponse>()


    override val loginRsp: LiveData<LoginRsp> = _loginRsp
    override var loginError: LiveData<BaseResponse> = _loginError
    override val registerRsp: LiveData<RegisterRsp> = _registerRsp



///body: LoginReqBody
    override suspend fun login(username:String,password:String) {
        service.login(username, password)
            .serverData()
            .onSuccess { //接口请求成功
                onBizError { code, message ->
                    _loginError.value = BaseResponse(code,null,message)
                }
                onBizOK<LoginRsp> { code, data, message ->
                    _loginRsp.value = data
                    //同步到room数据库 登录状态
                    // LogUtils.i("登录接口 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                _loginError.value = BaseResponse(0,null,it.message)
            }
    }
}