package com.cniao5.study.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseFragment
import com.cniao5.common.model.DataResult
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.study.R
import com.cniao5.study.databinding.FragmentNavTabBinding
import com.cniao5.study.net.NavRsp
import com.cniao5.study.net.TreeSub
import com.test.service.net.*
import org.koin.androidx.viewmodel.ext.android.viewModel
/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 主页的Fragment
 */

class NavTabFragment(val cid:Int = 0) : BaseFragment() {

    private val homeAdapter = NavTabAdapter { article ->
        // 文章详情
        WebViewActivity.openUrl(requireActivity(),article.link)
    }

    // 存储所有 tab 页面的数据
    private val navTabDatas = mutableMapOf<Int, TreeSub>()

    private val viewModel: StudyViewModel by viewModel()


    private lateinit var mbinding: FragmentNavTabBinding

    //传入布局资源,将布局和view绑定到一起
    override fun getLayoutRes() = R.layout.fragment_nav_tab

    //传入view,将view和databinding绑定到一起
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        mbinding = FragmentNavTabBinding.bind(view)

        return mbinding.apply {

            navTabRv.adapter = homeAdapter
            refreshLayout.setOnLoadmoreListener {

                mPage ++

                viewModel.getTreeSubList(mPage,cid)

                it.finishLoadmore()
            }
        }
    }

    private var mPage = 0
    override fun initConfig() {
        super.initConfig()
        viewModel.apply {
            getTreeSubList(mPage,cid)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun initData() {
        super.initData()
        viewModel.apply {
            // 文章列表
            liveTreeSubList.observeKt {
                it ?: return@observeKt

                val chapterId = it.datas.get(0).chapterId
                if(mPage==0){
                    // 是否加载的事同一 cid 的数据
                    if (chapterId == cid){
                        homeAdapter.setList(it.datas)
                    }
                }else{
                    // 是否加载的事同一 cid 的数据
                    if (chapterId == cid) {
                        homeAdapter.addData(it.datas)
                    }
                }
            }
        }
    }
}
