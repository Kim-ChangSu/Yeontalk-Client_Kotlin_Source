package com.changsune.changsu.yeontalk_kotlin.viewmodel.user_details

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.di.DaggerApiComponent
import com.changsune.changsu.yeontalk_kotlin.model.userdetails.UserDetails
import com.changsune.changsu.yeontalk_kotlin.networking.Response
import com.changsune.changsu.yeontalk_kotlin.networking.UserDetailsResponse
import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailsViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var yeonTalkService: YeonTalkService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    var sharedPreferences_profile = getApplication<Application>().getSharedPreferences(Constants.SHAREDPREF_KEY_PROFILE, Context.MODE_PRIVATE)

    lateinit var meId: String

    val userDetails = MutableLiveData<UserDetails>()

    var fetchUserDetailsError = MutableLiveData<Boolean>()

    val isFavorite = MutableLiveData<Boolean>()

    fun refresh(userId: String) {

        fetchUserDetails(userId)

    }

    fun onInsertFavoriteButtonClicked(userId: String) {

        meId = sharedPreferences_profile.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, "")

        insertFavorites(meId, userId)
    }

    fun onDeleteFavoriteButtonClicked(userId: String) {

        meId = sharedPreferences_profile.getString(Constants.SHAREDPREF_KEY_PROFILE_USER_ID, "")

        deleteFavorites(meId, userId)

    }

    private fun fetchUserDetails(userId : String?) {
        disposable.add(
            yeonTalkService.fetchUserDetails(userId!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UserDetailsResponse>(){
                    override fun onSuccess(value: UserDetailsResponse?) {
                        userDetails.value = value?.userDetails?.get(0)
                    }

                    override fun onError(e: Throwable?) {
                        fetchUserDetailsError.value = true
                    }

                })

        )

    }

    private fun insertFavorites(meId: String, userId: String) {
        disposable.add(
            yeonTalkService.insertFavorite(meId, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response>(){
                    override fun onSuccess(value: Response?) {
                        if (value?.success == true) {
                            isFavorite.value = true
                        }

                    }

                    override fun onError(e: Throwable?) {

                    }

                })
        )

    }

    private fun deleteFavorites(meId: String, userId: String) {

        disposable.add(
            yeonTalkService.deleteFavorite(meId, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Response>(){
                    override fun onSuccess(value: Response?) {
                        if (value?.success == true) {
                            isFavorite.value = false
                        }
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
        )

    }

}