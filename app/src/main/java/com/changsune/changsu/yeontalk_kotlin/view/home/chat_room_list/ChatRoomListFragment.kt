package com.changsune.changsu.yeontalk_kotlin.view.home.chat_room_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.changsune.changsu.yeontalk_kotlin.R
import com.changsune.changsu.yeontalk_kotlin.viewmodel.home.chat_room_list.ChatRoomListViewModel
import kotlinx.android.synthetic.main.fragment_chat_room_list.*

class ChatRoomListFragment : Fragment() {

    companion object {
        fun newInstance() = ChatRoomListFragment()
    }

    lateinit var viewModel: ChatRoomListViewModel

    private var chatRoomListRecyclerViewAdapter = ChatRoomListRecyclerViewAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_room_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerview_chatroom_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRoomListRecyclerViewAdapter
        }
        viewModel = ViewModelProviders.of(this).get(ChatRoomListViewModel::class.java)

        viewModel.refresh()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.chatRoomList.observe(this, Observer { chatRoomList ->
            chatRoomList?.let {
                chatRoomListRecyclerViewAdapter.updateChatRoomList(it)
            }
        })
    }

}
