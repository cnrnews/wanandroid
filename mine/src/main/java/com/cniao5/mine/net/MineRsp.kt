package com.cniao5.mine.net
import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.cniao5.mine.repo.UserEntity
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue


/**
 * 用户详情
 */
@Parcelize
data class UserDetail(
    val coinInfo: CoinInfo,
    val userInfo: UserInfo
):Parcelable
@Parcelize
data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String
):Parcelable

typealias UserInfo = UserEntity
// data class UserInfo(
//     val admin: Boolean,
//     val chapterTops: String?,//List<Int>
//     val coinCount: Int,
//     val collectIds: String?,//List<Int>
//     val email: String,
//     val icon: String,
//     val id: Int,
//     val nickname: String,
//     val password: String,
//     val publicName: String,
//     val token: String,
//     val type: Int,
//     val username: String
// ):Parcelable
// typealias UserInfoRsp = UserInfoRspData