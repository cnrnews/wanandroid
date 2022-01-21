package com.cniao5.course.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.base.BaseFragment
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.course.CourseViewModel
import com.cniao5.course.R
import com.cniao5.course.databinding.*
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
* 课程的Fragment
* 传入了R.layout.fragment_course之后就不用写onCreateView,因为布局已经被关联到fragment里了
* */
class CourseFragment: BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()
    private lateinit var mBinding: FragmentCourseBinding

    //传url并跳转到playvideoactivity
    private val coursePagingAdapter = CoursePagingAdapter { article ->
        // 文章详情
        WebViewActivity.openUrl(requireActivity(),article.link)
    }


    //传入布局资源,将布局和view绑定到一起
    override fun getLayoutRes() = R.layout.fragment_course

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        mBinding = FragmentCourseBinding.bind(view)

        return mBinding.apply {

            vm = viewModel


            val adapter = coursePagingAdapter
            //下方的load Header头部load
            rvCourse.adapter = adapter.withLoadStateFooter(CourseLoadAdapter(adapter))

            //Load上滑进度条监听
            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    pbFragmentCourse.visibility = View.VISIBLE
                } else {
                    pbFragmentCourse.visibility = View.GONE

                    //获取加载错误事件
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        ToastUtils.showShort(it.error.message)
                    }
                }
            }

            viewModel.apply {

                //进度条显示
                isLoading.observe(viewLifecycleOwner) {
                    //协程block获取数据代码块是否结束，协程结束时为false
                    pbFragmentCourse.visibility = if (it) View.VISIBLE else View.GONE
                }

            }
        }
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launchWhenCreated {
            viewModel.getCourseListPaging().collectLatest {
                coursePagingAdapter.submitData(it)
            }
        }
    }
}