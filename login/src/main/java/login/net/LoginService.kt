package login.net

import com.test.service.net.BaseResponse
import retrofit2.Call
import retrofit2.http.*

/*
* 登录模块的接口
* */
interface LoginService {
    //登录
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username:String,
              @Field("password")password:String,): Call<BaseResponse>



}