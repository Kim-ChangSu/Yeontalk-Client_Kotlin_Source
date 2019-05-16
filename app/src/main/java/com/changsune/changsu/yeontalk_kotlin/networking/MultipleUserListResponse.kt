package com.changsune.changsu.yeontalk_kotlin.networking

import com.google.gson.annotations.SerializedName

data class MultipleUserListResponse (
    @SerializedName("fetch_multiple_userlist")
    val multipleUserList: List<UserListResponse>?
)