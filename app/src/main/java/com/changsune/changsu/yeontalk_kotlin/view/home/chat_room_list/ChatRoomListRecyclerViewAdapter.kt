package com.changsune.changsu.yeontalk_kotlin.view.home.chat_room_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.model.chatroom.ChatRoom
import com.changsune.changsu.yeontalk_kotlin.util.image_loader.loadImage

import kotlinx.android.synthetic.main.layout_chat_room_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatRoomListRecyclerViewAdapter(var list: ArrayList<ChatRoom>) : RecyclerView.Adapter<ChatRoomListRecyclerViewAdapter.ViewHolder>() {

    fun updateChatRoomList(newChatRoomList: List<ChatRoom>) {
        list.clear()
        list.addAll(newChatRoomList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Todo Butterknife bindings

        private var imageView_user_image = view.circleimageview_image_chat_room_item_id
        private var textview_last_chat = view.textview_last_chat_chat_room_item_id
        private  var textview_last_chat_date = view.textview_date_chat_room_item_id
        private var textview_userNickName = view.textview_nickname_chat_room_item_id
        private var textview_unshown_num = view.textview_unshown_num_chat_room_item_id
        private var relativelayout_unshown_num = view.relativeLayout_unshown_num_chat_room_item_id

        fun bind(chatRoom: ChatRoom) {

            var date_str: String
            val date_item: String
            val mSimpleDateFormat = SimpleDateFormat("yyMM월 dd일HHmmssSSaa hh:mm", Locale.KOREA)
            val currentTime = Date()
            date_str = mSimpleDateFormat.format(currentTime)
            date_str = date_str.substring(0, 9)

            if (chatRoom != null) {

                val mDate: String
                if (chatRoom!!.lastChatDate == null || chatRoom!!.lastChatDate.length < 17) {
                    mDate = chatRoom!!.lastChatDate
                } else {
                    //오늘과 비교하여, 시간, 날짜 표기법 달리함
                    date_item = chatRoom!!.lastChatDate.substring(0, 9)
                    if (date_item == date_str) {
                        mDate = chatRoom!!.lastChatDate.substring(17)
                    } else {
                        mDate = chatRoom!!.lastChatDate.substring(2, 9)
                    }
                }

                if (chatRoom.userImage == null || chatRoom.userImage.equals("")) {
                    imageView_user_image.setImageResource(R.drawable.ic_person_black_24dp)
                } else {
                    imageView_user_image.loadImage(chatRoom.userImage)

                }

                textview_userNickName.setText((chatRoom as ChatRoom).userNickName)
                textview_last_chat_date.setText(mDate)

                if ((chatRoom as ChatRoom).lastChatType.equals(Constants.CHAT_TYPE) || (chatRoom as ChatRoom).lastChatType.equals(Constants.VIDEO_CALL_TYPE)) {
                    textview_last_chat.setText((chatRoom as ChatRoom).lastChatMessage)
                } else if ((chatRoom as ChatRoom).lastChatType.equals(Constants.IMAGE_TYPE)) {

                    textview_last_chat.setText("사진을 보냈습니다.")


                } else if ((chatRoom as ChatRoom).lastChatType.equals(Constants.VIDEO_TYPE)) {

                    textview_last_chat.setText("동영상을 보냈습니다.")


                } else {
                    textview_last_chat.setText("(알 수 없음)님이 채팅방에서 나가셨습니다.")
                }

                if (chatRoom!!.unshown_num.equals("0")) {
                    relativelayout_unshown_num.setVisibility(View.INVISIBLE)
                } else if (Integer.valueOf(chatRoom!!.unshown_num) > 99) {
                    relativelayout_unshown_num.setVisibility(View.VISIBLE)
                    textview_unshown_num.setText("99")
                } else {
                    relativelayout_unshown_num.setVisibility(View.VISIBLE)
                    textview_last_chat.setText(chatRoom!!.unshown_num)
                }

                itemView.setOnClickListener(View.OnClickListener {
//                    if (chatRoom.getUserDeviceId().equals("chatbot")) {
//                        ChatBotActivity.start(mContext, chatRoom.getUserId(), chatRoom.userNickName, chatRoom.getRoomId(), chatRoom.userImage)
//                    } else {
//                        ChatActivity.start(mContext, chatRoom.getUserId(), chatRoom.userNickName, chatRoom.getRoomId(), chatRoom.userImage)
//                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_chat_room_list_item, parent, false)
    )

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    companion object {

        private val TAG = ChatRoomListRecyclerViewAdapter::class.java.simpleName
    }

}