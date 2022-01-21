package com.test.service.net

import androidx.annotation.Keep
import com.google.gson.JsonObject


/*
* 基础的网络返回数据
* @Keep在混淆的时候会保留@Keep类的信息
* */
@Keep
data class BaseResponse(
    var errorCode: Int,  //响应码
    var data: Any?, //响应数据内容
    var errorMsg: String? //响应数据的结果描述
) {
    companion object {
        const val SERVER_CODE_FAILED = 0 //接口请求响应业务处理 失败
        const val SERVER_CODE_SUCCESS = 0 //接口请求响应业务处理 成功
        const val SERVER_CODE_SUCCESS1 = 0 //接口请求响应业务处理 成功
    }
}
