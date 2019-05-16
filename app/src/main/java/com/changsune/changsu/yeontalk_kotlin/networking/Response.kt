package com.changsune.changsu.yeontalk_kotlin.networking

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?
)