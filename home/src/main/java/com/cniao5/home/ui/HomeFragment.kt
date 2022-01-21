package com.cniao5.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseFragment
import com.cniao5.common.model.DataResult
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.home.R
import com.cniao5.home.databinding.FragmentHomeBinding
import com.cniao5.home.net.*
import com.cniao5.home.ui.adapter.BannerAdapter
import com.cniao5.home.ui.adapter.HomeAdapter
import com.test.service.net.*
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
/**
 * @Author lihl
 * @Date 2022/1/16 9:17
 * @Email 1601796593@qq.com
 *
 * 主页的Fragment
 */

class HomeFragment : BaseFragment() {

    private val homeAdapter = HomeAdapter { article ->
        // 文章详情
        WebViewActivity.openUrl(requireActivity(),article.link)
    }

    private val viewModel: HomeViewModel by viewModel()

    private val bannerList = BannerList()

    private val bannerAdapter by lazy { BannerAdapter(bannerList) }


    private lateinit var mbinding: FragmentHomeBinding

    //传入布局资源,将布局和view绑定到一起
    override fun getLayoutRes() = R.layout.fragment_home

    //传入view,将view和databinding绑定到一起
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        mbinding = FragmentHomeBinding.bind(view)
        return mbinding.apply {


            // 添加header
            val bannerHome:Banner<BannerList.BannerListItem,BannerAdapter> =
                LayoutInflater.from(context).inflate(R.layout.item_banner,rvHome,false)
                        as Banner<BannerList.BannerListItem, BannerAdapter>
            homeAdapter.addHeaderView(bannerHome)

            rvHome.adapter = homeAdapter

                    // val bannerView = Banner<BannerList.BannerListItem,BannerAdapter>(context)
            // rvHome.addView(bannerView)

            //Load上滑进度条监听
            bannerHome.addBannerLifecycleObserver(viewLifecycleOwner) //生命周期观察者
                .setAdapter(bannerAdapter).indicator = CircleIndicator(requireContext()) //轮播图上的小点


            refreshLayout.setOnLoadmoreListener {

                mPage ++

                viewModel.getArticles(mPage)

                it.finishLoadmore()
            }
        }
    }

    private var mPage = 0
    override fun initConfig() {
        super.initConfig()
        viewModel.apply {
            //上方banner
            getBanner()

            getArticles(mPage)
        }


    }

    override fun initData() {
        super.initData()

        viewModel.apply {

            //加载进度条显示
            // isLoading.observe(viewLifecycleOwner) {
            //     //协程block获取数据代码块是否结束，协程结束时为false
            //     mbinding.pbFragmentCourse.visibility = if (it) View.VISIBLE else View.INVISIBLE
            // }

            // 轮播图
            liveBanner.observeKt {
                it ?: return@observeKt
                bannerList.clear()
                bannerList.addAll(it)
                bannerAdapter.notifyDataSetChanged()
            }

            // 文章列表
            liveArticles.observeKt {
                it ?: return@observeKt
                LogUtils.d("liveArticles:${mPage},${it.datas.size}")
                homeAdapter.addData(it.datas)
            }
        }
    }
    //
    // private fun loadData() {
    //     // viewModel.getCourseList(code, difficulty, is_free,q)
    //     lifecycleScope.launchWhenCreated {
    //         viewModel.getArticles(1).apply {  }
    //         }
    //     }
    // }
}
