package com.changsune.changsu.yeontalk_kotlin.model.userdetails

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class UserDetails(
    @SerializedName("user_birthyear")
    val userBirthYear: String?,
    @SerializedName("user_device_id")
    val userDeviceId: String?,
    @SerializedName("user_gender")
    val userGender: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("user_images")
    val userImages: ArrayList<String>?,
    @SerializedName("user_introduction")
    val userIntroduction: String?,
    @SerializedName("user_login_time")
    val userLoginTime: String?,
    @SerializedName("user_nation")
    val userNation: String?,
    @SerializedName("user_nickname")
    val userNickName: String?,
    @SerializedName("user_profile_image")
    val userProfileImage: String?,
    @SerializedName("user_region")
    val userRegion: String?
)