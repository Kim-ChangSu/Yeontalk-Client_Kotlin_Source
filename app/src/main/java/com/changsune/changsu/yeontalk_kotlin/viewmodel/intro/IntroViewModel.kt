package com.changsune.changsu.yeontalk_kotlin.viewmodel.intro

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import com.changsune.changsu.yeontalk_kotlin.Constants
import com.changsune.changsu.yeontalk_kotlin.di.DaggerApiComponent
import com.changsune.changsu.yeontalk_kotlin.networking.Response
import com.changsune.changsu.yeontalk_kotlin.networking.YeonTalkService
import com.changsune.changsu.yeontalk_kotlin.util.device_id_factory.DeviceIdFactory
import com.changsune.changsu.yeontalk_kotlin.util.dialogs_manager.DialogsManager
import com.changsune.changsu.yeontalk_kotlin.view.home.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IntroViewModel (application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var yeonTalkService: YeonTalkService

    init {
        DaggerApiComponent.create().inject(this)
        Constants.DEVICE_ID = DeviceIdFactory(getApplication()).deviceId
    }

    private val disposable = CompositeDisposable()

    val confirmRegistrationError = MutableLiveData<Boolean>()
    val resultOfConfirmRegistration = MutableLiveData<String>()

    fun refresh() {

        // mock device id
        Constants.DEVICE_ID = "8bfe44496cceecde"

        confirmRegistration(Constants.DEVICE_ID)

    }

    private fun confirmRegistration(deviceId: String) {

        confirmRegistrationError.value = false
        disposable.add(
            yeonTalkService.getResultOfConfirmingRegistration(deviceId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Response>() {
                    override fun onSuccess(value: Response?) {
                        confirmRegistrationError.value = false
                        resultOfConfirmRegistration.value = value!!.message
                    }

                    override fun onError(e: Throwable?) {
                        confirmRegistrationError.value = true
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}