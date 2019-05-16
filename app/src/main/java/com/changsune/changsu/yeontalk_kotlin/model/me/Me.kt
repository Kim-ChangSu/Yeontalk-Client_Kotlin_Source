package com.changsune.changsu.yeontalk_kotlin.model.me

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Me(
    @SerializedName("me_birthyear")
    val meBirthYear: String?,
    @SerializedName("me_device_id")
    val meDeviceId: String?,
    @SerializedName("me_gender")
    val meGender: String?,
    @SerializedName("me_id")
    val meId: String?,
    @SerializedName("me_introduction")
    val meIntroduction: String?,
    @SerializedName("me_login_time")
    val meLogInTime: String?,
    @SerializedName("me_nation")
    val meNation: String?,
    @SerializedName("me_nickname")
    val meNickname: String?,
    @SerializedName("me_profile_image")
    val meProfileImage: String?,
    @SerializedName("me_region")
    val meRegion: String?,
    @SerializedName("me_point")
    val mePoint: String?
)