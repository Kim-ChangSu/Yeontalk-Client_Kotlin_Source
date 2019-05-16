package com.changsune.changsu.yeontalk_kotlin.networking

import com.changsune.changsu.yeontalk_kotlin.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class YeonTalkService {

    @Inject
    lateinit var api: YeonTalkApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getResultOfConfirmingRegistration(deviceId: String): Single<Response> {
        return api.confirmuserbydeviceid(deviceId)
    }

    fun fetchUserList(user_device_id: String, load_limit: String, user_login_time_loaded_last: String, selection_gender: String, selection_max_birthyear: String, selection_min_birthyear: String, selection_nation: String, selection_region: String): Single<MultipleUserListResponse> {
        return api.fetchUserList(user_device_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region)
    }

    fun fetchUserImageList(user_device_id: String, load_limit: String, user_login_time_loaded_last: String, selection_gender: String, selection_max_birthyear: String, selection_min_birthyear: String, selection_nation: String, selection_region: String): Single<UserListResponse> {
        return api.fetchUsersImageList(user_device_id, load_limit, user_login_time_loaded_last, selection_gender, selection_max_birthyear, selection_min_birthyear, selection_nation, selection_region)
    }

    fun fetchChatRoomList(user_id: String): Single<ChatRoomListResponse> {
        return api.fetchChatRoomListByUserId(user_id)
    }

    fun fetchUserDetails(userId: String): Single<UserDetailsResponse> {
        return api.fetchUserDetails(userId)
    }

    fun insertFavorite(user_id_from: String, user_id_to: String): Single<Response> {
        return api.insertFavorite(user_id_from, user_id_to)
    }

    fun deleteFavorite(user_id_from: String, user_id_to: String): Single<Response> {
        return api.deleteFavorite(user_id_from, user_id_to)
    }
}