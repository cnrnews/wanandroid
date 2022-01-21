package com.cniao5.study.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.cniao5.study.R
import com.cniao5.study.databinding.ItemRvBinding
import com.cniao5.study.net.*
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class StudiedAdapter(val callback:(group:Int,children:Int)->Unit): RecyclerView.Adapter<StudiedVH>() {

    private val mList = mutableListOf<Any>()

    fun submit(list: List<Any>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudiedVH {
        val stuVH = StudiedVH.createVH(parent)

        return stuVH
    }

    override fun onBindViewHolder(holder: StudiedVH, position: Int) {
        holder.bind(mList[position])
        holder.setCallback(position,callback)
    }

    override fun getItemCount() = mList.size
}

class StudiedVH(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root){

    val mInflater = LayoutInflater.from(binding.tagFlow.context)

    companion object {
        fun createVH(parent: ViewGroup): StudiedVH {
            return StudiedVH(
                ItemRvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
    var group: Int = 0
    lateinit var callback:(group:Int,children:Int)->Unit
    fun setCallback(group: Int,callback:(group:Int,children:Int)->Unit){
        this.group = group
        this.callback = callback
    }
    fun bind(info: Any) {
        when(info){
            is TreeRspItem -> {
                binding.title = info.name
                setSubAdapter(info.children)
            }
            is NavRspItem -> {
                binding.title = info.name
                setSubAdapter(info.articles)
            }
        }
        //把获取到的progress进度条数据给进度条 也可以用扩展函数app:progress_current实现
        // binding.npbProgressItemStudy.progress = info.progress.toInt()
        // 显示分类子菜单

        binding.tagFlow.setOnTagClickListener { view, position, parent ->
            LogUtils.d("childClick:$position")
            callback.invoke(group,position)
            true
        }
        // binding.card.setOnClickListener {
        //     ToastUtils.showShort("点击了${info.name}")
        // }
        // binding.executePendingBindings()
    }

    private fun setSubAdapter(tags: List<Any>) {

        binding.tagFlow.adapter = object : TagAdapter<Any>(tags){

            override fun getView(
                parent: FlowLayout?,
                position: Int,
                tag: Any?
            ): View {
                val tagView:TextView =
                    mInflater.inflate(R.layout.item_tag, binding.tagFlow, false) as TextView;

                when(tag){
                    is Children -> {
                        tagView.text = tag.name
                    }
                    is Article -> {
                        tagView.text = tag.title
                    }
                }
                return tagView;
            }
        }
    }
}