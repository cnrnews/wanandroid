package login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseViewModel
import login.net.LoginReqBody
import login.repo.ILoginResource

class LoginViewModel(private val resource: ILoginResource): BaseViewModel() {

    /*
    * ObservableField只有在数据发生改变时UI才会收到通知，而LiveData不同，只要你postValue或者setValue，UI都会收到通知，不管数据有无变化
    * livedata还能感知activity的生命周期，在Activity不活动的时候不会触发
    * */
    val obMobile = ObservableField<String>() //账号
    val obPassword = ObservableField<String>() //密码

    //返回的数据
    val liveRegisterRsp = resource.registerRsp
    val liveLoginRsp = resource.loginRsp
    val liveLoginError = resource.loginError

    // 登录请求
    fun login() {
        val account = obMobile.get() ?: return
        val password = obPassword.get() ?: return
        serverAwait {
            resource.login("Mr.HL", "@159346")
        }
    }
}