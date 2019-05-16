package com.changsune.changsu.yeontalk_kotlin.model.chatroom

import com.google.gson.annotations.SerializedName

data class ChatRoom(
    @SerializedName("chat_room_last_user_id")
    var lastChatUserId: String,
    @SerializedName("chat_room_last_date")
    var lastChatDate: String,
    @SerializedName("chat_room_last_message")
    var lastChatMessage: String,
    @SerializedName("chat_room_last_type")
    var lastChatType: String,
    @SerializedName("chat_room_id")
    var roomId: String,
    @SerializedName("chat_room_user_id")
    var userId: String,
    @SerializedName("chat_room_user_device_id")
    var userDeviceId: String,
    @SerializedName("chat_room_user_image")
    var userImage: String,
    @SerializedName("chat_room_user_nickname")
    var userNickName: String,
    @SerializedName("chat_room_unshown_num")
    var unshown_num: String

)