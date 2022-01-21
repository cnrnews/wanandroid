package com.test.service.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName

/*
* 菜鸟App的公共业务基础数据类
* 数据库的增删改查读写应该放在非UI线程
* */

//1、entity的定义
@Entity(tableName = "tv_user")
data class UserInfo(
    @PrimaryKey
    val idd: Int, //主键
    val admin: Boolean,
    // val chapterTops: List<Any>,
    val coinCount: Int,
    // val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int, // 用户ID
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)


//2、dao层的定义
@Dao
interface UserDao {

    //增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserInfo)

    //改
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserInfo)

    //删
    @Delete
    fun deleteUser(user: UserInfo)

    //条件判断： =精确判断  in多种条件的判断  like模糊的判断
    //查数据表有可能为空
    @Query("select * from tv_user limit 0,1")
    fun queryLiveUser(): LiveData<UserInfo>

    @Query("select * from tv_user where idd = :idd")
    fun queryUser(idd: Int = 0): UserInfo

}

//3、database定义数据库
@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    //单例
    companion object {
        private const val DB_NAME = "database_db"

        @Volatile //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序
        private var instance: DataBase? = null

        @Synchronized //保证线程安全
        fun getInstance(context: Context): DataBase {
            return instance ?: Room.databaseBuilder(
                context,
                DataBase::class.java,
                DB_NAME
            ).build().also { instance = it }
        }
    }
}