package com.cniao5.mine.repo

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.cniao5.mine.utils.GsonConvert
import kotlinx.parcelize.Parcelize

/*
* 个人信息的数据类
* PS:数据库的增删改查读写应该放在非UI线程
* */
//1、entity的定义
@TypeConverters(GsonConvert::class)
@Entity(tableName = "tv_user")
@Parcelize //可序列化的数据类型,必须保证里面的变量都是可序列化的
public class UserEntity(
    @PrimaryKey
    val idd: Int, //主键
    val admin: Boolean,
    val chapterTops: List<Int>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
): Parcelable

//2、定义Dao层
@Dao
interface UserDetailDao {
    //增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(userInfoRsp: UserEntity)

    //改
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserDetail(userInfoRsp: UserEntity)

    //删
    @Delete
    fun deleteUserDetail(userInfoRsp: UserEntity)

    //查，查询数据表有可能为空
    @Query("select * from tv_user where idd = :idd")
    fun queryLiveUserDetail(idd: Int = 0): LiveData<UserEntity>

    @Query("select * from tv_user where idd = :idd")
    fun queryUserDetail(idd: Int = 0): UserEntity

}

//3、database定义数据库
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDetailDB : RoomDatabase() {

    abstract fun userinforspDao(): UserDetailDao

    companion object {
        private const val DB_NAME = "userinforsp_db"

        @Volatile //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        private var instance: UserDetailDB ?= null

        //保证线程安全
        @Synchronized
        fun getInstance(context: Context): UserDetailDB {
            return instance ?: Room.databaseBuilder(
                context,
                UserDetailDB::class.java,
                DB_NAME
            ).build().also { instance = it }
        }

    }

}




























