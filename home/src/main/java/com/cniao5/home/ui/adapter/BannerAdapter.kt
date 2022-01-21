package com.cniao5.home.ui.adapter

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cniao5.common.webview.WebViewActivity
import com.cniao5.home.R
import com.cniao5.home.net.BannerList
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/*
* 首页上方banner的适配器,传入bannerList解析的数据格式
* */
class BannerAdapter(private val bannerList: BannerList) : BannerImageAdapter<BannerList.BannerListItem>(bannerList) {
    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerList.BannerListItem,
        position: Int,
        size: Int
    ) {
        holder ?: return //如果为空的话直接返回

        holder.imageView.scaleType = ImageView.ScaleType.FIT_XY

        //不为空时加载图片
        Glide.with(holder.itemView)
            .load( data?.imagePath)
            // .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            WebViewActivity.openUrl(it.context as Activity, data.url)
        }
    }
}