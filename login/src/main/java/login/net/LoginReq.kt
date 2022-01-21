package login.net

import androidx.annotation.Keep

/*
* 登录模块相关的请求数据类
* */
@Keep
data class LoginReqBody (val username: String,val password: String)