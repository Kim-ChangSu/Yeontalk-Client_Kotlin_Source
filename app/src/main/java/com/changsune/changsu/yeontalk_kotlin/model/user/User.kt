package com.changsune.changsu.yeontalk_kotlin.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_birthyear")
    val userBirthYear: String?,
    @SerializedName("user_device_id")
    val userDeviceId: String?,
    @SerializedName("user_gender")
    val userGender: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("user_introduction")
    val userIntroduction: String?,
    @SerializedName("user_login_time")
    val userLogInTime: String?,
    @SerializedName("user_nation")
    val userNation: String?,
    @SerializedName("user_nickname")
    val userNickname: String?,
    @SerializedName("user_profile_image")
    val userProfileImage: String?,
    @SerializedName("user_region")
    val userRegion: String?
)