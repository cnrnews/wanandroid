package com.cniao5.mine.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
/**
 * @Author lihl
 * @Date 2022/1/20 18:05
 * @Email 1601796593@qq.com
 */
public class GsonConvert {

    @TypeConverter
    fun objectToString(list:List<Int>):String {
        return Gson().toJson(list);
    }

    @TypeConverter
    fun stringToObject(json:String): List<Int>  {
        val listType =  object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, listType);
    }
}