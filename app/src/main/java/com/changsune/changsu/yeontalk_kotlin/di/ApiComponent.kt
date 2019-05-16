package com.changsune.changsu.yeontalk_kotlin.di

import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import com.changsune.changsu.yeontalk_kotlin.viewmodel.home.chat_room_list.ChatRoomListViewModel
import com.changsune.changsu.yeontalk_kotlin.viewmodel.home.user_list.UserListViewModel
import com.changsune.changsu.yeontalk_kotlin.viewmodel.intro.IntroViewModel
import com.changsune.changsu.yeontalk_kotlin.viewmodel.user_details.UserDetailsViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: YeonTalkService)

    fun inject(viewModel: IntroViewModel)

    fun inject(viewModel: UserListViewModel)

    fun inject(viewModel: ChatRoomListViewModel)

    fun inject(viewModel: UserDetailsViewModel)

}