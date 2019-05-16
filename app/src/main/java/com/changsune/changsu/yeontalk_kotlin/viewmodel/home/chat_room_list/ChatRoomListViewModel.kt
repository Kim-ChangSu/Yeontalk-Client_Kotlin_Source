package com.changsune.changsu.yeontalk_kotlin.viewmodel.home.chat_room_list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.content.Context
import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.di.DaggerApiComponent
import com.changsune.changsu.yeontalk_kotlin.model.chatroom.ChatRoom
import com.changsune.changsu.yeontalk_kotlin.networking.ChatRoomListResponse
import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChatRoomListViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var yeonTalkService: YeonTalkService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    var sharedPreferences_profile = getApplication<Application>().getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, Context.MODE_PRIVATE)

    val chatRoomList = MutableLiveData<List<ChatRoom>>()

    var fetchChatRoomListError = MutableLiveData<Boolean>()

    fun refresh() {

        var meId = sharedPreferences_profile?.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, "")

        fetchChatRoomList(meId)

    }

    private fun fetchChatRoomList(meId : String?) {
        disposable.add(
            yeonTalkService.fetchChatRoomList(meId!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ChatRoomListResponse>(){
                    override fun onSuccess(value: ChatRoomListResponse?) {
                        chatRoomList.value = value?.chatRoomList
                    }

                    override fun onError(e: Throwable?) {
                        fetchChatRoomListError.value = true
                    }

                })

        )

    }

}
