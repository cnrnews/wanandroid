package com.cniao5.study.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.GsonUtils
import com.cniao5.common.base.BaseFragment
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.study.R
import com.cniao5.study.databinding.FragmentStudyBinding
import com.cniao5.study.net.NavRsp
import com.cniao5.study.net.TreeRsp
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : BaseFragment() {
    private val viewModel: StudyViewModel by viewModel()

    // Tab 索引
    var tabIndex = 0
    lateinit var  treeRsp:TreeRsp
    lateinit var navRsp:NavRsp

    private val tagAdapter = StudiedAdapter {group,position ->
        if (tabIndex == 0){
            val rspItem = viewModel.liveTreeList.value?.get(group)
            NavTabActivity.openTreeSub(requireActivity(), GsonUtils.toJson(rspItem))
        }else{
            val link = viewModel.liveNavList.value?.get(group)?.articles?.get(position)?.link
            link?.let { WebViewActivity.openUrl(requireActivity(), it) }
        }
    }
    //传入布局资源,将布局和view绑定到一起
    override fun getLayoutRes() = R.layout.fragment_study

    //传入view,将view和databinding绑定到一起
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentStudyBinding.bind(view).apply {
            // vm = viewModel //记得加 关联viewmodel对象
            sysRecycler.adapter = tagAdapter

            tabLayout.addTab(tabLayout.newTab().setText("体系"))
            tabLayout.addTab(tabLayout.newTab().setText("导航"))

            //tablayout点击事件
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                //tab被选的时候回调 切换不同 tag 数据显示
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabIndex = tab!!.position
                    if (tabIndex == 0){
                        // viewModel?.getNavList()
                        tagAdapter.submit(treeRsp)
                    }else{
                        tagAdapter.submit(navRsp)
                    }
                }
                //tab未被选择时回调
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                //tab重新选择时回调
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }
    override fun initConfig() {
        super.initConfig()
        // 加载数据
        viewModel.apply {
            getTreeList()
            getNavList()
        }
    }
    override fun initData() {
        super.initData()
        viewModel.apply {
            liveTreeList.observeKt {
                it ?: return@observeKt
                treeRsp = it
                tagAdapter.submit(it)
            }
            liveNavList.observeKt {
                it ?: return@observeKt
                navRsp = it
                // tagAdapter.submit(it)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        navRsp.clear()
        treeRsp.clear()
    }
}

