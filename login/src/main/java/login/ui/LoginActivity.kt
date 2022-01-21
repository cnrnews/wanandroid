package login.ui

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseActivity
import com.cniao5.common.ktx.context
import com.cniao5.common.network.config.SP_KEY_USER_TOKEN
import com.cniao5.common.utils.MySpUtils
import com.test.service.repo.DbHelper
import login.LoginViewModel
import login.R
import login.databinding.ActivityLoginBinding
import login.net.RegisterRsp
import org.koin.androidx.viewmodel.ext.android.viewModel

//路由地址
@Route(path = "/login/login")
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_login

    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
            //左上角退出按钮点击事件
            ivClose.setOnClickListener {
                finish()
            }
        }
    }

    override fun initConfig() {
        super.initConfig()
        //在UI里观察两个请求的返回结果
        viewModel.apply {
            // 监听登录接口返回的结果数据
            liveLoginRsp.observerKt {
                it.also {
                    //将数据保存到数据库里
                    it?.let { DbHelper.insertUserInfo(context, it) }
                    //保存token值
                    // MySpUtils.put(SP_KEY_USER_TOKEN, it?.token)
                }
                //关闭Activity
                finish()
            }
            /**
             * 监听登录错误请求
             */
            liveLoginError.observerKt {
                ToastUtils.showShort(it?.errorMsg)
                Toast.makeText(this@LoginActivity,it?.errorMsg,Toast.LENGTH_SHORT).show()
            }
        }
    }
}