package com.cniao5.course.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.cniao5.common.databinding.LayoutArticleBinding
import com.cniao5.course.net.WendDaListRsp

class CoursePagingAdapter(private val callback: (WendDaListRsp.Data) -> Unit) :
    PagingDataAdapter<WendDaListRsp.Data, CourseViewHolder>(differCallback){

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        //在第几项触发的加载
        getItem(position)?.let { article ->
            holder.bind(article)
            holder.itemView.setOnClickListener { //lambda表达式简化点击事件
                callback.invoke(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder.create(parent)
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<WendDaListRsp.Data>() {
            override fun areItemsTheSame(
                oldItem: WendDaListRsp.Data,
                newItem: WendDaListRsp.Data
            ) = oldItem.id == newItem.id
            //            ) = false

            override fun areContentsTheSame(
                oldItem: WendDaListRsp.Data,
                newItem: WendDaListRsp.Data
            ) = oldItem == newItem
        }

    }

}

class CourseViewHolder(private val binding: LayoutArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): CourseViewHolder {
            return CourseViewHolder(
                LayoutArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(item: WendDaListRsp.Data) {
        binding.apply {
            title = item.title
            author =item.author?:""
            niceDate = item.niceDate
            superChapterName = item.superChapterName
        }
        binding.executePendingBindings()
    }
}