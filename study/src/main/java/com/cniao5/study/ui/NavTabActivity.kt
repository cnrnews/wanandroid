package com.cniao5.study.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.base.BaseActivity
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.study.R
import com.cniao5.study.databinding.ActivityNavTabBinding
import com.cniao5.study.net.TreeRspItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Author lihl
 * @Date 2022/1/19 13:58
 * @Email 1601796593@qq.com
 *
 * 体系文章列表
 */
class NavTabActivity:BaseActivity<ActivityNavTabBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_nav_tab

    override fun initView() {
        super.initView()

        mBinding.apply {

            //1.初始化fragment
            val jsonParam = intent.getStringExtra(TAG_PARAMS) ?: return@apply

            val treeRsp = GsonUtils.fromJson(jsonParam,TreeRspItem::class.java)
            title = treeRsp.name

            val count = treeRsp.children.size
            val fragments = mutableListOf<NavTabFragment>()
            for (i in 0 until count){

                fragments.add(NavTabFragment(treeRsp.children[i].id))
            }

            //2.绑定 adapter
            viewpager.adapter = TreeSubPagerAdapter(this@NavTabActivity,fragments)
            // viewpager + tablayout 绑定
            val mediator = TabLayoutMediator(tabLayout, viewpager) { tab, position ->
                tab.text =  treeRsp.children.get(position).name
            }
            mediator.attach()
            // 回到上一页面
            iconBack.setOnClickListener {
                finish()
            }
        }
    }
    companion object{
        fun openTreeSub(context: Context, jsonParams: String) {
            context.startActivity(Intent(context, NavTabActivity::class.java).also {
                it.putExtra(TAG_PARAMS, jsonParams)
            })
        }
    }
}
// 传递的体系分类实体
var TAG_PARAMS = "parames"
/**
 * ViewPager 的适配类
 */
class TreeSubPagerAdapter(framentActivity: FragmentActivity, val fragments: List<Fragment>) :
    FragmentStateAdapter(framentActivity){
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position] ?: error("请确保fragments数据源和 viewpager2的index匹配设置")
}