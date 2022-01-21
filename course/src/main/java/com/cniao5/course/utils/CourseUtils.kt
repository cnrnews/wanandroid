package com.cniao5.course.utils

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi

/*
* 工具类
* */
object CourseUtils {

    /*
    * 总课时 + 评价人数
    * */
    @RequiresApi(Build.VERSION_CODES.N)
    @JvmStatic
    fun parseHtml(html: String?): String {
        return Html.fromHtml(html,-1).toString()
    }
}