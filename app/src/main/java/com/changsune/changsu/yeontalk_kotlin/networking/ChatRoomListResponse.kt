package com.changsune.changsu.yeontalk_kotlin.networking

import com.changsune.changsu.yeontalk_kotlin.model.chatroom.ChatRoom
import com.google.gson.annotations.SerializedName

data class ChatRoomListResponse (
    @SerializedName("chat_room_list")
    val chatRoomList: List<ChatRoom>?
)