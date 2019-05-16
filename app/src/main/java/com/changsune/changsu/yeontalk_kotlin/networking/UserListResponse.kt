package com.changsune.changsu.yeontalk_kotlin.networking

import com.changsune.changsu.yeontalk_kotlin.model.me.Me
import com.changsune.changsu.yeontalk_kotlin.model.user.User
import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("fetch_me")
    val userList_me: List<Me>?,
    @SerializedName("fetch_chatbot")
    val userList_chatbot: List<User>?,
    @SerializedName("fetch_favorites")
    val userList_favorite: List<User>?,
    @SerializedName("fetch_users")
    val userList_users: ArrayList<User>?
)