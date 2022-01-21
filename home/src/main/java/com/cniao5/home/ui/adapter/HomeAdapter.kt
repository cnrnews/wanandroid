package com.cniao5.home.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cniao5.common.databinding.LayoutArticleBinding
import com.cniao5.home.R
import com.cniao5.home.net.Article

/**
 * 首页 adapter
 * 支持添加header footer 的库
 */
class HomeAdapter(private val callback: (Article.Data) -> Unit):BaseQuickAdapter<Article.Data,
        BaseDataBindingHolder<LayoutArticleBinding>>(R.layout.layout_article) {
    override fun convert(
        holder: BaseDataBindingHolder<LayoutArticleBinding>,
        item: Article.Data
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
