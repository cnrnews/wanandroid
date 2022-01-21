package com.cniao5.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.base.BaseFragment
import com.cniao5.mine.MineViewModel
import com.cniao5.mine.R
import com.cniao5.mine.databinding.FragmentMineBinding
import com.cniao5.mine.repo.UserInfoRspDBHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class MineFragment : BaseFragment() {

    private lateinit var binding:FragmentMineBinding

    private val viewModel: MineViewModel by viewModel()

    //传入布局资源,将布局和view绑定到一起
    override fun getLayoutRes() = R.layout.fragment_mine

    //传入view,将view和databinding绑定到一起
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        binding = FragmentMineBinding.bind(view)
        return binding.apply {
            vm = viewModel

            //头像 跳转到 个人信息
            ivUserIconMine.setOnClickListener {
                //点击的时候赋值以免拿到空值
                val info = viewModel.liveUserInfoBinding.value
                info?.let {
                    //TODO
                    // val action = MineFragmentDirections.actionMineFragmentToUserInfoFragment(info)
                    // findNavController().navigate(action)
                }
            }
            //未登录状态下点击用户名位置跳转到登录界面
            tvUserNameMine.setOnClickListener {
                val info = viewModel.liveUserInfoBinding.value
                if (info == null)
                    ARouter.getInstance().build("/login/login").navigation()
            }
        }
    }

    override fun initConfig() {
        super.initConfig()

        // 用户详情
        viewModel.apply {
            //如果Liveinfo数据更新不为空的话就存储到数据库中
            liveUserInfo.observeKt {

                it?.let {
                    UserInfoRspDBHelper.insertUserInfoRsp(requireContext(), it)
                    //将数据给到布局的livedata
                    liveUserInfoBinding.value = it
                    binding.tvUserNameMine.text = it?.username
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        UserInfoRspDBHelper.getLiveUserInfo(requireContext()).observeKt { info ->
            if (info == null){
                viewModel.getUserInfo()
            }else{
                binding.tvUserNameMine.text = info?.username
                viewModel.liveUserInfoBinding.value = info
            }
        }
    }
}