package com.changsune.changsu.yeontalk_kotlin.networking

import com.changsune.changsu.yeontalk_kotlin.model.userdetails.UserDetails
import com.google.gson.annotations.SerializedName

data class UserDetailsResponse (

    @SerializedName("users")
    val userDetails: List<UserDetails>?
)