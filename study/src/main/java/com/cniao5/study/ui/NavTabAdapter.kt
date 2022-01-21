package com.cniao5.study.ui
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cniao5.common.databinding.LayoutArticleBinding
import com.cniao5.study.R
import com.cniao5.study.net.DataX

/**
 * 首页 adapter
 * 支持添加header footer 的库
 */
class NavTabAdapter(private val callback: (DataX) -> Unit): BaseQuickAdapter<DataX,
        BaseDataBindingHolder<LayoutArticleBinding>>(R.layout.layout_article) {

    override fun convert(
        holder: BaseDataBindingHolder<LayoutArticleBinding>,
        item: DataX
    ) {
        holder.dataBinding?.apply {
            title = item.title
            author =item.author?:""
            niceDate = item.niceDate
            superChapterName = item.superChapterName
        }

        holder.itemView.setOnClickListener {
            callback?.let {
                it.invoke(item)
            }
        }
    }
}
