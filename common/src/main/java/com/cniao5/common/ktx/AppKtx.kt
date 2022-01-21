package com.cniao5.common.ktx

import android.app.Application


/*
* Application的扩展字段 参数一致性
*/
val Application.application: Application
    get() = this