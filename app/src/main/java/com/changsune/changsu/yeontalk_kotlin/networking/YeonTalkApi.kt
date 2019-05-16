package com.changsune.changsu.yeontalk_kotlin.networking

import io.reactivex.Single
import retrofit2.http.*

interface YeonTalkApi {

    @FormUrlEncoded
    @POST("confirmuserbydeviceid.php")
    fun confirmuserbydeviceid(@Field("user_device_id") deviceId: String): Single<Response>

    @FormUrlEncoded
    @POST("fetchuserslist.php")
    fun fetchUserList(@Field("user_device_id") user_device_id: String, @Field("load_limit") load_limit: String, @Field("user_login_time_loaded_last") user_login_time_loaded_last: String, @Field("selection_gender") selection_gender: String, @Field("selection_max_birthyear") selection_max_birthyear: String, @Field("selection_min_birthyear") selection_min_birthyear: String, @Field("selection_nation") selection_nation: String, @Field("selection_region") selection_region: String): Single<MultipleUserListResponse>

    @FormUrlEncoded
    @POST("fetchusersimagelist.php")
    fun fetchUsersImageList(@Field("user_device_id") user_device_id: String, @Field("load_limit") load_limit: String, @Field("user_login_time_loaded_last") user_login_time_loaded_last: String, @Field("selection_gender") selection_gender: String, @Field("selection_max_birthyear") selection_max_birthyear: String, @Field("selection_min_birthyear") selection_min_birthyear: String, @Field("selection_nation") selection_nation: String, @Field("selection_region") selection_region: String): Single<UserListResponse>

    @GET("fetchuserdetails.php")
    fun fetchUserDetails(@Query("user_id") userId: String): Single<UserDetailsResponse>

    @FormUrlEncoded
    @POST("insertfavorites.php")
    fun insertFavorite(@Field("user_id_from") user_id_from: String, @Field("user_id_to") user_id_to: String): Single<Response>

    @FormUrlEncoded
    @POST("deletefavorites.php")
    fun deleteFavorite(@Field("user_id_from") user_id_from: String, @Field("user_id_to") user_id_to: String): Single<Response>

    @FormUrlEncoded
    @POST("chat/fetchchatroomlist.php")
    fun fetchChatRoomListByUserId(@Field("user_id") str: String): Single<ChatRoomListResponse>
}